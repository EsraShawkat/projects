package com.example.level4task2.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.level4task2.viewmodel.MovieViewModel
import com.example.level4task2.data.model.Movie
import com.example.level4task2.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InfoScreen(
    viewModel: MovieViewModel
) {
    val selectedMovie by viewModel.selectedMovie.observeAsState()

    Scaffold(
        content = {
            selectedMovie?.let { movie ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Image(
                            painter = rememberImagePainter(
                                data = "https://image.tmdb.org/t/p/w500${movie.backdropPath}",
                            ),
                            contentDescription = movie.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        ) {
                            Image(
                                painter = rememberImagePainter(
                                    data = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                                ),
                                contentDescription = movie.title,
                                modifier = Modifier
                                    .height(200.dp)
                                    .clip(shape = RoundedCornerShape(8.dp))
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(
                                    text = movie.originalTitle,
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = movie.getFormattedRating())
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = movie.date)
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(30.dp))
                        Text(
                            text = stringResource(id = R.string.overview),
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                        Text(
                            text = movie.overview,
                            Modifier.padding(20.dp)
                        )
                    }
                }
            }
        }
    )
}

fun Movie.getFormattedRating(): String {
    return String.format("%.1f", votes)
}