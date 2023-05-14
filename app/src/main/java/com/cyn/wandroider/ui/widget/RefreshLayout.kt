package com.cyn.wandroider.ui.widget

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.cyn.wandroider.ui.theme.AppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/13
 *    desc   : 下拉刷新的列表使用封装
 */
/**
  * @description 下拉刷新的列表使用封装
  * @param lazyPagingItems 懒加载分页数据项
  * @param isRefreshing 是否刷新
  * @param onRefresh 刷新事件回调
  * @param listState 列表状态
  * @param itemContent 懒加载列表元素的内容
  * @return
  */
@Composable
fun <T: Any> RefreshLayout(
    lazyPagingItems: LazyPagingItems<T>,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit) = {},
    listState: LazyListState = rememberLazyListState(),
    itemContent: LazyListScope.() -> Unit
) {
    // 这里也是一个持久化的变量声明，用来管理下拉刷新视图的状态
    // 只要状态发生变化，就会自动重新渲染SwipeRefresh
    // 所以swipeRefreshState这里发生变化的话，触发SwipeRefresh视图的onRefresh毁掉
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    val error = lazyPagingItems.loadState.refresh is LoadState.Error
    if (error){
        Log.w("RefreshLayout", error.toString())
        return
    }

    // 这里用了一个开源的刷新库
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            onRefresh()
            // 触发LazyPagingItems 对象中缓存的数据的刷新，这样就可以发起网络请求，加载更多的数据
            lazyPagingItems.refresh()
        }) {
        swipeRefreshState.isRefreshing =
            (lazyPagingItems.loadState.refresh is LoadState.Loading || isRefreshing)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState
        ){
            // 这里将传进来的视图进行组合，就是自定义列表的item视图
            itemContent()
            // 这里定义下拉加载时，最后一项的视图。
            // 例如加载的话这里就会给一个等待转圈圈的item
            if (!swipeRefreshState.isRefreshing){
                // 处理 LazyColumn的内容
                item {
                    lazyPagingItems.apply {
                        when(loadState.append){
                            is LoadState.Loading -> {
                                LoadingItem()
                            }
                            is LoadState.Error -> {}
                            is LoadState.NotLoading -> {}
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorItem(retry: () -> Unit) {
    Button(
        onClick = { retry() },
        modifier = Modifier.padding(10.dp),
        colors = buttonColors(backgroundColor = AppTheme.colors.themeUi)
    ) {
        Text(text = "重试")
    }
}

@Composable
fun NoMoreItem() {
    Text(
        text = "没有更多了",
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = AppTheme.colors.themeUi,
            modifier = Modifier
                .padding(10.dp)
                .height(50.dp)
        )
    }
}