package com.example.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.model.Airport
import com.example.flightsearch.model.Favorite
import com.example.flightsearch.model.FavoriteWithAirportInfo
import com.example.flightsearch.repository.AirportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


@HiltViewModel
class FlightSearchViewModel @Inject constructor(
    private val airportRepository: AirportRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _searchResults = MutableStateFlow<List<Airport>>(emptyList())
    val searchResults: StateFlow<List<Airport>> = _searchResults

    private val _favorites = MutableStateFlow<List<FavoriteWithAirportInfo>>(emptyList())
    val favorites: StateFlow<List<FavoriteWithAirportInfo>> = _favorites

    init {
        loadFavorites()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        search(query)
    }

    private fun search(query: String) {
        viewModelScope.launch {
            airportRepository.searchAirports(query).collect { airports ->
                _searchResults.value = airports
            }
        }
    }

    fun toggleFavorite(favorite: Favorite) {
        viewModelScope.launch {
            airportRepository.toggleFavorite(favorite)
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            airportRepository.getAllFavorites().collect { favoriteList ->
                _favorites.value = favoriteList
            }
        }
    }
}