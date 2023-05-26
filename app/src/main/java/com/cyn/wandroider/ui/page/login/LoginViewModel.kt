package com.cyn.wandroider.ui.page.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyn.wandroider.MyApplication
import com.cyn.wandroider.ui.http.HttpState
import com.cyn.wandroider.ui.http.NetworkService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/7
 *    desc   : 登陆ViewModel
 */

// 用户登陆意图
sealed class LoginIntent {
    data class DoLogin(val account: String, val password: String) : LoginIntent()
    object DisMissDialog: LoginIntent()
}

// 由ViewModel传递给View的动作
sealed class LoginViewEvent{
    object PopBack: LoginViewEvent() //回退操作，离开页面
}

data class LoginUiState(
    val account: String = "",
    val password: String = "",
    val isAgree: Boolean = false,
    val isShowDialog: Boolean = false,
    val httpState: HttpState<UserInfo> = HttpState.None
)
@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {
    val loginChannel = Channel<LoginIntent>()
    val eventChannel = Channel<LoginViewEvent>()
    private var _loginUiState by mutableStateOf(LoginUiState())
    val loginUiState: LoginUiState
        get() = _loginUiState

    fun changeLoginUiState(loginUiState: LoginUiState){
        _loginUiState = loginUiState
    }

    init {
        handleIntent()
    }

    //处理用户意图
    private fun handleIntent() {
        viewModelScope.launch {
            loginChannel.consumeAsFlow().collect {
                when (it) {
                    is LoginIntent.DoLogin -> doLogin(it.account, it.password)
                    is LoginIntent.DisMissDialog -> _loginUiState = loginUiState.copy(isShowDialog = false)
                }
            }
        }
    }

    private fun doLogin(account: String, password: String) {
        viewModelScope.launch {
            flow {
                // 1.发起网络请求
                emit(NetworkService.wanApi.login(account, password))
            }.onStart {
                // 2.开始网络请求，展示进度条
                _loginUiState = loginUiState.copy(httpState = HttpState.Loading)

            }.catch {
                // 3.捕获异常
                _loginUiState = loginUiState.copy(httpState = HttpState.Error("网络出现异常～"), isShowDialog = true)
            }.collect {
                // 4.处理网络请求返回值
                when (it.errorCode) {
                    0 -> {
                        it.data?.let { userInfo ->
                            _loginUiState = loginUiState.copy(httpState = HttpState.Success(userInfo))
                            // 保存个人用户信息
                            MyApplication.CONTEXT.userInfo = userInfo
                            // 跳转
                            eventChannel.send(LoginViewEvent.PopBack)
                        }

                    }
                    else -> {
                        //请求出错
                        _loginUiState = loginUiState.copy(httpState = HttpState.Error(it.errorMsg), isShowDialog = true)
                    }

                }
            }
        }
    }
}