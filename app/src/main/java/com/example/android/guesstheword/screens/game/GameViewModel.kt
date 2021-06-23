package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
    get() = _eventGameFinish

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

    lateinit var wordList: MutableList<String>

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
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        if (!wordList.isEmpty()) {
            onGameFinish()
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
    }
    fun onGameFinish(){
        _eventGameFinish.value = true
    }
    fun onGameFinishComplete(){
        _eventGameFinish.value= false
    }
}