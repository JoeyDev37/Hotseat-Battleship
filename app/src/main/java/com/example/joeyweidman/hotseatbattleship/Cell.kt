package com.example.joeyweidman.hotseatbattleship

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth



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
            Status.EMPTY -> {
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
            Status.MISS -> {
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

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                if(isActive) {
                    if(currentStatus == Status.DESTROYER || currentStatus == Status.CRUISER || currentStatus == Status.SUBMARINE ||
                            currentStatus == Status.BATTLESHIP || currentStatus == Status.CARRIER) {
                        currentStatus = Status.HIT
                    }
                    Log.e("Cell", xPos.toString() + " , " + yPos.toString())
                    GameInfo.statusGridHistoryP1[xPos][yPos] = currentStatus
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