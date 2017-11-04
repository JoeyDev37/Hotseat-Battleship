package com.example.joeyweidman.hotseatbattleship

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import kotlinx.android.synthetic.main.activity_game_screen.*
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import java.io.Serializable

/*
 *Used this tutorial as reference to insert views into a GridLayout:
 * http://android-er.blogspot.com/2014/09/insert-view-to-gridlayout-dynamically.html
 */
class GameScreenActivity : AppCompatActivity() {

    companion object {
        var statusGrid: Array<Array<Status>> = Array(10, { Array(10, { Status.EMPTY }) })
    }

    val GRID_SIZE: Int = 10 //Number of rows and columns
    lateinit var cellGrid: Array<Array<Cell>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        cellGrid = Array(10, { Array(10, { Cell(this) }) })

        if(savedInstanceState != null) {
            @Suppress("UNCHECKED_CAST")
            statusGrid = savedInstanceState.getSerializable("StatusGridKey") as Array<Array<Status>>

            for(yPos in 0..9) {
                for(xPos in 0..9) {
                    Log.e("GameScreenActivity", statusGrid[xPos][yPos].toString())
                    val cell = Cell(this, xPos, yPos, statusGrid[xPos][yPos])
                    cellGrid[xPos][yPos] = cell
                    gridLayout.addView(cell)
                }
            }
        } else {
            for(yPos in 0..9) {
                for(xPos in 0..9) {
                    val cell = Cell(this, xPos, yPos, Status.EMPTY)
                    cellGrid[xPos][yPos] = cell
                    gridLayout.addView(cell)
                }
            }
        }

        gridLayout.viewTreeObserver.addOnGlobalLayoutListener(
                OnGlobalLayoutListener {
                    val MARGIN = 5

                    val layoutWidth = gridLayout.width
                    val layoutHeight = gridLayout.height
                    val cellWidth = layoutWidth / GRID_SIZE
                    val cellHeight = layoutHeight / GRID_SIZE

                    for (yPos in 0..GRID_SIZE - 1) {
                        for (xPos in 0..GRID_SIZE - 1) {
                            val params = cellGrid[xPos][yPos].layoutParams as GridLayout.LayoutParams
                            params.width = cellWidth - 2 * MARGIN
                            params.height = cellHeight - 2 * MARGIN
                            params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN)
                            cellGrid[xPos][yPos].layoutParams = params
                        }
                    }
                })

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putSerializable("StatusGridKey", statusGrid)
        super.onSaveInstanceState(outState)
    }

    private fun updateGrid() {

    }
}
