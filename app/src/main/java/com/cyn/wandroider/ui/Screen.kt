package com.cyn.wandroider.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.cyn.wandroider.R

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/6
 *    desc   : 页面导航的数据
 */

const val ROUTE_HOME = "HomeScreen"
const val ROUTE_HOT = "HotScreen"
const val ROUTE_LOVE = "LoveScreen"
const val ROUTE_ME = "MeScreen"
const val ROUTE_LOGIN = "login"


sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val imageVector: ImageVector
){
    object Home: Screen(ROUTE_HOME, resourceId = R.string.home, Icons.Filled.Home)
    object Hot: Screen(ROUTE_HOT, resourceId = R.string.hot, Icons.Filled.List)
    object Love: Screen(ROUTE_LOVE, resourceId = R.string.love, Icons.Filled.Favorite)
    object Me: Screen(ROUTE_ME, resourceId = R.string.me, Icons.Filled.Menu)
}