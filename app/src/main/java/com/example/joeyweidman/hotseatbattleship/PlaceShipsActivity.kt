package com.example.joeyweidman.hotseatbattleship

import android.content.Intent
import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import kotlinx.android.synthetic.main.activity_place_ships.*
import java.util.*

class PlaceShipsActivity : AppCompatActivity() {

    val GRID_SIZE = 10
    lateinit var shipGrid: Array<Array<Cell>>
    var currentStatusGrid = GameInfo.statusGridShipsP1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_ships)

        if(GameInfo.currentPlayer == 1) {
            currentStatusGrid = GameInfo.statusGridShipsP1
            placeShipsTextView.text = "P1 Place Ships"
        }

        if(GameInfo.currentPlayer == 2) {
            currentStatusGrid = GameInfo.statusGridHistoryP2
            placeShipsTextView.text = "P2 Place Ships"
        }


        shipGrid = Array(10, { Array(10, { Cell(this) }) })

        for(yPos in 0..9) {
            for(xPos in 0..9) {
                var cell: Cell = Cell(this, xPos, yPos, currentStatusGrid[xPos][yPos], false)
                shipGrid[xPos][yPos] = cell
                placeShipsGridLayout.addView(cell)
            }
        }

        placeShipsGridLayout.viewTreeObserver.addOnGlobalLayoutListener(
                {
                    val MARGIN = 5

                    var layoutWidth = placeShipsGridLayout.width
                    var layoutHeight = placeShipsGridLayout.height
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

        autoButton.setOnClickListener {
            placeRandomShips()
        }

        continueButton.setOnClickListener {

            if(GameInfo.currentPlayer == 1) {
                GameInfo.currentPlayer = 2
                val intent: Intent = Intent(applicationContext, PlaceShipsActivity::class.java)
                startActivity(intent)
            } else {
                val intent: Intent = Intent(applicationContext, GameScreenActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun placeRandomShips() {
        for(ship in Ship.values()) {
            placeShip(ship)
        }
        //Refresh the grid
        for(i in 0..GRID_SIZE - 1) {
            for (j in 0..GRID_SIZE - 1) {
                shipGrid[j][i].currentStatus = currentStatusGrid[j][i]
            }
        }
    }

    fun placeShip(shipToPlace: Ship) {
        var shipTypeStatus: Status
        when(shipToPlace) {
            Ship.DESTROYER -> {
                shipTypeStatus = Status.DESTROYER
            }
            Ship.CRUISER -> {
                shipTypeStatus = Status.CRUISER
            }
            Ship.SUBMARINE -> {
                shipTypeStatus = Status.SUBMARINE
            }
            Ship.BATTLESHIP -> {
                shipTypeStatus = Status.BATTLESHIP
            }
            Ship.CARRIER -> {
                shipTypeStatus = Status.CARRIER
            }
        }

        var potentialPlacement: Array<Point>
        start@while(true) {
            var random: Random = Random()
            val randomX: Int = random.nextInt(10)
            random = Random()
            val randomY: Int = random.nextInt(10)
            val currentPoint: Point = Point(randomX, randomY)
            val startingPoint: Point = Point(currentPoint)
            potentialPlacement = Array(shipToPlace.size, {Point()})
            potentialPlacement[0] = startingPoint
            val randomDirection: Direction = Direction.randomDirection()

            when(randomDirection) {
                Direction.NORTH -> {
                    for(i in 1..shipToPlace.size - 1) {
                        if(currentPoint.y - 1 < 0 || currentStatusGrid[currentPoint.x][currentPoint.y - 1] != Status.EMPTY) {
                            continue@start
                        } else {
                            currentPoint.y--
                            val point: Point = Point(currentPoint)
                            potentialPlacement[i] = point
                            if(i == shipToPlace.size - 1)
                                break@start
                        }
                    }
                }
                Direction.SOUTH -> {
                    for(i in 1..shipToPlace.size - 1) {
                        if(currentPoint.y + 1 > 9 || currentStatusGrid[currentPoint.x][currentPoint.y + 1] != Status.EMPTY) {
                            continue@start
                        } else {
                            currentPoint.y++
                            val point: Point = Point(currentPoint)
                            potentialPlacement[i] = point
                            if(i == shipToPlace.size - 1)
                                break@start
                        }
                    }
                }
                Direction.EAST -> {
                    for(i in 1..shipToPlace.size - 1) {
                        if(currentPoint.x + 1 > 9 || currentStatusGrid[currentPoint.x + 1][currentPoint.y] != Status.EMPTY) {
                            continue@start
                        } else {
                            currentPoint.x++
                            val point: Point = Point(currentPoint)
                            potentialPlacement[i] = point
                            if(i == shipToPlace.size - 1)
                                break@start
                        }
                    }
                }
                Direction.WEST -> {
                    for(i in 1..shipToPlace.size - 1) {
                        if(currentPoint.x - 1 < 0 || currentStatusGrid[currentPoint.x - 1][currentPoint.y] != Status.EMPTY) {
                            continue@start
                        } else {
                            currentPoint.x--
                            val point: Point = Point(currentPoint)
                            potentialPlacement[i] = point
                            if(i == shipToPlace.size - 1)
                                break@start
                        }
                    }
                }
            }
        }
        for(i in potentialPlacement) {
            currentStatusGrid[i.x][i.y] = shipTypeStatus
        }
    }

    enum class Direction(value: Int) {
        NORTH(0),
        SOUTH(1),
        EAST(2),
        WEST(3);

        companion object {
            fun randomDirection(): Direction {
                var random: Random = Random()
                return values()[random.nextInt(values().size)]
            }
        }
    }
}
