package com.lucky.allofthem.domain.repository

import com.lucky.allofthem.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    val locationFlow: Flow<Location>
    suspend fun emitLocation(location: android.location.Location)
}