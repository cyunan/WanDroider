package com.cyn.wandroider.ui.http

import com.cyn.wandroider.ui.page.login.LoginIntent
import com.cyn.wandroider.ui.page.login.UserInfo
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/7
 *    desc   :
 */
interface WanApi {
    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(
        @Field("username") userName: String,
        @Field("password") password: String
    ):BasicRes<UserInfo>
}