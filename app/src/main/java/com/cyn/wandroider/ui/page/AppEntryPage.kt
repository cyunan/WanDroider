package com.cyn.wandroider.ui.page

import androidx.compose.runtime.*
import com.cyn.wandroider.ui.theme.AppTheme

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/10
 *    desc   :  首页入口
 */

@Composable
fun AppEntryPage() {
    // 是否冷启动
    var isSplash by remember {
        mutableStateOf(true)
    }
    AppTheme {
        if (isSplash){
            SplashPage {
                isSplash = false
            }
        }else{
            NavContent()
        }
    }
}