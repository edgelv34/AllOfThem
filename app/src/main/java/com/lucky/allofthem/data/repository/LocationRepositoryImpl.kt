package com.lucky.allofthem.data.repository

import android.util.Log
import com.lucky.allofthem.domain.model.Location
import com.lucky.allofthem.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class LocationRepositoryImpl(

): LocationRepository {
    private val _locationFlow = MutableSharedFlow<Location>(replay = 1)
    override val locationFlow: Flow<Location> get() = _locationFlow.asSharedFlow()

    override suspend fun emitLocation(location: android.location.Location) {
        val convertLocationFromGms = Location(
            latitude = location.latitude,
            longitude = location.longitude,
            timestamp = location.time
        )
        Log.d("Location", "현재 위치 : $convertLocationFromGms")
        _locationFlow.emit(convertLocationFromGms)
    }
}