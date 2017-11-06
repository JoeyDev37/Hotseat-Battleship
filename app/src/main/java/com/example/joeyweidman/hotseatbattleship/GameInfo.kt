package com.example.joeyweidman.hotseatbattleship

import android.app.Application
import android.content.Context
import android.util.Log
import java.io.*



/**
 * Created by pcjoe on 11/4/2017.
 */
object GameInfo: Serializable, Application(){
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
        Log.e("GameInfo", shipType.toString() + " , " + player1.destroyerHealth)
    }

    fun writeObject(file: String) {
        val fileStream: FileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
        val objectStream = ObjectOutputStream(fileStream)

        objectStream.writeObject(gameState)
        objectStream.writeObject(currentPlayer)
        objectStream.writeObject(player1UnsunkShips)
        objectStream.writeObject(player2UnsunkShips)

        objectStream.writeObject(player1)
        objectStream.writeObject(player2)

        objectStream.writeObject(P1ShipGrid)
        objectStream.writeObject(P1AttackGrid)
        objectStream.writeObject(P2ShipGrid)
        objectStream.writeObject(P2AttackGrid)

        objectStream.close()
        fileStream.close()
    }

    fun readObject(file: File) {
        val fileStream = FileInputStream(file)
        val objectStream = ObjectInputStream(fileStream)

        gameState = objectStream.readObject() as String
        currentPlayer = objectStream.readObject() as Int
        player1UnsunkShips = objectStream.readObject() as Int
        player2UnsunkShips = objectStream.readObject() as Int

        player1 = objectStream.read() as Player
        player2 = objectStream.read() as Player

        P1ShipGrid = objectStream.read() as Array<Array<Cell>>
        P1AttackGrid = objectStream.read() as Array<Array<Cell>>
        P2ShipGrid = objectStream.read() as Array<Array<Cell>>
        P2AttackGrid = objectStream.read() as Array<Array<Cell>>
    }
}