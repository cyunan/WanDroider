package com.cyn.wandroider.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/10
 *    desc   : 标题栏TabBar
 */

data class TextTabInfo(
    val id: Int, //tabId
    val text: String, //tab内容
    var position: Int = 0, //当前位置
    var selected: Boolean = false //当前tab是否选中
)

@Composable
fun TextTabBar(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    textTabInfoList: List<TextTabInfo>,
    contentAlignment: Alignment = Alignment.Center,
    bgColor: Color,
    onTabSelectedAction:((index: Int) -> Unit)?
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxWidth()
            .height(54.dp)
            .background(color = bgColor)
            .horizontalScroll(state = rememberScrollState())
    ) {
        // 横向布局 编写顶部三个TabBar
        Row(modifier = Modifier.align(contentAlignment)) {
            // 转载tabitem
            textTabInfoList.forEachIndexed { index, textTabInfo ->
                Text(
                    text = textTabInfo.text,
                    fontSize = if (selectedIndex == index) 20.sp else 15.sp,
                    fontWeight = if (selectedIndex == index) FontWeight.SemiBold else FontWeight.Normal,
                    modifier = modifier.align(Alignment.CenterVertically)
                        .padding(horizontal = 10.dp)
                        .clickable { onTabSelectedAction?.invoke(index) }
                )
            }
        }

    }
}