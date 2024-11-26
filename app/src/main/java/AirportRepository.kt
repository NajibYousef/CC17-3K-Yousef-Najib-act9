package com.example.flightsearch.repository

import com.example.flightsearch.db.AirportDao
import com.example.flightsearch.db.FavoriteDao
import com.example.flightsearch.model.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AirportRepository {
    fun searchAirports(query: String): Flow<List<Airport>>
    fun getAllFavorites(): Flow<List<FavoriteWithAirportInfo>>
    suspend fun toggleFavorite(favorite: Favorite)
}

class AirportRepositoryImpl @Inject constructor(
    private val airportDao: AirportDao,
    private val favoriteDao: FavoriteDao
) : AirportRepository {
    override fun searchAirports(query: String): Flow<List<Airport>> =
        airportDao.searchAirports("%$query%")

    override fun getAllFavorites(): Flow<List<FavoriteWithAirportInfo>> =
        favoriteDao.getAllFavoritesWithAirportInfo()

    override suspend fun toggleFavorite(favorite: Favorite) {
        val existing = favoriteDao.getFavorite(favorite.departureCode, favorite.destinationCode)
        if (existing == null) {
            favoriteDao.insert(favorite)
        } else {
            favoriteDao.delete(existing)
        }
    }
}