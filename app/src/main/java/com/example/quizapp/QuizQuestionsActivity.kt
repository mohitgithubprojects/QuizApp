package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*
import java.util.ArrayList

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPos: Int = 1
    private lateinit var mQuestionsList: ArrayList<Question>
    private var mSelectedPos = 0
    private var mCorrectAnswers = 0
    private lateinit var mUserName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME).toString()

        mQuestionsList = Constants.getQuestions()

        setQuestion()

        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
        submitButton.setOnClickListener(this)

    }

    private fun setQuestion(){
        val question = mQuestionsList[mCurrentPos-1]

        defaultOptionsView()

        if (mCurrentPos == mQuestionsList.size)
            submitButton.text = "FINISH"
        else
            submitButton.text = "SUBMIT"

        progressBar.progress = mCurrentPos
        progressTV.text = "${mCurrentPos}/${progressBar.max}"

        questionTV.text = question!!.question
        questionIV.setImageResource(question.image)
        tv_option_one.text = question.optionOne
        tv_option_two.text = question.optionTwo
        tv_option_three.text = question.optionThree
        tv_option_four.text = question.optionFour
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        options.add(0,tv_option_one)
        options.add(1,tv_option_two)
        options.add(2,tv_option_three)
        options.add(3,tv_option_four)

        for (option in options){
            option.setTextColor(Color.parseColor("#7a8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_option_one -> {
                selectedOptionView(tv_option_one,1)
            }
            R.id.tv_option_two -> {
                selectedOptionView(tv_option_two,2)
            }
            R.id.tv_option_three -> {
                selectedOptionView(tv_option_three,3)
            }
            R.id.tv_option_four -> {
                selectedOptionView(tv_option_four,4)
            }
            R.id.submitButton -> {
                if (mSelectedPos==0){
                    mCurrentPos++

                    if (mCurrentPos<=mQuestionsList.size){
                        setQuestion()
                    }else{
                        val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra(Constants.USER_NAME, mUserName)
                        intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                        intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList.size)
                        startActivity(intent)
                        finish()
                    }
                }else{
                    val question = mQuestionsList[mCurrentPos-1]
                    if (question.correctAnswer != mSelectedPos){
                        answerView(mSelectedPos, R.drawable.uncorrect_option_bg)
                    }else{
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_bg)

                    if (mCurrentPos == mQuestionsList.size)
                        submitButton.text = "FINISH"
                    else
                        submitButton.text = "GO TO NEXT QUESTION"

                    mSelectedPos = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int){
        when(answer){
            1 -> {
                tv_option_one.setTextColor(Color.parseColor("#363A43"))
                tv_option_one.setTypeface(tv_option_one.typeface, Typeface.BOLD)
                tv_option_one.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            2 -> {
                tv_option_two.setTextColor(Color.parseColor("#363A43"))
                tv_option_two.setTypeface(tv_option_two.typeface, Typeface.BOLD)
                tv_option_two.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            3 -> {
                tv_option_three.setTextColor(Color.parseColor("#363A43"))
                tv_option_three.setTypeface(tv_option_three.typeface, Typeface.BOLD)
                tv_option_three.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            4 -> {
                tv_option_four.setTextColor(Color.parseColor("#363A43"))
                tv_option_four.setTypeface(tv_option_four.typeface, Typeface.BOLD)
                tv_option_four.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNumber: Int){
        defaultOptionsView()
        mSelectedPos = selectedOptionNumber

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }
}