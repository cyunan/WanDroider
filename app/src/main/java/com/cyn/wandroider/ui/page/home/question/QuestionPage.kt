package com.cyn.wandroider.ui.page.home.question

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.cyn.wandroider.ui.ROUTE_WEBVIEW
import com.cyn.wandroider.ui.page.home.square.Article
import com.cyn.wandroider.ui.theme.AppTheme
import com.cyn.wandroider.ui.webview.WebData
import com.cyn.wandroider.ui.widget.MainTitle
import com.cyn.wandroider.ui.widget.MiniTitle
import com.cyn.wandroider.ui.widget.RefreshLayout
import com.cyn.wandroider.ui.widget.TextContent
import com.cyn.wandroider.utils.RegexUtils
import com.cyn.wandroider.utils.RouteUtils

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/11
 *    desc   : 问答页
 */
@Composable
fun QuestionPage(
    navCtrl: NavHostController,
    scaffoldState: ScaffoldState,
    viewModel: QuestionViewModel = hiltViewModel()
) {
    val uiState = remember {
        viewModel.uiState
    }
    val questionData = uiState.pagingData.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    RefreshLayout(lazyPagingItems = questionData, listState = listState){
        itemsIndexed(questionData){ _, article ->
            article?.let {
                QuestionItem(article = article) {
                    article.run {
                        RouteUtils.navTo(
                            navCtrl,
                            ROUTE_WEBVIEW,
                            WebData(title, link!!)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuestionItem(article: Article, isLoading: Boolean= false, onClick: ()->Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(5.dp)
            .clickable(enabled = !isLoading) { onClick.invoke() }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            MainTitle(
                title = titleSubstring(article.title) ?: "每日一问",
                maxLine = 2,
                isLoading = isLoading,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(vertical = 5.dp)
            ) {
                MiniTitle(
                    text = "作者：${article.author ?: "xxx"}",
                    color = AppTheme.colors.textSecondary,
                    modifier = Modifier
                        .padding(start = if (isLoading) 5.dp else 0.dp)
                        .align(Alignment.CenterVertically),
                    isLoading = isLoading
                )
                Spacer(modifier = Modifier.width(10.dp))
                //发布时间
                MiniTitle(
                    text = "日期：${RegexUtils().timestamp(article.niceDate) ?: "2020"}",
                    color = AppTheme.colors.textSecondary,
                    modifier = Modifier
                        .padding(start = if (isLoading) 5.dp else 0.dp)
                        .align(Alignment.CenterVertically),
                    isLoading = isLoading
                )

            }
            TextContent(
                text = RegexUtils().symbolClear(article.desc),
                maxLines = 3,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 60.dp),
                isLoading = isLoading
            )
        }
    }
}
private fun titleSubstring(oldText: String?): String? {
    return oldText?.run {
        var newText = this
        if (startsWith("每日一问") && contains(" | ")) {
            newText = substring(indexOf(" | ") + 3, length)
        }
        newText
    }
}
