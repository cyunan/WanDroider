package com.cyn.wandroider

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.cyn.wandroider.ui.page.NavContent
import com.cyn.wandroider.ui.page.login.LoginPage
import com.cyn.wandroider.ui.page.login.LoginViewModel
import com.cyn.wandroider.ui.theme.WanDroiderTheme

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/6
 *    desc   : MainActivity
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WanDroiderTheme {
                NavContent()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WanDroiderTheme {
        Greeting("Android")
    }
}