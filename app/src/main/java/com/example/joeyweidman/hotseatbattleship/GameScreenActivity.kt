package com.example.joeyweidman.hotseatbattleship

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridLayout
import kotlinx.android.synthetic.main.activity_game_screen.*
import java.io.Serializable
import android.view.MotionEvent
import android.view.View.OnTouchListener



/*
 *Used this tutorial as reference to insert views into a GridLayout:
 * http://android-er.blogspot.com/2014/09/insert-view-to-gridlayout-dynamically.html
 */
class GameScreenActivity : AppCompatActivity() {

    val GRID_SIZE: Int = 10 //Number of rows and columns

    lateinit var historyGrid: Array<Array<Cell>>
    lateinit var shipGrid: Array<Array<Cell>>
    lateinit var currentHistoryStatusGrid:  Array<Array<Status>>
    lateinit var currentShipsStatusGrid:  Array<Array<Status>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        if(GameInfo.currentPlayer == 1) {
            currentShipsStatusGrid = GameInfo.statusGridShipsP1
            currentHistoryStatusGrid = GameInfo.statusGridHistoryP1
        }
        if(GameInfo.currentPlayer == 2) {
            Log.e("GameScreenActivity", "REACHED")
            currentShipsStatusGrid = GameInfo.statusGridShipsP2
            currentHistoryStatusGrid = GameInfo.statusGridHistoryP2
        }

        historyGrid = Array(10, { Array(10, { Cell(this) }) })
        shipGrid = Array(10, { Array(10, { Cell(this) }) })

        @SuppressLint("ClickableViewAccessibility")
        for(yPos in 0..9) {
            for(xPos in 0..9) {
                val cell = Cell(this, xPos, yPos, currentHistoryStatusGrid[xPos][yPos], true)

                /*cell.setOnTouchListener(OnTouchListener { _, event ->
                    if (event.action == MotionEvent.ACTION_UP) {
                        //nextPlayerButton.visibility = View.VISIBLE
                        // Do what you want
                        return@OnTouchListener true
                    }
                    false
                })*/
                historyGrid[xPos][yPos] = cell
                historyGridLayout.addView(cell)
            }
        }
        for(yPos in 0..9) {
            for(xPos in 0..9) {
                val cell = Cell(this, xPos, yPos, currentShipsStatusGrid[xPos][yPos], false)
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
