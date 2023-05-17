package com.cyn.wandroider.ui.http

import com.cyn.wandroider.ui.page.home.recommened.BannerInfo
import com.cyn.wandroider.ui.page.home.square.Article
import com.cyn.wandroider.ui.page.login.UserInfo
import retrofit2.http.*

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
    ):BaseRes<UserInfo>

    //广场
    @GET("/user_article/list/{page}/json")
    suspend fun getSquareData(@Path("page") page: Int): BaseRes<ListWrapper<Article>>

    //banner
    @GET("/banner/json")
    suspend fun getBanners(): BaseRes<MutableList<BannerInfo>>

    //置顶文章
    @GET("/article/top/json")
    suspend fun getTopArticles(): BaseRes<MutableList<Article>>


}