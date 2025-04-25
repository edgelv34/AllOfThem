package com.lucky.allofthem.ui.navigation

import kotlinx.serialization.Serializable

/**
 * Navigation 기능을 사용할 화면에 맞게 Router 생성하면 됌.
 * 현재는 예시로 Navigation 기능을 사용할 화면만 만들어 둠.
 */
sealed interface NaviRouter {

    @Serializable
    data object AScreen: NaviRouter

    @Serializable
    data object BScreen: NaviRouter

}
