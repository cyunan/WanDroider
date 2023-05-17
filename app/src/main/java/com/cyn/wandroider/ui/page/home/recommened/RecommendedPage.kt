package com.cyn.wandroider.ui.page.home.recommened

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.cyn.wandroider.ui.page.home.square.ArticleItem
import com.cyn.wandroider.ui.widget.Banner
import com.cyn.wandroider.ui.widget.RefreshLayout

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/11
 *    desc   : 推荐页
 */
@Composable
fun RecommendedPage(recommendedViewModel: RecommendedViewModel = RecommendedViewModel()) {
    var uiState = recommendedViewModel.uiState

    Column(modifier = Modifier.fillMaxSize()) {
        Banner(list = uiState.bannerList, onClick ={ link, title ->

        })
        LazyColumn(state = rememberLazyListState()){
            item {
                uiState.topArticles.forEachIndexed { index, article ->
                    ArticleItem(item = article)

                }
            }

        }
    }

}