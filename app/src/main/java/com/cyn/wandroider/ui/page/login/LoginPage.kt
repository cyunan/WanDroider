package com.cyn.wandroider.ui.page.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cyn.wandroider.R
import com.cyn.wandroider.ui.theme.AppTheme
import com.cyn.wandroider.ui.widget.HttpLayout
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/6
 *    desc   : 登陆页
 */
@Composable
fun LoginPage(
    navCtrl: NavHostController,
    scaffoldState: ScaffoldState,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val imageBitmap: ImageBitmap = ImageBitmap.imageResource(id = R.mipmap.image_3)
    val deleteIcon: ImageBitmap = ImageBitmap.imageResource(id = R.mipmap.image_1)
    val scope = rememberCoroutineScope()
    val viewUiState = loginViewModel.loginUiState

    LaunchedEffect(key1 = Unit, block = {
        // 这里用 consumeAsFlow 是因为协程是连续不间断的
        // 需要一直监听 eventChannel 的数据变化
        // 根据不同的事件，执行不同的操作
        loginViewModel.eventChannel.consumeAsFlow().collect { loginViewEvent->
            when (loginViewEvent){
                is LoginViewEvent.PopBack -> navCtrl.popBackStack()
            }
        }
    })
    HttpLayout(
        isShowDialog = viewUiState.isShowDialog,
        httpState = viewUiState.httpState,
        confirmAction = {
            scope.launch {
                loginViewModel.loginChannel.send(LoginIntent.DisMissDialog)
            }
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    bitmap = imageBitmap,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .clip(QueryToImageShape(160f))
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(0.dp)
                        .clip(QueryToImageShape(10f))
                        .background(Color(206, 236, 250, 121))
                        .width(130.dp)
                        .height(130.dp)
                ) {
                    Button(onClick = { /*TODO*/ }) {

                    }
                    Image(
                        bitmap = deleteIcon,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                            .background(color = Color(0XFF0DBEBF), shape = CircleShape)
                            .padding(3.dp)
                            .clip(CircleShape)
                            .shadow(elevation = 150.dp, clip = true)
                    )
                }
//            Image(bitmap = deleteIcon, contentDescription = null)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
            ) {
                TextField(value = viewUiState.account,
                    onValueChange = { loginViewModel.changeLoginUiState(viewUiState.copy(account = it)) },
                    shape = RoundedCornerShape(18.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = Color.White
                    ),
                    modifier = Modifier.border(
                        1.dp, Color(111, 111, 111, 66),
                        shape = RoundedCornerShape(18.dp)
                    ),
                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) }
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(value = viewUiState.password,
                    onValueChange = { loginViewModel.changeLoginUiState(viewUiState.copy(password = it)) },
                    shape = RoundedCornerShape(18.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = Color.White
                    ),
                    modifier = Modifier.border(
                        1.dp, Color(111, 111, 111, 66),
                        shape = RoundedCornerShape(18.dp)
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    leadingIcon = { Icon(Icons.Filled.AccountCircle, contentDescription = null) }
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 50.dp, vertical = 20.dp)
                    ) {
                        Checkbox(
                            checked = true, onCheckedChange = {},
                            colors = CheckboxDefaults.colors(checkedColor = AppTheme.colors.themeUi)
                        )
                        Text(text = "用户注册", color = AppTheme.colors.themeUi)
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(onClick = {
                            scope.launch {
                                loginViewModel.loginChannel.send(
                                    LoginIntent.DoLogin(
                                        viewUiState.account,
                                        viewUiState.password
                                    )
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = AppTheme.colors.themeUi,
                            contentColor = AppTheme.colors.listItem
                        )) {
                            Text(text = "Compose 登陆", fontSize = 18.sp)
                        }

                        Text(text = "更多精彩，更多体验~", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }


}

class QueryToImageShape(var radian: Float = 100f) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path()
        path.moveTo(0f, 0f)
        path.lineTo(0f, size.height - radian)
        path.quadraticBezierTo(size.width / 2f, size.height, size.width, size.height / 0.5F)
        path.lineTo(size.width, 0f)
        path.close()
        return Outline.Generic(path)
    }
}