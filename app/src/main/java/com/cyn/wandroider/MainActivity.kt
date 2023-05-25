package com.cyn.wandroider

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cyn.wandroider.ui.page.AppEntryPage
import com.cyn.wandroider.ui.page.NavContent
import dagger.hilt.android.AndroidEntryPoint

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/6
 *    desc   : MainActivity
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setContent {
                AppEntryPage()
            }
        }
    }
}


