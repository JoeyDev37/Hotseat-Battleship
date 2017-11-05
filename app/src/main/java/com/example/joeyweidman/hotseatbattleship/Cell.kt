package com.example.joeyweidman.hotseatbattleship

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_game_screen.*
import kotlinx.android.synthetic.main.activity_game_screen.view.*


/**
 * Created by Joey Weidman on 11/3/2017.
 */
class Cell : View {
    var xPos: Int = 0
    var yPos: Int = 0
    private var isActive: Boolean = false //Allows the cell to be changeable.

    constructor(context: Context?, x: Int, y: Int, status: Status, isActive: Boolean) : super(context) {
        xPos = x
        yPos = y
        currentStatus = status
        this.isActive = isActive
    }
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    var currentStatus: Status
        set(newStatus) {
            field = newStatus
            invalidate()
        }
    var selectedColor: Int

    init {
        currentStatus = Status.EMPTY
        selectedColor = Color.CYAN
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas !is Canvas)
            return

        when(currentStatus) {
            (Status.EMPTY) -> {
                selectedColor = Color.CYAN
            }
            (Status.DESTROYER) -> {
                selectedColor = Color.GRAY
            }
            (Status.CRUISER) -> {
                selectedColor = Color.GRAY
            }
            (Status.SUBMARINE) -> {
                selectedColor = Color.GRAY
            }
            (Status.BATTLESHIP) -> {
                selectedColor = Color.GRAY
            }
            (Status.CARRIER) -> {
                selectedColor = Color.GRAY
            }
            (Status.HIT) -> {
                selectedColor = Color.GREEN
            }
            (Status.MISS) -> {
                selectedColor = Color.RED
            }
            Status.SUNK -> {
                selectedColor = Color.BLACK
            }
        }

        canvas.drawColor(selectedColor)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event !is MotionEvent)
            return false

        var currentHistoryStatusGrid:  Array<Array<Status>> = Array(10, {Array(10, {Status.EMPTY})})
        var opponentShipStatusGrid: Array<Array<Status>> = Array(10, {Array(10, {Status.EMPTY})})
        if(GameInfo.currentPlayer == 1) {
            currentHistoryStatusGrid = GameInfo.statusGridHistoryP1
            opponentShipStatusGrid = GameInfo.statusGridShipsP2
        }

        if(GameInfo.currentPlayer == 2) {
            currentHistoryStatusGrid = GameInfo.statusGridHistoryP2
            opponentShipStatusGrid = GameInfo.statusGridShipsP1
        }

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                if(isActive) {
                    //This means you have already chosen this cell previously
                    if(currentHistoryStatusGrid[xPos][yPos] == Status.HIT || currentHistoryStatusGrid[xPos][yPos] == Status.MISS || currentHistoryStatusGrid[xPos][yPos] == Status.SUNK ) {
                        return true
                    }
                    if(opponentShipStatusGrid[xPos][yPos] == Status.EMPTY) {

                        currentStatus = Status.MISS

                        val status: String = "MISS"

                        if(GameInfo.currentPlayer == 1)
                            GameInfo.currentPlayer = 2
                        else
                            GameInfo.currentPlayer = 1

                        val intent: Intent = Intent(context, TextActivity::class.java)
                        intent.putExtra("STATUS", status)
                        context.startActivity(intent)

                    } else {
                        currentStatus = Status.HIT

                        val status: String = "HIT"

                        if(GameInfo.currentPlayer == 1)
                            GameInfo.currentPlayer = 2
                        else
                            GameInfo.currentPlayer = 1

                        val intent: Intent = Intent(context, TextActivity::class.java)
                        intent.putExtra("STATUS", status)
                        context.startActivity(intent)
                    }
                    currentHistoryStatusGrid[xPos][yPos] = currentStatus
                    invalidate()
                }
            }
        }
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec))
    }
}