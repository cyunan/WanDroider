package com.cyn.wandroider.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cyn.wandroider.ui.theme.AppTheme
import com.cyn.wandroider.ui.theme.white
import kotlinx.coroutines.delay

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/10
 *    desc   : 闪屏页
 */

@Composable
fun SplashPage(onNextPage: ()->Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.themeUi),
        contentAlignment = Alignment.TopCenter
    ) {
        // 启动效果，模拟数据请求，延迟0.5秒，执行传递进来的动作
        LaunchedEffect(Unit) {
            delay(500)
            onNextPage.invoke()
        }
        Text(
            text = "Wan Android",
            fontSize = 32.sp,
            color = white,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(0.dp, 150.dp, 0.dp, 0.dp)
        )
    }
}