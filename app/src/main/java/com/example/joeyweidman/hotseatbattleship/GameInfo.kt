package com.example.joeyweidman.hotseatbattleship

import android.app.Application

/**
 * Created by pcjoe on 11/4/2017.
 */
object GameInfo {
    val GRID_SIZE = 10
    var currentPlayer: Int
    var statusGridHistoryP1: Array<Array<Status>>
    var statusGridHistoryP2: Array<Array<Status>>
    var statusGridShipsP1: Array<Array<Status>>
    var statusGridShipsP2: Array<Array<Status>>

    var player1: Player
    var player2: Player

    lateinit var P1ShipGrid: Array<Array<Cell>>
    lateinit var P1AttackGrid: Array<Array<Cell>>
    lateinit var P2ShipGrid: Array<Array<Cell>>
    lateinit var P2AttackGrid: Array<Array<Cell>>

    init {
        currentPlayer = 1
        statusGridHistoryP1 = Array(10, { Array(10, { Status.EMPTY }) })
        statusGridHistoryP2 = Array(10, { Array(10, { Status.EMPTY }) })
        statusGridShipsP1 = Array(10, { Array(10, { Status.EMPTY }) })
        statusGridShipsP2 = Array(10, { Array(10, { Status.EMPTY }) })

        player1 = Player()
        player2 = Player()
    }
}