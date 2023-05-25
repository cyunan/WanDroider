package com.cyn.wandroider.ui.page.home.recommened

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.cyn.wandroider.ui.ROUTE_WEBVIEW
import com.cyn.wandroider.ui.http.HttpState
import com.cyn.wandroider.ui.page.home.square.ArticleItem
import com.cyn.wandroider.ui.webview.WebData
import com.cyn.wandroider.ui.widget.Banner
import com.cyn.wandroider.ui.widget.RefreshLayout
import com.cyn.wandroider.utils.RouteUtils

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/11
 *    desc   : 推荐页
 */
@Composable
fun RecommendedPage(
    navCtrl: NavHostController,
    scaffoldState: ScaffoldState,
    recommendedViewModel: RecommendedViewModel = hiltViewModel()
) {
    val uiState = recommendedViewModel.uiState
    val recommendData = uiState.pagingData.collectAsLazyPagingItems()
    val banners = uiState.bannerList
    val topArticle = uiState.topArticles
    val isRefreshing = uiState.httpState == HttpState.Loading
    val listState = if (recommendData.itemCount > 0) uiState.listState else LazyListState()
    RefreshLayout(
        lazyPagingItems = recommendData,
        listState = listState,
        isRefreshing = isRefreshing,
        onRefresh = {

        }){
        if (banners.isNotEmpty()) {
            item {
                Banner(list = banners) { url, title ->
                    RouteUtils.navTo(navCtrl, ROUTE_WEBVIEW, WebData(title, url))
                }
            }
        }

        if (topArticle.isNotEmpty()) {
            itemsIndexed(topArticle) { index, item ->
                ArticleItem(item = item){ webData ->
                    RouteUtils.navTo(navCtrl, ROUTE_WEBVIEW, WebData(webData.title, webData.url))
                }
            }
        }

        itemsIndexed(recommendData) { _, item ->
            item?.let {
                ArticleItem(item = it) { webData ->
                    RouteUtils.navTo(navCtrl, ROUTE_WEBVIEW, WebData(webData.title, webData.url))
                }
            }
        }
    }


}