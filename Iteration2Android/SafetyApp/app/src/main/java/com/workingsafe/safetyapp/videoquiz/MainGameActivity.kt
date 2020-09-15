package com.workingsafe.safetyapp.videoquiz

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import info.hoang8f.widget.FButton
import java.util.*

import com.workingsafe.safetyapp.R

class MainGameActivity : AppCompatActivity() {
    var buttonA: FButton? = null
    var buttonB: FButton? = null
    var buttonC: FButton? = null
    var buttonD: FButton? = null
    var triviaQuizText: TextView? = null
    var timeText: TextView? = null
    var coinText: TextView? = null
    var videoPath: TextView? = null
    private var triviaQuizHelper: TriviaQuizHelper? = null
    var videoView: VideoView? = null
    var imageView: SubsamplingScaleImageView? = null
    var currentQuestion: TriviaQuestion? = null
    var list: List<TriviaQuestion>? = null
    var qid = 0
    var timeValue = 60
    var coinValue = 0
    var countDownTimer: CountDownTimer? = null
    var tb: Typeface? = null
    var sb: Typeface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_main)

        //Initializing variables
        videoView = findViewById<View>(R.id.myVideoView) as VideoView
        imageView = findViewById<View>(R.id.imageView) as SubsamplingScaleImageView
        buttonA = findViewById<View>(R.id.buttonA) as FButton
        buttonB = findViewById<View>(R.id.buttonB) as FButton
        buttonC = findViewById<View>(R.id.buttonC) as FButton
        buttonD = findViewById<View>(R.id.buttonD) as FButton
        triviaQuizText = findViewById<View>(R.id.triviaQuizText) as TextView
        timeText = findViewById<View>(R.id.timeText) as TextView
        coinText = findViewById<View>(R.id.coinText) as TextView

        //Setting typefaces for textview and buttons - this will give stylish fonts on textview and button etc
        tb = Typeface.createFromAsset(assets, "fonts/TitilliumWeb-Bold.ttf")
        sb = Typeface.createFromAsset(assets, "fonts/shablagooital.ttf")
        triviaQuizText!!.typeface = sb
        //videoPath.setTypeface(sb);
        buttonA!!.typeface = tb
        buttonB!!.typeface = tb
        buttonC!!.typeface = tb
        buttonD!!.typeface = tb
        timeText!!.typeface = tb
        coinText!!.typeface = tb

        //Our database helper class
        triviaQuizHelper = TriviaQuizHelper(this)
        //Make db writable
        triviaQuizHelper!!.writableDatabase

        //It will check if the ques,options are already added in table or not
        //If they are not added then the getAllOfTheQuestions() will return a list of size zero
        if (triviaQuizHelper!!.allOfTheQuestions.size == 0) {
            //If not added then add the ques,options in table
            triviaQuizHelper!!.allQuestion()
        }

        //This will return us a list of data type TriviaQuestion
        list = triviaQuizHelper!!.allOfTheQuestions

        //Now we gonna shuffle the elements of the list so that we will get questions randomly
        Collections.shuffle(list)


        //currentQuestion will hold the question,video path 4 option and answer for particular id
        currentQuestion = list!![qid]

        //countDownTimer
        countDownTimer = object : CountDownTimer(47000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                //here you can have your logic to set text to timeText
                timeText!!.text = timeValue.toString() + "\""

                //With each iteration decrement the time by 1 sec
                timeValue -= 1

                //This means the user is out of time so onFinished will called after this iteration
                if (timeValue == -1) {

                    //Since user is out of time setText as time up
                   // resultText!!.text = getString(R.string.timeup)

                    //Since user is out of time he won't be able to click any buttons
                    //therefore we will disable all four options buttons using this method
                    disableButton()
                }
            }

            //Now user is out of time
            override fun onFinish() {
                //We will navigate him to the time up activity using below method
                timeUp()
            }
        }.start()

        //This method will set the que and four options
        updateQueAndOptions()
    }

    fun updateQueAndOptions() {
        val m = MediaController(this)
        val showImage = currentQuestion?.id==3 || currentQuestion?.id==7;
        videoView?.setMediaController(m)
        imageView?.visibility=if (showImage) View.VISIBLE else View.GONE
        videoView?.visibility=if (showImage) View.GONE else View.VISIBLE
        //This method will setText for question, video and options
        //questionText!!.text = currentQuestion!!.question
        //videoView.setVideoURI(Uri.parse(currentQuestion.getVideo()));
        if (!showImage) {
            val uri = Uri.parse(currentQuestion?.video);
            videoView?.setVideoURI(uri)
            videoView?.start()
        } else {
            imageView?.setImage(ImageSource.resource((currentQuestion!!.image)))
        }

//        videoView!!.setOnPreparedListener { mp ->
//            mp.isLooping = true
//            videoView!!.start()
//        }
        buttonA!!.text = currentQuestion!!.optA
        buttonB!!.text = currentQuestion!!.optB
        buttonC!!.text = currentQuestion!!.optC
        buttonD!!.text = currentQuestion!!.optD
        timeValue = 60

        //Now since the user has ans correct just reset timer back for another que- by cancel and start
        countDownTimer!!.cancel()
        countDownTimer!!.start()

        //set the value of coin text
        coinText!!.text = coinValue.toString()
        //Now since user has ans correct increment the coinvalue
        coinValue++
    }

    //Onclick listener for first button
    fun buttonA(view: View?) {
        //compare the option with the ans if yes then make button color green
        if (currentQuestion!!.optA == currentQuestion!!.answer) {
            buttonA!!.buttonColor = ContextCompat.getColor(applicationContext, R.color.lightGreen)
            //Check if user has not exceeds the que limit
            if (qid < list!!.size - 1) {

                //Now disable all the option button since user ans is correct so
                //user won't be able to press another option button after pressing one button
                disableButton()

                //Show the dialog that ans is correct
                correctDialog()
            } else {
                gameWon()
            }
        } else {
            gameLostPlayAgain()
        }
    }

    //Onclick listener for sec button
    fun buttonB(view: View?) {
        if (currentQuestion!!.optB == currentQuestion!!.answer) {
            buttonB!!.buttonColor = ContextCompat.getColor(applicationContext, R.color.lightGreen)
            if (qid < list!!.size - 1) {
                disableButton()
                correctDialog()
            } else {
                gameWon()
            }
        } else {
            gameLostPlayAgain()
        }
    }

    //Onclick listener for third button
    fun buttonC(view: View?) {
        if (currentQuestion!!.optC == currentQuestion!!.answer) {
            buttonC!!.buttonColor = ContextCompat.getColor(applicationContext, R.color.lightGreen)
            if (qid < list!!.size - 1) {
                disableButton()
                correctDialog()
            } else {
                gameWon()
            }
        } else {
            gameLostPlayAgain()
        }
    }

    //Onclick listener for fourth button
    fun buttonD(view: View?) {
        if (currentQuestion!!.optD == currentQuestion!!.answer) {
            buttonD!!.buttonColor = ContextCompat.getColor(applicationContext, R.color.lightGreen)
            if (qid < list!!.size - 1) {
                disableButton()
                correctDialog()
            } else {
                gameWon()
            }
        } else {
            gameLostPlayAgain()
        }
    }

    //This method will navigate from current activity to GameWon
    fun gameWon() {
        val intent = Intent(this, GameWon::class.java)
        startActivity(intent)
        finish()
    }

    //This method is called when user ans is wrong
    //this method will navigate user to the activity PlayAgain
    fun gameLostPlayAgain() {
        val intent = Intent(this, PlayAgain::class.java)
        startActivity(intent)
        finish()
    }

    //This method is called when time is up
    //this method will navigate user to the activity Time_Up
    fun timeUp() {
        val intent = Intent(this, Time_Up::class.java)
        startActivity(intent)
        finish()
    }

    //If user press home button and come in the game from memory then this
    //method will continue the timer from the previous time it left
    override fun onRestart() {
        super.onRestart()
        countDownTimer!!.start()
    }

    //When activity is destroyed then this will cancel the timer
    override fun onStop() {
        super.onStop()
        countDownTimer!!.cancel()
    }

    //This will pause the time
    override fun onPause() {
        super.onPause()
        countDownTimer!!.cancel()
    }

    //On BackPressed
    override fun onBackPressed() {
        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
        finish()
    }

    //This dialog is show to the user after he ans correct
    fun correctDialog() {
        val dialogCorrect = Dialog(this@MainGameActivity)
        dialogCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (dialogCorrect.window != null) {
            val colorDrawable = ColorDrawable(Color.TRANSPARENT)
            dialogCorrect.window!!.setBackgroundDrawable(colorDrawable)
        }
        dialogCorrect.setContentView(R.layout.dialog_correct)
        dialogCorrect.setCancelable(false)
        dialogCorrect.show()

        //Since the dialog is show to user just pause the timer in background
        onPause()
        val correctText = dialogCorrect.findViewById<View>(R.id.correctText) as TextView
        val buttonNext = dialogCorrect.findViewById<View>(R.id.dialogNext) as FButton

        //Setting type faces
        correctText.typeface = sb
        buttonNext.typeface = sb

        //OnCLick listener to go next que
        buttonNext.setOnClickListener { //This will dismiss the dialog
            dialogCorrect.dismiss()
            //it will increment the question number
            qid++
            //get the que and 4 option and store in the currentQuestion
            currentQuestion = list!![qid]
            //Now this method will set the new que and 4 options
            updateQueAndOptions()
            //reset the color of buttons back to white
            resetColor()
            //Enable button - remember we had disable them when user ans was correct in there particular button methods
            enableButton()
        }
    }

    //This method will make button color white again since our one button color was turned green
    fun resetColor() {
        buttonA!!.buttonColor = ContextCompat.getColor(applicationContext, R.color.white)
        buttonB!!.buttonColor = ContextCompat.getColor(applicationContext, R.color.white)
        buttonC!!.buttonColor = ContextCompat.getColor(applicationContext, R.color.white)
        buttonD!!.buttonColor = ContextCompat.getColor(applicationContext, R.color.white)
    }

    //This method will disable all the option button
    fun disableButton() {
        buttonA!!.isEnabled = false
        buttonB!!.isEnabled = false
        buttonC!!.isEnabled = false
        buttonD!!.isEnabled = false
    }

    //This method will all enable the option buttons
    fun enableButton() {
        buttonA!!.isEnabled = true
        buttonB!!.isEnabled = true
        buttonC!!.isEnabled = true
        buttonD!!.isEnabled = true
    }
}