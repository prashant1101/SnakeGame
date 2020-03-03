package com.example.snakegame

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import java.util.*


class MainActivity : AppCompatActivity() {

    private var parentLayout: LinearLayout? = null
    private val n = 10
    private val buttons: Array<Array<Button?>> = Array (n) { Array<Button?> (n) {null} }
    private var currentValue = 0
    private var rollDice: Button? = null
    private var resetGame: Button? = null
    private var diceValueView: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        parentLayout = findViewById(R.id.parent)

        for (i in 0 until n) {
            val layout = LinearLayout(this)
            layout.orientation = LinearLayout.HORIZONTAL
            for (j in 0 until n) {
                setButton(layout, i, j)
            }
            parentLayout?.addView(layout)
        }
        setRandomValue()
        buttons[0][0]?.setBackgroundColor(Color.GREEN)


        rollDice = Button(this)
        rollDice?.text = getString(R.string.DiceButtonText)
        rollDice?.setOnClickListener {
            diceRolled()
        }

        resetGame = Button(this)
        resetGame?.text = getString(R.string.ResetButtonText)
        resetGame?.setOnClickListener {
            resetGame()
        }

        val layout = LinearLayout(this)
        val diceText = TextView(this)
        diceText.text = getString(R.string.diceText)
        diceValueView = TextView(this)
        diceValueView?.text = "0"
        layout.addView(diceText)
        layout.addView(diceValueView)

        parentLayout?.addView(rollDice)
        parentLayout?.addView(resetGame)
        parentLayout?.addView(layout)
    }

    private fun resetGame() {
//        currentValue = 0
//        for (i in 0 until n) {
//            for (j in 0 until n) {
//                buttons[i][j]?.text = "0"
//            }
//        }
//        setRandomValue()
//        buttons[currentValue/n][currentValue%n]?.setBackgroundColor(Color.GRAY)
//        buttons[0][0]?.setBackgroundColor(Color.GREEN)
//        diceValueView?.text = "0"


    }

    private fun diceRolled() {
        val value = Random().nextInt(6) + 1
        Log.d("CheckValue", "" + value)
        diceValueView?.text = "$value"
        var nextValue = currentValue + value
        var i = nextValue/n
        var j = nextValue%n
        while (true) {
            if (i >= n || i < 0 || j < 0){
                Toast.makeText(this, getString(R.string.ValueExceedText), Toast.LENGTH_SHORT).show()
                return
            }
            val buttonValue = buttons[i][j]?.text.toString().toInt()
            if (buttonValue == 0)
                break

            nextValue += buttonValue
            i = nextValue/n
            j = nextValue%n
        }

        buttons[currentValue/n][currentValue%n]?.setBackgroundColor(Color.GRAY)
        buttons[i][j]?.setBackgroundColor(Color.GREEN)

        currentValue = nextValue

        if (currentValue >= n*n - 1)
        {
            Toast.makeText(this, getString(R.string.GameFinishedText), Toast.LENGTH_SHORT).show()
            rollDice?.isEnabled = false
        }


    }

    private fun setRandomValue() {
        val randomValues =  (n*n * (0.2f)).toInt()
        for (x in 0 until randomValues) {
            val randomNum = Random().nextInt(n*n - 2) + 1
            val i = randomNum/n
            val j = randomNum%n
            var randomVal = generateRandomVal()
            var cycle = checkCycle(randomVal, randomNum)
            while (cycle) {
                randomVal = generateRandomVal()
                cycle = checkCycle(randomVal, randomNum)
            }
            buttons[i][j]?.text = "$randomVal"
        }
    }

    private fun generateRandomVal() : Int {
        return Random().nextInt(10) - 5
    }

    private fun checkCycle(randomVal: Int, randomNum: Int): Boolean {
        var nextValue = randomNum + randomVal
        while (nextValue != randomNum) {
            val x = nextValue/n
            val y = nextValue%n

            if (x >= n || x < 0 || y < 0)
                return false
            val buttonValue = buttons[x][y]?.text.toString().toInt()
            if (buttonValue == 0) {
                return false
            }
            nextValue += buttonValue
        }
        return true
    }


    private fun setButton(layout: LinearLayout, i: Int, j: Int) {
        val button = Button(this)
        val buttonParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        buttonParams.weight = 1f
        button.text = "0"
        button.setBackgroundColor(Color.GRAY)
        button.layoutParams = buttonParams
        buttons[i][j] = button
        layout.addView(buttons[i][j])
    }
}
