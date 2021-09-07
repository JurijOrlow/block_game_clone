package com.example.blockgame

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlin.math.abs

class GameScreenActivity : AppCompatActivity() {
    var globX:Int = 0
    var globY:Int = 0
    val TAG = "GameScrenActivity"
    override fun onTouchEvent(event: MotionEvent): Boolean {
        globX = event.x.toInt()
        globY = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {}
            MotionEvent.ACTION_MOVE -> {}
            MotionEvent.ACTION_UP -> {Toast.makeText(this@GameScreenActivity, "${globX} x ${globY}", Toast.LENGTH_SHORT).show()}
        }

        return false
    }
    val meshXCoords:IntArray = intArrayOf(300, 440, 580, 720, 860, 1000)
    val meshYCoords:IntArray = intArrayOf(788, 928, 1068, 1208, 1348, 1488)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)
        roll()
        /*val listener = View.OnTouchListener(function = { view, motionEvent ->

            if (motionEvent.action == MotionEvent.ACTION_MOVE) {

                view.y = motionEvent.rawY - view.height/2
                view.x = motionEvent.rawX - view.width/2
            }

            true

        })*/

        // Declared in our activity_shapes_view.xml file.
        val brick_drag_1 = findViewById<ImageView>(R.id.rolled_1)
        val brick_drag_2 = findViewById<ImageView>(R.id.rolled_2)
        val brick_drag_3 = findViewById<ImageView>(R.id.rolled_3)
        brick_drag_1.setOnTouchListener (object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                RunAnimations(event, brick_drag_1)
                return true
            }
        })
        brick_drag_2.setOnTouchListener (object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                RunAnimations(event, brick_drag_2)
                return true
            }
        })
        brick_drag_3.setOnTouchListener (object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                RunAnimations(event, brick_drag_3)
                return true
            }
        })
        //draggableImage.setOnLongClickListener(listener)
        val losuj = findViewById<Button>(R.id.reroll_button)

        losuj.setOnClickListener {
            roll()
        }
    }

    private fun RunAnimations(event: MotionEvent, brick_1: View) {
        //  Animation b = AnimationUtils.loadAnimation(this,R.anim.cardtrans);
        val xStart: Int
        val yStart: Int
        var dx:Float
        var dy:Float
        var toX:Float = 500.0F
        var toY:Float = 500.0F
        xStart = brick_1.getLeft()
        yStart = brick_1.getTop()
        when(event.action) {
            MotionEvent.ACTION_DOWN ->
            {

            }
            MotionEvent.ACTION_UP -> {
                dx = event.rawX
                dy = event.rawY
                var absdx = event.x
                var absdy = event.y
                if (dx >= 300 && dy >= 788) {
                    val coords:FloatArray = blockCoords(dx, dy)
                    toX = coords[0] - (dx - absdx)
                    toY = coords[1] - (dy - absdy)
                    Toast.makeText(this@GameScreenActivity, "COORDS ${blockCoords(dx, dy)[0]}", Toast.LENGTH_SHORT).show()
                }
                else {
                    toX = 0.0F
                    toY = 0.0F
                }
                val b: Animation = TranslateAnimation(
                    Animation.ABSOLUTE, 0F, Animation.ABSOLUTE, toX,
                    Animation.ABSOLUTE, 0F, Animation.ABSOLUTE, toY,
                )
                val testLoc = findViewById<ImageView>(R.id.sq11)
                val location = IntArray(2)
                testLoc.getLocationOnScreen(location)
                val x = location[0]
                val y = location[1]
                Toast.makeText(this@GameScreenActivity, "${x} x ${y}", Toast.LENGTH_SHORT).show()
                Toast.makeText(
                    this@GameScreenActivity,
                    "${dx} x ${dy} || ${absdx} x ${absdy} || ${toX} x ${toY} || ${x} x ${y}",
                    Toast.LENGTH_LONG
                ).show()
                b.setDuration(3000)
                b.fillAfter = true
                brick_1.clearAnimation()
                brick_1.startAnimation(b)
            }
        }
    }

    fun roll(){
        val brick = Brick()
        val brick1 = brick.roll()
        val brick2 = brick.roll(false)
        val brick3 = brick.roll()
        //val tempRes = findViewById<TextView>(R.id.temp_result)
        //tempRes.text = "${brick1} & ${brick2} & ${brick3}"
        val testLoc1 = findViewById<ImageView>(R.id.sq11)
        val testLoc2 = findViewById<ImageView>(R.id.sq12)
        val testLoc3 = findViewById<ImageView>(R.id.sq21)
        val location1 = IntArray(2)
        val location2 = IntArray(2)
        val location3 = IntArray(2)
        testLoc1.getLocationOnScreen(location1)
        testLoc2.getLocationOnScreen(location2)
        testLoc3.getLocationOnScreen(location3)
        val x1 = location1[0]
        val y1 = location1[1]
        val x2 = location2[0]
        val y2 = location2[1]
        val x3 = location3[0]
        val y3 = location3[1]

        Toast.makeText(this@GameScreenActivity, "${x1} x ${y1} || ${x2} x ${y2} || ${x3} x ${y3} ||", Toast.LENGTH_SHORT).show()
        updateImages(brick1, brick2, brick3)
    }

    fun updateImages(brick1: Int, brick2: Int, brick3: Int) {
        val brickImg1: ImageView = findViewById<ImageView>(R.id.rolled_1)
        val brickImg2: ImageView = findViewById<ImageView>(R.id.rolled_2)
        val brickImg3: ImageView = findViewById<ImageView>(R.id.rolled_3)

        val drawableRes1 = when(brick1){
            1 -> R.drawable.block_1x1
            2 -> R.drawable.block_1x2
            3 -> R.drawable.block_2x1
            4 -> R.drawable.block_ml1
            5 -> R.drawable.block_ml2
            6 -> R.drawable.block_ml3
            7 -> R.drawable.block_ml4
            8 -> R.drawable.block_2x2
            9 -> R.drawable.block_1x3
            10 -> R.drawable.block_3x1
            11 -> R.drawable.block_l1
            12 -> R.drawable.block_l2
            13 -> R.drawable.block_l3
            14 -> R.drawable.block_l4
            15 -> R.drawable.block_3x3
            16 -> R.drawable.block_1x1
            17 -> R.drawable.block_1x2
            18 -> R.drawable.block_1x3
            19 -> R.drawable.block_2x2
            else -> R.drawable.block_3x3
        }
        brickImg1.setImageResource(drawableRes1)

        val drawableRes2 = when(brick2){
            1 -> R.drawable.block_1x1
            2 -> R.drawable.block_1x2
            3 -> R.drawable.block_2x1
            4 -> R.drawable.block_ml1
            5 -> R.drawable.block_ml2
            6 -> R.drawable.block_ml3
            7 -> R.drawable.block_ml4
            8 -> R.drawable.block_2x2
            9 -> R.drawable.block_1x3
            10 -> R.drawable.block_3x1
            11 -> R.drawable.block_l1
            12 -> R.drawable.block_l2
            13 -> R.drawable.block_l3
            14 -> R.drawable.block_l4
            15 -> R.drawable.block_3x3
            16 -> R.drawable.block_1x1
            17 -> R.drawable.block_1x2
            18 -> R.drawable.block_1x3
            19 -> R.drawable.block_2x2
            else -> R.drawable.block_3x3
        }
        brickImg2.setImageResource(drawableRes2)

        val drawableRes3 = when(brick3){
            1 -> R.drawable.block_1x1
            2 -> R.drawable.block_1x2
            3 -> R.drawable.block_2x1
            4 -> R.drawable.block_ml1
            5 -> R.drawable.block_ml2
            6 -> R.drawable.block_ml3
            7 -> R.drawable.block_ml4
            8 -> R.drawable.block_2x2
            9 -> R.drawable.block_1x3
            10 -> R.drawable.block_3x1
            11 -> R.drawable.block_l1
            12 -> R.drawable.block_l2
            13 -> R.drawable.block_l3
            14 -> R.drawable.block_l4
            15 -> R.drawable.block_3x3
            16 -> R.drawable.block_1x1
            17 -> R.drawable.block_1x2
            18 -> R.drawable.block_1x3
            19 -> R.drawable.block_2x2
            else -> R.drawable.block_3x3
        }
        brickImg3.setImageResource(drawableRes3)
    }
    fun blockCoords(clickX:Float, clickY:Float):FloatArray{
        Toast.makeText(this@GameScreenActivity, "PASSING ${clickX} x ${clickY}", Toast.LENGTH_SHORT).show()
        var xDist:Float = 900.0F
        var yDist:Float = 900.0F
        var pos:FloatArray = FloatArray(2)
        for (i in 0..5) {
            if (clickX - meshXCoords[i] < xDist && clickX - meshXCoords[i] >= 0) {
                pos[0] = meshXCoords[i].toFloat()
                xDist = clickX - meshXCoords[i]
                Toast.makeText(this@GameScreenActivity, "xDist ${xDist}", Toast.LENGTH_SHORT).show()
                for (j in 0..5) {
                    if (clickY - meshYCoords[j] < yDist && clickY - meshYCoords[j] >= 0) {
                        pos[1] = meshYCoords[j].toFloat()
                        yDist = clickY - meshYCoords[j]
                    }
                }
            }
        }
        return pos
    }

}