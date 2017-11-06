package com.example.joeyweidman.hotseatbattleship

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        if(MyApplication.currentPlayer == 1) {
            playerTurnText.text = "P1 Turn"
            topGrid = MyApplication.P1AttackGrid
            bottomGrid = MyApplication.P1ShipGrid
        } else if(MyApplication.currentPlayer == 2) {
            playerTurnText.text = "P2 Turn"
            topGrid = MyApplication.P2AttackGrid
            bottomGrid = MyApplication.P2ShipGrid
        }

        @SuppressLint("ClickableViewAccessibility")
        for(yPos in 0..9) {
            for(xPos in 0..9) {
                var currentCell: Cell = topGrid[xPos][yPos]
                currentCell.setOnTouchListener(object : OnTouchListener {
                    override fun onTouch(v: View, event: MotionEvent): Boolean {
                        if(MyApplication.currentPlayer == 1) {
                            opponentShipGrid = MyApplication.P2ShipGrid
                        } else if(MyApplication.currentPlayer == 2) {
                            opponentShipGrid = MyApplication.P1ShipGrid
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
                                        opponentShipGrid[xPos][yPos].currentStatus = Status.MISS

                                        val status: String = "MISS"

                                        changePlayer()
                                        val intent: Intent = Intent(applicationContext, TextActivity::class.java)
                                        intent.putExtra("STATUS", status)
                                        applicationContext.startActivity(intent)

                                    } else {
                                        currentCell.currentStatus = Status.HIT
                                        opponentShipGrid[xPos][yPos].currentStatus = Status.HIT

                                        var status: String = "HIT"

                                        val oppositePlayer = if(MyApplication.currentPlayer == 1) {2} else {1}

                                        MyApplication.hitShip(opponentShipGrid[xPos][yPos].shipType, oppositePlayer)

                                        if(MyApplication.currentPlayer == 2) {
                                            if(MyApplication.player1.destroyerHealth == 0) {
                                                status = "SUNK DESTROYER"
                                                sinkShip(Ship.DESTROYER)
                                                MyApplication.player1.destroyerHealth = -1
                                            } else if (MyApplication.player1.cruiserHealth == 0) {
                                                status = "SUNK CRUISER"
                                                sinkShip(Ship.CRUISER)
                                                MyApplication.player1.cruiserHealth = -1
                                            } else if (MyApplication.player1.submarineHealth == 0) {
                                                status = "SUNK SUBMARINE"
                                                sinkShip(Ship.SUBMARINE)
                                                MyApplication.player1.submarineHealth = -1
                                            } else if (MyApplication.player1.battleshipHealth == 0) {
                                                status = "SUNK BATTLESHIP"
                                                sinkShip(Ship.BATTLESHIP)
                                                MyApplication.player1.battleshipHealth = -1
                                            } else if (MyApplication.player1.carrierHealth == 0) {
                                                status = "SUNK CARRIER"
                                                sinkShip(Ship.CARRIER)
                                                MyApplication.player1.carrierHealth = -1
                                            }
                                        }

                                        if(MyApplication.currentPlayer == 1) {
                                            if(MyApplication.player2.destroyerHealth == 0) {
                                                status = "SUNK DESTROYER"
                                                sinkShip(Ship.DESTROYER)
                                                MyApplication.player2.destroyerHealth = -1
                                            } else if (MyApplication.player2.cruiserHealth == 0) {
                                                status = "SUNK CRUISER"
                                                sinkShip(Ship.CRUISER)
                                                MyApplication.player2.cruiserHealth = -1
                                            } else if (MyApplication.player2.submarineHealth == 0) {
                                                status = "SUNK SUBMARINE"
                                                sinkShip(Ship.SUBMARINE)
                                                MyApplication.player2.submarineHealth = -1
                                            } else if (MyApplication.player2.battleshipHealth == 0) {
                                                status = "SUNK BATTLESHIP"
                                                sinkShip(Ship.BATTLESHIP)
                                                MyApplication.player2.battleshipHealth = -1
                                            } else if (MyApplication.player2.carrierHealth == 0) {
                                                status = "SUNK CARRIER"
                                                sinkShip(Ship.CARRIER)
                                                MyApplication.player2.carrierHealth = -1
                                            }
                                        }

                                        if(checkForVictory()) {
                                            if(MyApplication.currentPlayer == 1)
                                                status = "P1 VICTORY!"
                                            else if (MyApplication.currentPlayer == 2)
                                                status = "P2 VICTORY!"
                                        }

                                        changePlayer()
                                        val intent: Intent = Intent(MyApplication.applicationContext(), TextActivity::class.java)
                                        intent.putExtra("STATUS", status)
                                        MyApplication.applicationContext().startActivity(intent)
                                    }
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
                    val cellWidth = layoutWidth / MyApplication.GRID_SIZE
                    val cellHeight = layoutHeight / MyApplication.GRID_SIZE

                    for (yPos in 0..MyApplication.GRID_SIZE - 1) {
                        for (xPos in 0..MyApplication.GRID_SIZE - 1) {
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
                    val cellWidth = layoutWidth / MyApplication.GRID_SIZE
                    val cellHeight = layoutHeight / MyApplication.GRID_SIZE

                    for (yPos in 0..MyApplication.GRID_SIZE - 1) {
                        for (xPos in 0..MyApplication.GRID_SIZE - 1) {
                            val params = bottomGrid[xPos][yPos].layoutParams as GridLayout.LayoutParams
                            params.width = cellWidth - 2 * MARGIN
                            params.height = cellHeight - 2 * MARGIN
                            params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN)
                            bottomGrid[xPos][yPos].layoutParams = params
                        }
                    }
                })
    }

    fun changePlayer() {
        if(MyApplication.currentPlayer == 1)
            MyApplication.currentPlayer = 2
        else
            MyApplication.currentPlayer = 1
    }

    fun sinkShip(shipType: Ship) {
        for(yPos in 0..9) {
            for(xPos in 0..9) {
                if(opponentShipGrid[xPos][yPos].shipType == shipType){
                    topGrid[xPos][yPos].currentStatus = Status.SUNK
                    topGrid[xPos][yPos].invalidate()
                    opponentShipGrid[xPos][yPos].currentStatus = Status.SUNK
                    opponentShipGrid[xPos][yPos].invalidate()
                }
            }
        }
    }

    fun checkForVictory() : Boolean {
        if(MyApplication.currentPlayer == 1) {
            return (MyApplication.player2.destroyerHealth == -1 && MyApplication.player2.cruiserHealth == -1 && MyApplication.player2.submarineHealth == -1 &&
                    MyApplication.player2.battleshipHealth == -1 && MyApplication.player2.carrierHealth == -1)
        } else if (MyApplication.currentPlayer == 2) {
            return (MyApplication.player1.destroyerHealth == -1 && MyApplication.player1.cruiserHealth == -1 && MyApplication.player1.submarineHealth == -1 &&
                    MyApplication.player1.battleshipHealth == -1 && MyApplication.player1.carrierHealth == -1)
        }
        return false
    }
}
