package com.cyn.wandroider.ui.page.home.question

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cyn.wandroider.ui.common.easingPager
import com.cyn.wandroider.ui.http.NetworkService
import com.cyn.wandroider.ui.page.home.square.PagingArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/17
 *    desc   :
 */
data class QuestionUiState(
    val isRefreshing: Boolean = false,
    val listState: LazyListState = LazyListState(),
    val pagingData: PagingArticle

)

@HiltViewModel
class QuestionViewModel @Inject constructor() : ViewModel(){
    private val pager by lazy {
        easingPager { NetworkService.wanApi.getWendaData(it) }
    }

    var uiState by mutableStateOf(QuestionUiState(pagingData = pager))

}