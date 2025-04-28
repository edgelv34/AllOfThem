package com.lucky.allofthem.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.lucky.allofthem.R
import com.lucky.allofthem.domain.repository.LocationRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * LifecycleService 를 사용하기때문에 따로 service 를 stop 하면서 job 을 제거하지 않아도 됌.
 */
@AndroidEntryPoint
class LocationService: LifecycleService() {

    @Inject
    lateinit var locationRepository: LocationRepository

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val cancellationTokenSource = CancellationTokenSource()
    private var requestCurrentLocationTime = 0L

    companion object {
        const val CHANNEL_ID = "location_service_channel"
        const val NOTIFICATION_ID = 1
        const val LOCATION_MIN_INTERVAL = 30 * 1000L
        const val LOCATION_MAX_INTERVAL = 60 * 1000L
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onCreate() {
        super.onCreate()
        Log.d("LocationService", "서비스 시작")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        startForegroundService()
        //최초 한번만 현재 위치정보 획득
        getCurrentLocation()
        //주기에 따라 위치정보 획득
        startLocationUpdates()
    }

    private fun startForegroundService() {
        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("AllOfThem")
            .setContentText("위치정보 받아오는 중")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Location Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.setShowBadge(false)
        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun getCurrentLocation() {
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.token)
            .addOnSuccessListener { location ->
                location?.let {
                    if (Math.abs(location.time - requestCurrentLocationTime) < 5000) {
                        return@let
                    }

                    lifecycleScope.launch {
                        requestCurrentLocationTime = location.time
                        locationRepository.emitLocation(location)
                    }
                }
            }
    }


    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun startLocationUpdates() {
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, LOCATION_MIN_INTERVAL)
            .apply {
                setMinUpdateIntervalMillis(LOCATION_MIN_INTERVAL)
                setMaxUpdateDelayMillis(LOCATION_MAX_INTERVAL)
                setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                setWaitForAccurateLocation(true)
            }.build()

        fusedLocationClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            result.lastLocation?.let { location ->
                if (Math.abs(location.time - requestCurrentLocationTime) < 5000) {
                    return
                }
                lifecycleScope.launch {
                    requestCurrentLocationTime = location.time
                    locationRepository.emitLocation(location)
                }
            }
        }
    }
}