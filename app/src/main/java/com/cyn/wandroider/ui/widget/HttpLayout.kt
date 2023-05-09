package com.cyn.wandroider.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cyn.wandroider.ui.http.HttpState
import com.cyn.wandroider.ui.page.login.UserInfo

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/8
 *    desc   : 网络状态响应页
 */

@Composable
fun HttpLayout(
    isShowDialog: Boolean,
    httpState: HttpState<UserInfo>,
    modifier: Modifier = Modifier.fillMaxSize(),
    confirmAction: ()->Unit,
    content: @Composable BoxScope.()->Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        // 1.展示页面
        content()
        // 2.根据网络请求状态处理不同的逻辑
        when(httpState){
            HttpState.Loading -> CircularProgressIndicator()
            HttpState.Success("") -> {}
            HttpState.None -> {}
            else -> {
                if (isShowDialog){
                    PromptDialog(title = "提示", content = (httpState as HttpState.Error).errorStr) {
                        confirmAction.invoke()
                    }
                }
            }
        }
    }
}