package com.example.level4task2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.level4task2.data.model.api.util.Resource
import com.example.level4task2.data.model.Movie
import com.example.level4task2.data.model.MovieInformation
import com.example.level4task2.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val _moviesRepository = MovieRepository()

    val movieResource: LiveData<Resource<MovieInformation>> get() = _movieResource

    private val _selectedMovie = MutableLiveData<Movie?>()
    val selectedMovie: LiveData<Movie?> = _selectedMovie

    private val _movieResource: MutableLiveData<Resource<MovieInformation>> = MutableLiveData(Resource.Empty())

    fun getMovie(query: String) {
        _movieResource.value = Resource.Loading()

        viewModelScope.launch {
            _movieResource.value = _moviesRepository.getRandomMovie(query)
        }
    }

    fun selectMovie(movie: Movie) {
        _selectedMovie.value = movie
    }
}