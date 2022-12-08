package com.ignacio.android.mathskids

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset

private const val LEVEL_KEY = "LEVEL_KEY"
private const val POINTS_KEY = "POINTS_KEY"

class GameViewModel(private val savedStateHandle: SavedStateHandle): ViewModel(){
    val logic: Maths = Maths()
    var level:Int
        get() = savedStateHandle[LEVEL_KEY]?: 1
        set(value) {logic.level = value; savedStateHandle[LEVEL_KEY] = logic.level }
    var points: Int
        get() = savedStateHandle[POINTS_KEY]?: 0
        set(value) = savedStateHandle.set(POINTS_KEY, value)
    init {
        logic.seed = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
    }
    fun checkAnswer(answer: Int): Boolean{
        if(logic.checkAnswer(answer)){
            points++
            return true
        }
        return false
    }
    fun newProblem():String{
        return logic.updateQuestion()
    }
}