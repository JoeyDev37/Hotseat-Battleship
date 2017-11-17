package com.example.joeyweidman.hotseatbattleship

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.io.Serializable
import java.io.IOException


/**
 * Created by Joey Weidman on 11/3/2017.
 */
class Cell : View, Serializable {
    constructor() : super(null)
    constructor(context: Context?, isTouchable: Boolean) : super(context) {
        this.isTouchable = isTouchable
    }
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    var isTouchable: Boolean = false //Allows the cell status to be changeable.
    var shipType: Ship?
    var currentStatus: Status
        set(newStatus) {
            field = newStatus
            invalidate()
        }
    var selectedColor: Int

    init {
        currentStatus = Status.EMPTY
        selectedColor = Color.CYAN
        shipType = null
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

    @Throws(IOException::class)
    private fun writeObject(stream: java.io.ObjectOutputStream) {
        stream.writeObject(isTouchable)
        stream.writeObject(shipType)
        stream.writeObject(currentStatus)
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(stream: java.io.ObjectInputStream) {
        Log.e("Cell", "REACHED")
        isTouchable = stream.readObject() as Boolean
        shipType = stream.readObject() as Ship?
        currentStatus = stream.readObject() as Status
    }
}