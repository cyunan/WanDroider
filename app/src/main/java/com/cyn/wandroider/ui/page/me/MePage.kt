package com.cyn.wandroider.ui.page.me

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.navigation.NavController
import com.cyn.wandroider.ui.ROUTE_LOGIN

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/6
 *    desc   :
 */
@Composable
fun MePage(navCtrl: NavController) {
    Box(modifier = Modifier.fillMaxSize(),){
        Text(
            text = "登录",
            modifier = Modifier.clickable {
                navCtrl.navigate(ROUTE_LOGIN)
            }.align(Alignment.Center)
        )
    }
}