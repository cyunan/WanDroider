package com.cyn.wandroider.ui.page

import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cyn.wandroider.ui.*
import com.cyn.wandroider.ui.page.home.HomePage
import com.cyn.wandroider.ui.page.hot.HotPage
import com.cyn.wandroider.ui.page.login.LoginPage
import com.cyn.wandroider.ui.page.login.LoginViewModel
import com.cyn.wandroider.ui.page.love.LovePage
import com.cyn.wandroider.ui.page.me.MePage
import com.cyn.wandroider.ui.theme.AppTheme

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/6
 *    desc   : 导航页面
 */

@Composable
fun NavContent() {
    val navCtrl = rememberNavController()
    //当前返回栈 实体内容
    val navBackStackEntry by navCtrl.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    //整体页面搭建
    // 1. 首先创建一个脚手架
    Scaffold(
        // 底部导航栏
        bottomBar = {
            when (currentDestination?.route) {
                ROUTE_HOME -> BottomNavBarView(navCtrl = navCtrl)
                ROUTE_HOT -> BottomNavBarView(navCtrl = navCtrl)
                ROUTE_LOVE -> BottomNavBarView(navCtrl = navCtrl)
                ROUTE_ME -> BottomNavBarView(navCtrl = navCtrl)
            }
        },
        // 主体content
        content = {
            // 页面切换
            NavHost(navController = navCtrl, startDestination = ROUTE_HOME, builder = {
                composable(route = ROUTE_HOME){
                    HomePage()
                }
                composable(route = ROUTE_HOT){
                    HotPage()
                }
                composable(route = ROUTE_LOVE){
                    LovePage()
                }
                composable(route = ROUTE_ME){
                    MePage(navCtrl)
                }
                composable(route = ROUTE_LOGIN){
                    LoginPage(navCtrl = navCtrl, loginViewModel = LoginViewModel())
                }

            })
        }
    )

}

/**
 * 底部导航视图
 */
@Composable
fun BottomNavBarView(navCtrl: NavHostController) {
    val items = listOf(Screen.Home, Screen.Hot, Screen.Love, Screen.Me)
    val navBackStackEntry by navCtrl.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    BottomNavigation {
        items.forEach{ screen ->
            BottomNavigationItem(
                icon = { Icon(imageVector = screen.imageVector, contentDescription = null)},
                label = { Text(text = stringResource(id = screen.resourceId))},
                selected = currentDestination?.route == screen.route,
                modifier = Modifier.background(AppTheme.colors.themeUi),
                onClick = {
                    navCtrl.navigate(screen.route){
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }


}