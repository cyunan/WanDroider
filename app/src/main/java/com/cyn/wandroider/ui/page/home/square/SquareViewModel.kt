package com.cyn.wandroider.ui.page.home.square

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.cyn.wandroider.ui.common.easingPager
import com.cyn.wandroider.ui.http.NetworkService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/12
 *    desc   : 广场的viewModel
 */

typealias PagingArticle = Flow<PagingData<Article>>

/**
 * 广场的数据类
 */
data class SquareUIState(
    val isRefresh: Boolean = false, //是否刷新
    val pagingData: PagingArticle, //分页数据
    val listState: LazyListState = LazyListState() //懒加载列表状态
)

@HiltViewModel
class SquareViewModel @Inject constructor() : ViewModel(){
    private val pager by lazy {
        // 这里使用了自定义的分页函数，将网络返回的数据封装成一个page对象
        easingPager { page->
            delay(2000)
            // 进行网络请求将对应页码的数据进行返回
            NetworkService.wanApi.getSquareData(page)
        }
    }

    // 广场列表的数据
    // 值得注意的是，这里用了mutableStateOf()定义uiState是可变状态(Mutable State)对象
    // 所以这里数据发生变化的话，compose框架会自动进行UI重绘
    var uiState by mutableStateOf(SquareUIState(pagingData = pager))
        private set
}