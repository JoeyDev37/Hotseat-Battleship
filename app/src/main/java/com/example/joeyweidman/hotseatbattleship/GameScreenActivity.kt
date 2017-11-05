package com.example.joeyweidman.hotseatbattleship

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.GridLayout
import kotlinx.android.synthetic.main.activity_game_screen.*
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener





/*
 *Used this tutorial as reference to insert views into a GridLayout:
 * http://android-er.blogspot.com/2014/09/insert-view-to-gridlayout-dynamically.html
 */
class GameScreenActivity : AppCompatActivity() {

    lateinit var topGrid: Array<Array<Cell>>
    lateinit var bottomGrid: Array<Array<Cell>>
    lateinit var opponentShipGrid: Array<Array<Cell>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        if(GameInfo.currentPlayer == 1) {
            playerTurnText.text = "P1 Turn"
            topGrid = GameInfo.P1AttackGrid
            bottomGrid = GameInfo.P1ShipGrid
        } else if(GameInfo.currentPlayer == 2) {
            playerTurnText.text = "P2 Turn"
            topGrid = GameInfo.P2AttackGrid
            bottomGrid = GameInfo.P2ShipGrid
        }

        @SuppressLint("ClickableViewAccessibility")
        for(yPos in 0..9) {
            for(xPos in 0..9) {
                var currentCell: Cell = topGrid[xPos][yPos]
                currentCell.setOnTouchListener(object : OnTouchListener {
                    override fun onTouch(v: View, event: MotionEvent): Boolean {
                        if(GameInfo.currentPlayer == 1) {
                            opponentShipGrid = GameInfo.P2ShipGrid
                        } else if(GameInfo.currentPlayer == 2) {
                            opponentShipGrid = GameInfo.P1ShipGrid
                        }
                        when(event.action) {
                            (MotionEvent.ACTION_DOWN) -> {
                                if(currentCell.isTouchable) {
                                    //This means you have already chosen this cell previously
                                    if(topGrid[xPos][yPos].currentStatus == Status.HIT || topGrid[xPos][yPos].currentStatus == Status.MISS || topGrid[xPos][yPos].currentStatus == Status.SUNK ) {
                                        return true
                                    }
                                    if(opponentShipGrid[xPos][yPos].currentStatus == Status.EMPTY) {

                                        currentCell.currentStatus = Status.MISS

                                        val status: String = "MISS"

                                        if(GameInfo.currentPlayer == 1)
                                            GameInfo.currentPlayer = 2
                                        else
                                            GameInfo.currentPlayer = 1

                                        val intent: Intent = Intent(applicationContext, TextActivity::class.java)
                                        intent.putExtra("STATUS", status)
                                        applicationContext.startActivity(intent)

                                    } else {
                                        currentCell.currentStatus = Status.HIT

                                        val status: String = "HIT"

                                        if(GameInfo.currentPlayer == 1)
                                            GameInfo.currentPlayer = 2
                                        else
                                            GameInfo.currentPlayer = 1

                                        val intent: Intent = Intent(applicationContext, TextActivity::class.java)
                                        intent.putExtra("STATUS", status)
                                        applicationContext.startActivity(intent)
                                    }
                                    //playerAttackGrid[xPos][yPos].currentStatus = currentStatus
                                    currentCell.invalidate()
                                }
                            }
                        }
                        return true
                    }
                })
                if(topGrid[xPos][yPos].parent != null) {
                    val parent: ViewGroup = topGrid[xPos][yPos].parent as ViewGroup
                    parent.removeView(topGrid[xPos][yPos])
                    attackGridLayout.addView(topGrid[xPos][yPos])
                } else {
                    attackGridLayout.addView(topGrid[xPos][yPos])
                }
            }
        }
        for(yPos in 0..9) {
            for(xPos in 0..9) {
                if(bottomGrid[xPos][yPos].parent != null) {
                    val parent: ViewGroup = bottomGrid[xPos][yPos].parent as ViewGroup
                    parent.removeView(bottomGrid[xPos][yPos])
                    shipGridLayout.addView(bottomGrid[xPos][yPos])
                } else {
                    shipGridLayout.addView(bottomGrid[xPos][yPos])
                }
            }
        }

        attackGridLayout.viewTreeObserver.addOnGlobalLayoutListener(
                {
                    val MARGIN = 5

                    var layoutWidth = attackGridLayout.width
                    var layoutHeight = attackGridLayout.height
                    val cellWidth = layoutWidth / GameInfo.GRID_SIZE
                    val cellHeight = layoutHeight / GameInfo.GRID_SIZE

                    for (yPos in 0..GameInfo.GRID_SIZE - 1) {
                        for (xPos in 0..GameInfo.GRID_SIZE - 1) {
                            val params = topGrid[xPos][yPos].layoutParams as GridLayout.LayoutParams
                            params.width = cellWidth - 2 * MARGIN
                            params.height = cellHeight - 2 * MARGIN
                            params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN)
                            topGrid[xPos][yPos].layoutParams = params
                        }
                    }
                })

        shipGridLayout.viewTreeObserver.addOnGlobalLayoutListener(
                {
                    val MARGIN = 5

                    var layoutWidth = shipGridLayout.width
                    var layoutHeight = shipGridLayout.height
                    val cellWidth = layoutWidth / GameInfo.GRID_SIZE
                    val cellHeight = layoutHeight / GameInfo.GRID_SIZE

                    for (yPos in 0..GameInfo.GRID_SIZE - 1) {
                        for (xPos in 0..GameInfo.GRID_SIZE - 1) {
                            val params = bottomGrid[xPos][yPos].layoutParams as GridLayout.LayoutParams
                            params.width = cellWidth - 2 * MARGIN
                            params.height = cellHeight - 2 * MARGIN
                            params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN)
                            bottomGrid[xPos][yPos].layoutParams = params
                        }
                    }
                })
    }
}
