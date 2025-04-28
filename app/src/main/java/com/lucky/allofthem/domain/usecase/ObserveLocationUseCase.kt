package com.lucky.allofthem.domain.usecase

import com.lucky.allofthem.domain.model.Location
import com.lucky.allofthem.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    operator fun invoke(): Flow<Location> {
        return locationRepository.locationFlow
    }
}