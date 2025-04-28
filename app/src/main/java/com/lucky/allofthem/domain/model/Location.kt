package com.lucky.allofthem.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long
): Parcelable
