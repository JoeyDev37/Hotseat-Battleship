package com.example.joeyweidman.hotseatbattleship

import android.app.Application
import android.content.Context
import android.util.Log
import java.io.*

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose

/**
 * Created by pcjoe on 11/5/2017.
 */
class MyApplication: Application(), Serializable {


    override fun onCreate() {
        super.onCreate()

        instance = this

        val context: Context = MyApplication.applicationContext()
    }

    companion object {
        private var instance: MyApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }

        @Expose var gameName: String = "Untitled"
        @Expose var gameState: String = "Starting"
        @Expose var currentPlayer: Int

        val GRID_SIZE = 10

        @Expose var player1: Player
        @Expose var player2: Player

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
        }

        fun logInfo() {
            Log.e("MyApplication", "GameState: " + gameState + "\n" +
                                            "Current Turn: Player " + currentPlayer + "\n" +
                                            "Player1 unsunk ships: " + player1UnsunkShips + "\n" +
                                            "Player 2 unsunk ships: " + player2UnsunkShips)
        }

        fun saveGame(file: String) {
            val fileStream: FileOutputStream? = applicationContext().openFileOutput(file, Context.MODE_PRIVATE)
            val objectStream = ObjectOutputStream(fileStream)

            objectStream.writeObject(gameName)
            objectStream.writeObject(gameState)
            objectStream.writeObject(currentPlayer)
            objectStream.writeObject(player1UnsunkShips)
            objectStream.writeObject(player2UnsunkShips)

            objectStream.writeObject(player1)
            objectStream.writeObject(player2)

            var P1TempShipGrid: Array<Array<Triple<Status, Ship?, Boolean>>> = Array(10, {Array(10, {Triple(Status.EMPTY, P1ShipGrid[0][0].shipType, false)})})
            for(i in 0..9) {
                for(j in 0..9) {
                    var triple: Triple<Status, Ship?, Boolean> = Triple(P1ShipGrid[j][i].currentStatus, P1ShipGrid[j][i].shipType, P1ShipGrid[j][i].isActivated)
                    P1TempShipGrid[j][i] = triple
                }
            }
            objectStream.writeObject(P1TempShipGrid)

            var P1TempAttackGrid: Array<Array<Triple<Status, Ship?, Boolean>>> = Array(10, {Array(10, {Triple(Status.EMPTY, P1ShipGrid[0][0].shipType, false)})})
            for(i in 0..9) {
                for(j in 0..9) {
                    var triple: Triple<Status, Ship?, Boolean> = Triple(P1AttackGrid[j][i].currentStatus, P1AttackGrid[j][i].shipType, P1AttackGrid[j][i].isActivated)
                    P1TempAttackGrid[j][i] = triple
                }
            }
            objectStream.writeObject(P1TempAttackGrid)

            var P2TempShipGrid: Array<Array<Triple<Status, Ship?, Boolean>>> = Array(10, {Array(10, {Triple(Status.EMPTY, P1ShipGrid[0][0].shipType, false)})})
            for(i in 0..9) {
                for(j in 0..9) {
                    var triple: Triple<Status, Ship?, Boolean> = Triple(P2ShipGrid[j][i].currentStatus, P2ShipGrid[j][i].shipType, P2ShipGrid[j][i].isActivated)
                    P2TempShipGrid[j][i] = triple
                }
            }
            objectStream.writeObject(P2TempShipGrid)

            var P2TempAttackGrid: Array<Array<Triple<Status, Ship?, Boolean>>> = Array(10, {Array(10, {Triple(Status.EMPTY, P1ShipGrid[0][0].shipType, false)})})
            for(i in 0..9) {
                for(j in 0..9) {
                    var triple: Triple<Status, Ship?, Boolean> = Triple(P2AttackGrid[j][i].currentStatus, P2AttackGrid[j][i].shipType, P2AttackGrid[j][i].isActivated)
                    P2TempAttackGrid[j][i] = triple
                }
            }
            objectStream.writeObject(P2TempAttackGrid)

            objectStream.close()
            fileStream?.close()
        }

        fun loadGame(file: File) {
            /*val file: File = MyApplication.applicationContext().filesDir.listFiles()[0]
            val text: String  = file.readText()
            Log.e("MyApplication", text)*/
            val fileStream = FileInputStream(file)
            val objectStream = ObjectInputStream(fileStream)

            gameName = objectStream.readObject() as String
            gameState = objectStream.readObject() as String
            currentPlayer = objectStream.readObject() as Int
            player1UnsunkShips = objectStream.readObject() as Int
            player2UnsunkShips = objectStream.readObject() as Int

            player1 = objectStream.readObject() as Player
            player2 = objectStream.readObject() as Player

            val P1TempShipGrid = objectStream.readObject() as Array<Array<Triple<Status, Ship?, Boolean>>>
            for(i in 0..9) {
                for(j in 0..9) {
                    P1ShipGrid[j][i].currentStatus = P1TempShipGrid[j][i].first
                    P1ShipGrid[j][i].shipType = P1TempShipGrid[j][i].second
                    P1ShipGrid[j][i].isActivated = P1TempShipGrid[j][i].third
                }
            }
            val P1TempAttackGrid = objectStream.readObject() as Array<Array<Triple<Status, Ship?, Boolean>>>
            for(i in 0..9) {
                for(j in 0..9) {
                    P1AttackGrid[j][i].currentStatus = P1TempAttackGrid[j][i].first
                    P1AttackGrid[j][i].shipType = P1TempAttackGrid[j][i].second
                    P1AttackGrid[j][i].isActivated = P1TempAttackGrid[j][i].third
                }
            }
            val P2TempShipGrid = objectStream.readObject() as Array<Array<Triple<Status, Ship?, Boolean>>>
            for(i in 0..9) {
                for(j in 0..9) {
                    P2ShipGrid[j][i].currentStatus = P2TempShipGrid[j][i].first
                    P2ShipGrid[j][i].shipType = P2TempShipGrid[j][i].second
                    P2ShipGrid[j][i].isActivated = P2TempShipGrid[j][i].third
                }
            }
            val P2TempAttackGrid = objectStream.readObject() as Array<Array<Triple<Status, Ship?, Boolean>>>
            for(i in 0..9) {
                for(j in 0..9) {
                    P2AttackGrid[j][i].currentStatus = P2TempAttackGrid[j][i].first
                    P2AttackGrid[j][i].shipType = P2TempAttackGrid[j][i].second
                    P2AttackGrid[j][i].isActivated = P2TempAttackGrid[j][i].third
                }
            }
        }
    }
}