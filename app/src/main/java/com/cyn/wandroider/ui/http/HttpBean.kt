package com.cyn.wandroider.ui.http

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/7
 *    desc   :
 */
data class BasicRes<T>(
    var data: T?,
    var errorCode: Int,
    var errorMsg: String
)

// 网络状态封装
sealed class HttpState<out T>{
    data class Success<T>(val result: T): HttpState<T>()
    data class Error(val errorStr: String): HttpState<Nothing>()
    object Loading: HttpState<Nothing>()
    object None: HttpState<Nothing>()
}