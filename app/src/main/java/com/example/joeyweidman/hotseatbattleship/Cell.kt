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
 * Created by pcjoe on 11/3/2017.
 */
class Cell : View {
    constructor(context: Context?, x: Int, y: Int) : super(context)
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    var currentStatus: Status = Status.EMPTY
    var selectedColor = Color.GREEN

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas !is Canvas)
            return

        canvas.drawColor(selectedColor)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event !is MotionEvent)
            return false

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                selectedColor = Color.YELLOW
                invalidate()
            }
        }
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec))
    }
}