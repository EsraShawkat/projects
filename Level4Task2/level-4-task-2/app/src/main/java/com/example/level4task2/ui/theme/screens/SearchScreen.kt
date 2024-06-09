package com.example.level4task2.ui.theme.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import com.example.level4task2.R
import com.example.level4task2.data.model.api.util.Resource
import com.example.level4task2.viewmodel.MovieViewModel
import androidx.navigation.NavHostController
import com.example.level4task2.data.model.Movie


@Composable
fun SearchScreen(viewModel: MovieViewModel, navController: NavHostController) {
    val movieResource by viewModel.movieResource.observeAsState()

    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            ) {
                SearchView { query ->
                    viewModel.getMovie(query)
                }

                Spacer(modifier = Modifier.height(16.dp))

                movieResource?.data?.results?.let { movies ->
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 24.dp),
                        columns = GridCells.Adaptive(128.dp),
                        content = {
                            items(movies) { movie ->
                                MovieItem(navController, movie, viewModel)
                            }
                        }
                    )
                }
                if (movieResource is Resource.Loading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    )
}


@Composable
fun MovieItem(navController: NavHostController, movie: Movie, viewModel: MovieViewModel) {
    Column {
        Image(
            painter = rememberImagePainter(
                data = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
            ),
            contentDescription = movie.title,
            modifier = Modifier
                .height(300.dp)
                .clickable {
                    navController.navigate(MovieScreens.InfoScreen.route) {
                        viewModel.selectMovie(movie)
                    }
                }
        )
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchView(searchTMDB: (String) -> Unit) {
    val searchQueryState = rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(String()))
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = searchQueryState.value,
        onValueChange = { value ->
            searchQueryState.value = value
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(fontSize = 18.sp),
        leadingIcon = {
            if (searchQueryState.value != TextFieldValue(String())) {
                IconButton(
                    onClick = {
                        searchQueryState.value =
                            TextFieldValue(String()) // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Remove search argument",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        trailingIcon = {
            IconButton(onClick = {
                searchTMDB(searchQueryState.value.text)
                keyboardController?.hide()
            }) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search for movies in TMDB based on search argument provided",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp),
                )
            }
        },
        placeholder = {
            Text(
                text = stringResource(R.string.search)
            )
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
    )
}

