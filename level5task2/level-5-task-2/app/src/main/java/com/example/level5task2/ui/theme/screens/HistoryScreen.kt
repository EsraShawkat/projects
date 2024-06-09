package com.example.level5task2.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.level5task2.data.model.Play
import com.example.level5task2.utils.Utils
import com.example.level5task2.viewmodel.PlayViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HistoryScreen(
    viewModel: PlayViewModel,
    navController: NavController
) {
    val plays by viewModel.getAllPlays.observeAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.deleteAllPlays() },
                modifier = Modifier.padding(16.dp),
                content = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete all games",
                        tint = Color.Black
                    )
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(start = 35.dp),
        ) {
            if (plays != null) {
                items(items = plays!!, key = { it.id }) { play ->
                    PlayHistory(play)
                }
            }
        }
    }
}


@Composable
private fun PlayHistory(play: Play) {
    val context = LocalContext.current
    val playResult = Utils.getGameResultMessage(play.result, context)
    val computerMove = Utils.getImage(play.computerMove)
    val playerMove = Utils.getImage(play.playerMove)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(playResult, style = MaterialTheme.typography.headlineSmall)

        Text(play.date.toString(), style = MaterialTheme.typography.bodySmall)
        GameResult(computerMove, playerMove)
    }
    Divider(thickness = 1.dp, modifier = Modifier.padding(top = 16.dp))
}
