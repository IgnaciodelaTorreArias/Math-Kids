package com.ignacio.android.mathskids

import kotlin.math.pow
import kotlin.random.Random

class Maths{
    private val simbolos = listOf(
        listOf( //Lista de simbolos nivel Basic
            "+",
            "-"
        ),
        listOf( //Lista de simbolos nivel Mid
            "*",
            "/"
        ),
        listOf( //Lista de simbolos nivel Pro
            "^",
            "√",
            "OR",
            "AND",
            "XOR",
            "NAND",
            "NOR",
            "XNOR"
        )
    )
    private var operator: String = "+"
    private var primary: Int = 1
    private var secondary: Int = 1
    private var rand = Random(1)

    var level:Int = 0
        set(value) {field = value%3}
    var seed: Long = 1
        set(value) {field=value; rand = Random(seed = value)}

    fun updateQuestion(): String{
        newSymbol()
        firstNumber()
        secondNumber()
        return "$primary $operator $secondary"
    }
    private fun newSymbol(){
        operator = simbolos[level].random(rand)
    }
    private fun firstNumber(){
        primary = if (level<2){
            rand.nextInt(11)
        }else when(operator){
            "OR", "AND", "XOR", "NAND", "NOR", "XNOR"-> Random.nextInt(2)
            "√"-> rand.nextInt(1,4)
            else-> rand.nextInt(11)
        }
    }
    private fun secondNumber(){
        secondary = if (level<2){
            rand.nextInt(11)
        }else when(operator){
            "OR", "AND", "XOR", "NAND", "NOR", "XNOR"-> Random.nextInt(2)
            "√"-> rand.nextInt(1,11)*primary
            "/"-> rand.nextInt(1, 11)
            "^"-> rand.nextInt(4)
            else-> rand.nextInt(11)
        }
    }
    fun checkAnswer(value: Int): Boolean{
        return when(operator){
            "+"->primary+secondary
            "-"->primary-secondary
            "*"->primary*secondary
            "/"->primary/secondary
            "^"->primary.toDouble().pow(secondary.toDouble()).toInt()
            "√"->secondary.toDouble().pow(1 / primary.toDouble()).toInt()
            else-> {
                val prim = primary>0
                val seco = secondary>0
                val correct:Boolean = when(operator){
                    "OR"-> prim || seco
                    "AND"-> prim && seco
                    "XOR"-> (prim || seco) && !(prim && seco)
                    "NAND"-> !(prim && seco)
                    "NOR"-> !(prim || seco)
                    else-> !(prim || seco) || (prim && seco)
                }
                if (correct) (if (value > 0) value else 1) else 0
            }
        } == value
    }
}