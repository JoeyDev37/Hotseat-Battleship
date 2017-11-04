package com.example.joeyweidman.hotseatbattleship

/**
 * Created by pcjoe on 11/4/2017.
 */
object GameInfo {
    var currentPlayer: Int
    var statusGridHistoryP1: Array<Array<Status>>
    var statusGridHistoryP2: Array<Array<Status>>
    var statusGridShipsP1: Array<Array<Status>>
    var statusGridShipsP2: Array<Array<Status>>

    init {
        currentPlayer = 1
        statusGridHistoryP1 = Array(10, { Array(10, { Status.EMPTY }) })
        statusGridHistoryP2 = Array(10, { Array(10, { Status.EMPTY }) })
        statusGridShipsP1 = Array(10, { Array(10, { Status.EMPTY }) })
        statusGridShipsP2 = Array(10, { Array(10, { Status.EMPTY }) })
    }
}