package com.cyn.wandroider.ui.page.login

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/7
 *    desc   : 用户数据类
 */
data class UserInfo(
    var id: Int,
    var admin: Boolean,
    var chapterTops: MutableList<Int>,
    var coinCount: Int,
    var collectIds: MutableList<Int>,
    var email: String,
    var icon: String,
    var nickname: String,
    var password: String,
    var token: String,
    var type: Int,
    var username: String
)
