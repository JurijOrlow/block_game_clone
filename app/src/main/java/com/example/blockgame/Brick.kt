package com.example.blockgame

import java.util.*

class Brick (val numberOfBricks: Int = 19) {
    fun roll(isRangesRandom: Boolean = true): Int{
        if(isRangesRandom){
            return(1..numberOfBricks).random()
        }
        else{
            return Random().nextInt(numberOfBricks) + 1
        }
    }
}