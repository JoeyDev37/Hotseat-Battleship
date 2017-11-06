package com.example.joeyweidman.hotseatbattleship

import java.io.Serializable

/**
 * Created by pcjoe on 11/4/2017.
 */
class Player: Serializable {
    var shipsRemaining: Int
    var destroyerHealth: Int
    var cruiserHealth: Int
    var submarineHealth: Int
    var battleshipHealth: Int
    var carrierHealth: Int

    init {
        shipsRemaining = 5
        destroyerHealth = 2
        cruiserHealth = 3
        submarineHealth = 3
        battleshipHealth = 4
        carrierHealth = 5
    }
}