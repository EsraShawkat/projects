package com.example.level5task2.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.example.level5task2.R
import com.example.level5task2.data.model.Play
import com.example.level5task2.enums.PlayResult
import com.example.level5task2.enums.RockPaperScissors
import com.example.level5task2.utils.Utils
import com.example.level5task2.viewmodel.PlayViewModel
import java.util.Date

@Composable
fun PlayScreen(viewModel: PlayViewModel) {
    val game = remember {
        mutableStateOf<Play?>(null)
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        Text(
            stringResource(R.string.instruction),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = 20.dp)
        )

        StatisticsDisplay(
            viewModel.getTotalWins(),
            viewModel.getTotalDraws(),
            viewModel.getTotalLosses(),
        )

        GameDisplay(game)

        ImgButton { play ->
            game.value = play
            viewModel.insertPlay(play)
        }
    }
}


@Composable
private fun StatisticsDisplay(
    winCount: LiveData<Int>,
    drawCount: LiveData<Int>,
    loseCount: LiveData<Int>
) {
    val winCountState = winCount.observeAsState()
    val drawCountState = drawCount.observeAsState()
    val loseCountState = loseCount.observeAsState()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            stringResource(R.string.statistics), style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            String.format(
                "%s: %d, - %s: %d - %s: %d",
                PlayResult.WIN,
                winCountState.value,
                PlayResult.DRAW,
                drawCountState.value,
                PlayResult.LOSE,
                loseCountState.value,
            ), style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun GameDisplay(game: MutableState<Play?>) {
    val context = LocalContext.current
    val resultText = Utils.getGameResultMessage(game.value?.result, context)
    val computerChoice = Utils.getImage(game.value?.computerMove)
    val playerChoice = Utils.getImage(game.value?.playerMove)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = resultText,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        GameResult(computerChoice = computerChoice, playerChoice = playerChoice)
    }
}


@Composable
private fun ImgButton(
    getGame: (Play) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
        FloatingActionButton(onClick = { getGame(handleChoice(RockPaperScissors.ROCK)) }) {
            Image(painterResource(id = R.drawable.rock), contentDescription = "rock")
        }

        FloatingActionButton(onClick = { getGame(handleChoice(RockPaperScissors.PAPER)) }) {
            Image(painterResource(id = R.drawable.paper), contentDescription = "paper")
        }

        FloatingActionButton(onClick = { getGame(handleChoice(RockPaperScissors.SCISSORS)) }) {
            Image(painterResource(id = R.drawable.scissors), contentDescription = "scissors")
        }
    }
}

private fun handleChoice(playerChoice: RockPaperScissors): Play {
    val computerChoice = Utils.generateComputerMove()
    val gameResult = Utils.getGameResult(playerChoice, computerChoice)
    return Play(
        date = Date(),
        playerMove = playerChoice,
        computerMove = computerChoice,
        result = gameResult,
    )
}

@Composable
fun GameResult(computerChoice: Int?, playerChoice: Int?) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (computerChoice != null) {
                ImgCard(
                    cardImage = computerChoice,
                    contentDescription = "Computer"
                )
            }
            Text(text = stringResource(id = R.string.computer))
        }

        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stringResource(id = R.string.VS),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (playerChoice != null) {
                ImgCard(cardImage = playerChoice, contentDescription = "Player")
            }
            Text(text = stringResource(id = R.string.you))
        }
    }
}


@Composable
private fun ImgCard(cardImage: Int, contentDescription: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Image(
            painter = painterResource(id = cardImage),
            contentDescription = contentDescription,
            modifier = Modifier.size(110.dp)
        )
    }
}