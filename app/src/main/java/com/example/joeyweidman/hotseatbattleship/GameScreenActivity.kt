package com.example.joeyweidman.hotseatbattleship

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import kotlinx.android.synthetic.main.activity_game_screen.*
import android.view.ViewTreeObserver.OnGlobalLayoutListener

/*
 *Used this tutorial as reference to insert views into a GridLayout:
 * http://android-er.blogspot.com/2014/09/insert-view-to-gridlayout-dynamically.html
 */
class GameScreenActivity : AppCompatActivity() {

    val GRID_SIZE: Int = 10 //Number of rows and columns
    lateinit var cells: Array<Cell>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        cells = Array(100, {Cell(this)})

        for(yPos in 0..9) {
            for(xPos in 0..9) {
                val cell = Cell(this, xPos, yPos)
                cells[yPos * 10 + xPos] = cell
                gridLayout.addView(cell)
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
                            val params = cells[yPos * GRID_SIZE + xPos].layoutParams as GridLayout.LayoutParams
                            params.width = cellWidth - 2 * MARGIN
                            params.height = cellHeight - 2 * MARGIN
                            params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN)
                            cells[yPos * GRID_SIZE + xPos].layoutParams = params
                        }
                    }
                })

    }

    private fun updateGrid() {

    }
}
