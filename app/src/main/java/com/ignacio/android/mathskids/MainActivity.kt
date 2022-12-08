package com.ignacio.android.mathskids

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import androidx.activity.viewModels
import com.ignacio.android.mathskids.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when(gameViewModel.level) {
            1 -> binding.level1.isChecked = true
            2 -> binding.level2.isChecked = true
            else -> binding.level3.isChecked = true
        }
        binding.points.text = gameViewModel.points.toString()
        binding.problem.text = gameViewModel.newProblem()

        binding.apply {
            buttonCheck.setOnClickListener{ view->
                try {
                    snack(gameViewModel.checkAnswer(respuesta.text.toString().toInt()),view)
                    points.text = gameViewModel.points.toString()
                    problem.text = gameViewModel.newProblem()
                    respuesta.setText("")
                }catch(e:NumberFormatException){
                    snack(null, view)
                }
            }
            respuesta.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    buttonCheck.callOnClick()
                    return@OnKeyListener true
                }
                false
            })
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                gameViewModel.level = checkedId
                problem.text = gameViewModel.newProblem()
            }
        }
    }
    private fun snack(answer: Boolean?, view: View){
        val message: String
        val color: Int
        when(answer){
            true->{message = getString(R.string.correctAnswer);color=R.color.green}
            false->{message = getString(R.string.incorrectAnswer);color=R.color.red}
            else->{message = getString(R.string.badAnswer);color=R.color.yellow}
        }
        val mySnack = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        val params = mySnack.view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        mySnack.view.layoutParams = params
        mySnack.setBackgroundTint(getColor(color))
        mySnack.show()
    }
}