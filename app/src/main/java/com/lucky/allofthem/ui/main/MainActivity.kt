package com.lucky.allofthem.ui.main

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.lucky.allofthem.R
import com.lucky.allofthem.common.PermissionUtil
import com.lucky.allofthem.service.LocationService
import com.lucky.allofthem.ui.theme.AllOfThemTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val  notificationActivityLauncher: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGrant ->
        //포그라운드 서비스를 위해 알림 권한을 획득. 현재는 grant 유무 상관없이 위치권한 확인으로 바로 넘어가도록 함.
        checkLocation()
    }

    /**
     * Location 권한 요청
     */
    private val locationActivityLauncher: ActivityResultLauncher<Array<String>> = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { grants ->
        var isGrant = false
        grants.entries.forEach {
            if (it.value) {
                isGrant = true
            }
        }

        //위치 권한을 모두 획득한 이후에 백그라운드 위치정보 권한 확인
        if (isGrant) {
            checkBackgroundLocation()
        } else {
            showLocationPermissionToast()
        }
    }

    /**
     * Background Location 권한 요청
     */
    private val  backgroundLocationActivityLauncher: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGrant ->
        //백그라운드 권한이 없어도 포그라운드로 서비스가 계속 돌아가거나, 앱을 강제종료해도 당장 서비스를 동작하는 건 괜찮음
        //하지만 장시간 백그라운드에서 서비스가 정상적으로 동작하려면 백그라운드 권한이 필수임 (비정상적인데이터 또는 에러가 발생할 수 있음)
        checkLocationSettings()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        checkPermission()

        setContent {
            AllOfThemTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen()
                }
            }
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkNotification()
        } else {
            checkLocation()
        }
    }

    /**
     * 알림 권한 확인
     */
    private fun checkNotification() {
        if (PermissionUtil.checkNotification(activity = this@MainActivity)) {
            checkLocation()
        } else {
            notificationActivityLauncher.launch(
                PermissionUtil.PERMISSION_NOTIFICATION
            )
        }
    }

    /**
     * 위치 권한 확인
     */
    private fun checkLocation() {
        if (PermissionUtil.checkLocation(activity = this@MainActivity)) {
            checkBackgroundLocation()
        } else {
            //권한이 허용되지 않았을 때
            locationActivityLauncher.launch(
                PermissionUtil.PERMISSIONS_LOCATION
            )
        }
    }

    /**
     * 백그라운드 위치 권한 확인
     */
    private fun checkBackgroundLocation() {
        if (PermissionUtil.checkBackgroundLocation(activity = this@MainActivity)) {
            checkLocationSettings()
        } else {
            backgroundLocationActivityLauncher.launch(PermissionUtil.PERMISSION_BACKGROUND_LOCATION)
        }
    }

    /**
     * 위치 설정 확인
     */
    private fun checkLocationSettings() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGpsEnabled && !isNetworkEnabled) {
            showLocationSettingsDialog()
        } else {
            startLocationService()
        }
    }

    private fun showLocationPermissionToast() {
        Toast.makeText(this, getString(R.string.location_need_permission), Toast.LENGTH_SHORT).show()
    }

    private fun showBackgroundLocationPermissionToast() {
        Toast.makeText(this, getString(R.string.location_need_background_permission), Toast.LENGTH_SHORT).show()
    }

    private fun showLocationSettingsDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.location_setting))
            .setMessage(getString(R.string.location_need_setting))
            .setPositiveButton(getString(R.string.setting)) { _, _ ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    /**
     * 위치 서비스 (LocationService) 시작
     */
    private fun startLocationService() {
        ContextCompat.startForegroundService(
            this,
            Intent(this, LocationService::class.java)
        )
    }

}
