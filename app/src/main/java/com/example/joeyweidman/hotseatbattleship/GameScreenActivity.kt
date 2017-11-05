package com.example.joeyweidman.hotseatbattleship

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

    val GRID_SIZE: Int = 10 //Number of rows and columns
    lateinit var historyGrid: Array<Array<Cell>>
    lateinit var shipGrid: Array<Array<Cell>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        historyGrid = Array(10, { Array(10, { Cell(this) }) })
        shipGrid = Array(10, { Array(10, { Cell(this) }) })

        for(yPos in 0..9) {
            for(xPos in 0..9) {
                val cell = Cell(this, xPos, yPos, GameInfo.statusGridHistoryP1[xPos][yPos], true)
                historyGrid[xPos][yPos] = cell
                historyGridLayout.addView(cell)
            }
        }
        for(yPos in 0..9) {
            for(xPos in 0..9) {
                val cell = Cell(this, xPos, yPos, GameInfo.statusGridShipsP1[xPos][yPos], false)
                shipGrid[xPos][yPos] = cell
                shipGridLayout.addView(cell)
            }
        }

        historyGridLayout.viewTreeObserver.addOnGlobalLayoutListener(
                {
                    val MARGIN = 5

                    var layoutWidth = historyGridLayout.width
                    var layoutHeight = historyGridLayout.height
                    val cellWidth = layoutWidth / GRID_SIZE
                    val cellHeight = layoutHeight / GRID_SIZE

                    for (yPos in 0..GRID_SIZE - 1) {
                        for (xPos in 0..GRID_SIZE - 1) {
                            val params = historyGrid[xPos][yPos].layoutParams as GridLayout.LayoutParams
                            params.width = cellWidth - 2 * MARGIN
                            params.height = cellHeight - 2 * MARGIN
                            params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN)
                            historyGrid[xPos][yPos].layoutParams = params
                        }
                    }
                })

        shipGridLayout.viewTreeObserver.addOnGlobalLayoutListener(
                {
                    val MARGIN = 5

                    var layoutWidth = shipGridLayout.width
                    var layoutHeight = shipGridLayout.height
                    val cellWidth = layoutWidth / GRID_SIZE
                    val cellHeight = layoutHeight / GRID_SIZE

                    for (yPos in 0..GRID_SIZE - 1) {
                        for (xPos in 0..GRID_SIZE - 1) {
                            val params = shipGrid[xPos][yPos].layoutParams as GridLayout.LayoutParams
                            params.width = cellWidth - 2 * MARGIN
                            params.height = cellHeight - 2 * MARGIN
                            params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN)
                            shipGrid[xPos][yPos].layoutParams = params
                        }
                    }
                })
    }
}
