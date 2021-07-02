package com.example.android.guesstheword.screens.game

import android.content.IntentSender
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val timer:CountDownTimer

    companion object {
        // game over time
        private const val  DONE = 0L
        //countdown time interval
        private const val ONE_SECOND = 1000L
        //total time for the game
        private const val  COUNTDOWN_TIME = 60000L
    }

    //store countdown time of timer
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
    get() = _currentTime

    val currentTimeString  = Transformations.map(currentTime){
        time -> DateUtils.formatElapsedTime(time)
    }


    // The current word
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
    get() = _word
    // The current score
   private val _score = MutableLiveData<Int>()
    //public version of liveData type called score
    val score: LiveData<Int>
    // make the score accesible in Game Fragment by setting a getter
        get() = _score


    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    lateinit var wordList: MutableList<String>
     val WordHint = Transformations.map(word){
         word-> val randomPosition = (1..word.length).random()
         "Current word has " + word.length + " letters" +
                 "\nThe Letter at position  " + randomPosition + " is" +
                 word.get(randomPosition -1).toUpperCase()
     }

    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }
    //viewmodel is lifecycle aware thus init
    init {
        Log.i("GameViewModel", "GameViewModel created")
        // wordlist is reset when viewModel is created as opposed to when fragment is created
        //initialize score and word
        _word.value = ""
        _score.value = 0
        resetList()
        nextWord()

        //initialize and start timer
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND){
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished/ ONE_SECOND
            }

            override fun onFinish() {
                _currentTime.value = DONE
                onGameFinish()
            }
        }
        timer.start()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        if (wordList.isEmpty()) {
            resetList()
        }else{

            //Select and remove a word from the list
            _word.value = wordList.removeAt(0)
        }

    }
     fun onSkip() {
        _score.value =(score.value)?.minus(1)
        nextWord()
    }

 fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    override fun onCleared() {
                super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
        timer.cancel()
    }
    fun onGameFinish(){
        _eventGameFinish.value = true
    }
    fun onGameFinishComplete(){
        _eventGameFinish.value= false
    }
}