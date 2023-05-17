package com.cyn.wandroider.ui.widget

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.cyn.wandroider.R
import com.cyn.wandroider.ui.page.home.recommened.BannerData
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/13
 *    desc   :
 */

/**
 * 轮播视图的数据类
 */

@OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)
@Composable
fun Banner(
    list: List<BannerData>?,
    duration: Long = 3000, //单个item的停留时间
    @DrawableRes loadImage: Int = R.mipmap.no_banner, //没数据时页面的默认图
    indicatorAlignment: Alignment = Alignment.BottomCenter, //底部指示条的位置
    onClick: (link: String, title: String) -> Unit //item的点击事件
) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(6.dp)
            .height(120.dp)
    ) {
        if (list == null){
            Image(
                painter = painterResource(id = loadImage),
                contentDescription = "默认占位符",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth,

            )
        }else{
            // 1. 持久化，页数管理
            val pagerState = rememberPagerState(initialPage = 0)
            // 2。持久化，是否轮动
            val executeChangePage by remember{ mutableStateOf(false) }
            // 3. 轮动逻辑，3秒轮动一次
            LaunchedEffect(key1 = pagerState, key2 = executeChangePage){
                if (pagerState.pageCount > 0){
                    delay(timeMillis = duration)
                    // 使用页面滚动效果来轮动至下一页面
                    pagerState.animateScrollToPage((pagerState.currentPage + 1) % pagerState.pageCount)
                }
            }
            
            HorizontalPager(
                count = list.size,
                state = pagerState,
            ) { page ->
                Image(
                    painter = rememberImagePainter(data = list[page].imageUrl),
                    contentDescription = "banner图片加载",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.FillWidth
                )
            }

            Box(modifier = Modifier
                .align(indicatorAlignment)
                .padding(bottom = 6.dp, start = 6.dp, end = 6.dp)){
                Row(horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {
                    list.forEachIndexed{ index, bannerInfo ->
                        var size by remember {
                            mutableStateOf(5.dp)
                        }
                        size = if (pagerState.currentPage == index) 7.dp else 5.dp

                        val color = if (pagerState.currentPage == index) MaterialTheme.colors.primary else Color.Gray

                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(color)
                                .animateContentSize()
                                .size(size)
                        )

                        //指示点间的间隔
                        if (index != list.lastIndex) Spacer(
                            modifier = Modifier
                                .height(0.dp)
                                .width(4.dp)
                        )


                    }
                }
            }

        }
    }
}
