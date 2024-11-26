package com.example.flightsearch.di

import android.content.Context
import com.example.flightsearch.db.FlightDatabase
import com.example.flightsearch.db.AirportDao
import com.example.flightsearch.db.FavoriteDao
import com.example.flightsearch.repository.AirportRepository
import com.example.flightsearch.repository.AirportRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FlightDatabase {
        return FlightDatabase.getDatabase(context)
    }

    @Provides
    fun provideAirportDao(database: FlightDatabase): AirportDao {
        return database.airportDao()
    }

    @Provides
    fun provideFavoriteDao(database: FlightDatabase): FavoriteDao {
        return database.favoriteDao()
    }

    @Provides
    @Singleton
    fun provideAirportRepository(
        airportDao: AirportDao,
        favoriteDao: FavoriteDao
    ): AirportRepository {
        return AirportRepositoryImpl(airportDao, favoriteDao)
    }
}
