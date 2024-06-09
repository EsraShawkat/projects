package com.example.level5task2.utils

import android.content.Context
import com.example.level5task2.R
import com.example.level5task2.enums.PlayResult
import com.example.level5task2.enums.RockPaperScissors
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Utils {
    companion object {
        fun generateComputerMove(): RockPaperScissors {
            return RockPaperScissors.entries.random()
        }

        fun getGameResult(
            playerMove: RockPaperScissors,
            computerMove: RockPaperScissors
        ): PlayResult {
            return when {
                playerMove == computerMove -> PlayResult.DRAW
                playerMove == RockPaperScissors.ROCK && computerMove == RockPaperScissors.PAPER -> PlayResult.WIN
                playerMove == RockPaperScissors.PAPER && computerMove == RockPaperScissors.ROCK -> PlayResult.WIN
                playerMove == RockPaperScissors.SCISSORS && computerMove == RockPaperScissors.PAPER -> PlayResult.WIN
                else -> PlayResult.LOSE
            }
        }

        fun getImage(choice: RockPaperScissors?): Int? {
            return when (choice) {
                RockPaperScissors.ROCK -> R.drawable.rock
                RockPaperScissors.PAPER -> R.drawable.paper
                RockPaperScissors.SCISSORS -> R.drawable.scissors
                else -> null
            }
        }

        fun getGameResultMessage(result: PlayResult?, context: Context): String {
            return when (result) {
                PlayResult.WIN -> context.getString(R.string.win)
                PlayResult.DRAW -> context.getString(R.string.draw)
                PlayResult.LOSE -> context.getString(R.string.lose)
                else -> ""
            }
        }
    }
}