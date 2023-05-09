package com.cyn.wandroider

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.cyn.wandroider.ui.page.login.UserInfo

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/7
 *    desc   :
 */

//@HiltAndroidApp
class MyApplication: Application() {
    var userInfo: UserInfo? = null
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var CONTEXT: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = this
    }
}