package com.lucky.allofthem.common

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

object PermissionUtil {

    /**
     * 카메라 권한
     */
    val PERMISSIONS_CAMERA = Manifest.permission.CAMERA

    /**
     * 위치 권한
     */
    val PERMISSIONS_LOCATION =
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    /**
     * Q 이하 저장소 권한
     */
    private val PERMISSIONS_STORAGE =
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

    /**
     * Q 이상 저장소 권한
     */
    private val PERMISSIONS_STORAGE_Q =
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

    /**
     * TIRAMISU 이상 저장소 권한
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val PERMISSIONS_STORAGE_T_GALLERY =
        arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO
        )

    /**
     * UPSIDE_DOWN_CAKE 이상 저장소 권한
     */
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private val PERMISSIONS_STORAGE_U_GALLERY =
        arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
        )


    /**
     * 다수의 permission check
     * @see Activity.checkSelfPermission
     * @return true : permissions 모든 권한이 있을 경우, false : 한 개라도 권한이 없을 경우 (GRANTED 되지 않은 경우)
     */
    @JvmStatic
    fun hasSelfPermission(activity: Activity, permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    /**
     * 권한이 있는 경우
     * @see Activity.checkSelfPermission
     * @return true : 권한이 있을 경우, false : 권한이 없을 경우 (GRANTED 되지 않은 경우)
     */
    @JvmStatic
    fun hasSelfPermission(activity: Activity, permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Image, Video 권한을 요청하기위한 권한 리스트
     */
    @JvmStatic
    fun requireMediaStoragePermission(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            PERMISSIONS_STORAGE_U_GALLERY
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            PERMISSIONS_STORAGE_T_GALLERY
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            PERMISSIONS_STORAGE_Q
        } else {
            PERMISSIONS_STORAGE
        }
    }

    /**
     * Image, Video 권한이 있는지 확인
     *
     * UPSIDE_DOWN_CAKE 이상 일 경우 READ_MEDIA_VISUAL_USER_SELECTED 추가
     * READ_MEDIA_VISUAL_USER_SELECTED 가 true 일 경우 media (image, video) 는 false 일 수 있음.
     * 반대로  media (image, video) 가 true 일 경우 READ_MEDIA_VISUAL_USER_SELECTED 가 false 일 수 있음.
     * UPSIDE_DOWN_CAKE 이상 일 경우 체크 로직 -> READ_MEDIA_VISUAL_USER_SELECTED || (READ_MEDIA_IMAGES && READ_MEDIA_VIDEO)
     */
    @JvmStatic
    fun checkMediaStorage(activity: Activity): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            hasSelfPermission(
                activity,
                Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
            ) || hasSelfPermission(
                activity,
                PERMISSIONS_STORAGE_T_GALLERY
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            hasSelfPermission(activity, PERMISSIONS_STORAGE_T_GALLERY)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            hasSelfPermission(activity, PERMISSIONS_STORAGE_Q)
        } else {
            hasSelfPermission(activity, PERMISSIONS_STORAGE)
        }
    }


}