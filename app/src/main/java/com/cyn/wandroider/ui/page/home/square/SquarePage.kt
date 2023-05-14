package com.cyn.wandroider.ui.page.home.square

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.cyn.wandroider.ui.widget.RefreshLayout
import kotlinx.coroutines.flow.Flow

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/11
 *    desc   : 广场页
 */

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun SquarePage(viewModel: SquareViewModel = SquareViewModel()) {
    // 1. 使用remember对列表数据做了持久化操作
    // 调用 viewModel.uiState 的时候，会进行网络数据的请求操作
    val uiState = remember { viewModel.uiState }
    // 2. 将uiState.pagingData分页数据通过collectAsLazyPagingItems()转化成懒加载集合
    val squareData = uiState.pagingData.collectAsLazyPagingItems()
    // 3. 这里值得注意，这个listState是用来同步更新列表的滚动位置
    // 如果列表数据为空的话，就不需要处理滚动位置
    val listState = if (squareData.itemCount > 0) uiState.listState else LazyListState()


    RefreshLayout(lazyPagingItems = squareData,
        listState = listState,
        onRefresh = {}
    ){
        itemsIndexed(squareData){ _, item ->
            androidx.compose.material.Surface(
                elevation = 10.dp, // item间距
                modifier = Modifier.padding(10.dp), //item内边距
                shape = RoundedCornerShape(10.dp) //圆角弧度
            ) {
                ListItem(
                    text = {
                        Text(
                            text = item!!.title.toString(),
                            fontSize = 16.sp,
                            overflow = TextOverflow.Ellipsis, //溢出一行的部分省略
                            maxLines = 1
                        )
                    },
                    secondaryText = {
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = SpaceBetween,
                            verticalAlignment = CenterVertically
                        ) {
                            Text(text = "${item!!.shareUser} ${item.niceDate}")
                            TextButton(onClick = { /*TODO*/ }) {
                                Text(text = item.zan.toString(), color = Color.Gray)
                                Image(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(color = Color.Red))
                            }
                        }
                    }
                )

            }

        }
    }

}

