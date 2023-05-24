package com.cyn.wandroider.ui.page.home.recommened

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.cyn.wandroider.ui.common.easingPager
import com.cyn.wandroider.ui.http.HttpState
import com.cyn.wandroider.ui.http.NetworkService
import com.cyn.wandroider.ui.page.home.square.Article
import com.cyn.wandroider.ui.page.home.square.PagingArticle
import com.cyn.wandroider.ui.page.login.UserInfo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/14
 *    desc   :
 */

data class RecommendUIState(
    val bannerList: MutableList<BannerData> = mutableListOf(),
    val topArticles: List<Article> = emptyList(),
    val httpState: HttpState<UserInfo> = HttpState.None,
    val listState: LazyListState = LazyListState(),
    val pagingData: PagingArticle,
)

sealed class RecommendIntent{
    object FetchData: RecommendIntent()
}
class RecommendedViewModel : ViewModel(){
    val intentChannel = Channel<RecommendIntent>()
    private val pager by lazy {
        easingPager {
            NetworkService.wanApi.getIndexList(it)
        }.cachedIn(viewModelScope)
    }
    var uiState by mutableStateOf( RecommendUIState(pagingData = pager) )
        private set

    init {
//        intentChannel.send(RecommendIntent.FetchData)
        handleIntent()
        fetchData()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { recommenedIntent->
                when(recommenedIntent){
                    is RecommendIntent.FetchData -> {
                        fetchData()
                    }
                }
            }
        }
    }

    private fun fetchData() {
        val imageFlow = flow {
            kotlinx.coroutines.delay(2000)
            emit(NetworkService.wanApi.getBanners())
        }.map { bannerInfo ->
            val result = mutableListOf<BannerData>()
            bannerInfo.data?.forEach {
                result.add(BannerData(it.title ?: "", it.imagePath ?: "", it.url ?: ""))
            }
            result
        }

        val topListFlow = flow {
            emit(NetworkService.wanApi.getTopArticles())
        }.map {
            it.data ?: emptyList()
        }


        viewModelScope.launch {
            imageFlow.zip(topListFlow){ banners, articles ->
                uiState = uiState.copy(bannerList = banners, topArticles = articles)
            }.onStart {
//                uiState = uiState.copy(httpState = HttpState.Loading)
            }.catch {
//                uiState = uiState.copy(httpState = HttpState.Error("网络异常～"))
            }.collect()
        }

    }

}

data class BannerData(
    val title: String,
    val imageUrl: String,
    val linkUrl: String
)