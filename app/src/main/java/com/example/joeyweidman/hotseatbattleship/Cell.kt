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
    var isTouchable: Boolean = false //Allows the cell to be changeable.

    constructor(context: Context?, x: Int, y: Int, status: Status, isTouchable: Boolean) : super(context) {
        xPos = x
        yPos = y
        currentStatus = status
        this.isTouchable = isTouchable
    }
    constructor(context: Context?, isTouchable: Boolean) : super(context) {
        this.isTouchable = isTouchable
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
            (Status.SHIP) -> {
                selectedColor = Color.GRAY
            }
            (Status.EMPTY) -> {
                selectedColor = Color.CYAN
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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec))
    }
}