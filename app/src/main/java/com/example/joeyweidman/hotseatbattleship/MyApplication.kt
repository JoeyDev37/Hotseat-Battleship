package com.example.joeyweidman.hotseatbattleship

import android.app.Application
import android.util.Log

/**
 * Created by pcjoe on 11/5/2017.
 */
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MyApplication
            private set

        var gameName: String = "Untitled"
        var gameState: String = "Starting"
        val GRID_SIZE = 10
        var currentPlayer: Int

        var player1: Player
        var player2: Player

        var player1UnsunkShips: Int
        var player2UnsunkShips: Int

        lateinit var P1ShipGrid: Array<Array<Cell>>
        lateinit var P1AttackGrid: Array<Array<Cell>>
        lateinit var P2ShipGrid: Array<Array<Cell>>
        lateinit var P2AttackGrid: Array<Array<Cell>>

        init {
            currentPlayer = 1
            player1UnsunkShips = 5
            player2UnsunkShips = 5

            player1 = Player()
            player2 = Player()
        }
    }

    fun hitShip(shipType: Ship?, player: Int) {
        if(player == 1) {
            when(shipType) {
                Ship.DESTROYER -> {
                    player1.destroyerHealth--
                }
                Ship.CRUISER -> {
                    player1.cruiserHealth--
                }
                Ship.SUBMARINE -> {
                    player1.submarineHealth--
                }
                Ship.BATTLESHIP -> {
                    player1.battleshipHealth--
                }
                Ship.CARRIER -> {
                    player1.carrierHealth--
                }
            }
        } else if (player == 2) {
            when(shipType) {
                Ship.DESTROYER -> {
                    player2.destroyerHealth--
                }
                Ship.CRUISER -> {
                    player2.cruiserHealth--
                }
                Ship.SUBMARINE -> {
                    player2.submarineHealth--
                }
                Ship.BATTLESHIP -> {
                    player2.battleshipHealth--
                }
                Ship.CARRIER -> {
                    player2.carrierHealth--
                }
            }
        }
    }
}