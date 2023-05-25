package com.cyn.wandroider.ui.page.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cyn.wandroider.ui.page.home.question.QuestionPage
import com.cyn.wandroider.ui.page.home.recommened.RecommendedPage
import com.cyn.wandroider.ui.page.home.recommened.RecommendedViewModel
import com.cyn.wandroider.ui.page.home.square.SquarePage
import com.cyn.wandroider.ui.theme.AppTheme
import com.cyn.wandroider.ui.widget.TextTabBar
import com.cyn.wandroider.ui.widget.TextTabInfo
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/6
 *    desc   : 首页视图
 */

//声明tabList
val textTabList = listOf(
    TextTabInfo(101, "广场"),
    TextTabInfo(102, "推荐"),
    TextTabInfo(103, "问答")
)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomePage(navCtrl: NavHostController, scaffoldState: ScaffoldState) {
    // 1. 先拿到协程作用域
    val scope = rememberCoroutineScope()
    // 2. 组合视图
    Column {
        // 2.1 先通过 rememberPagerState 拿到默认展示tab
        val pageState = rememberPagerState(initialPage = 0)
        // 2.2 装载TabBar
        TextTabBar(
            selectedIndex = pageState.currentPage,
            textTabInfoList = textTabList,
            bgColor = AppTheme.colors.themeUi,
            onTabSelectedAction = { index ->
                // 切换viewPager
                scope.launch {
                    pageState.scrollToPage(index)
                }
            }
        )
        //2.3 装载viewPager
        HorizontalPager(
            count = textTabList.size,
            state = pageState,
            modifier = Modifier.padding(bottom = 50.dp)
        ) { page->
            when(page){
                0 -> SquarePage(navCtrl, scaffoldState)
                1 -> RecommendedPage(navCtrl, scaffoldState)
                2 -> QuestionPage(navCtrl, scaffoldState)
            }
        }
    }
}