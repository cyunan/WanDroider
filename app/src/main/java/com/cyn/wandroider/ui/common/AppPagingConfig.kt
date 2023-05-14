package com.cyn.wandroider.ui.common

import androidx.paging.PagingConfig

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/11
 *    desc   : 分页配置
 */
data class AppPagingConfig(
    val pageSize: Int = 20, //一页的数量
    val initialLoadSize: Int = 20, //初始记载的数量
    val prefetchDistance: Int = 2, //到倒数第几个开始再次请求数据
    val maxSize: Int = PagingConfig.MAX_SIZE_UNBOUNDED, //最大加载数量
    val enablePlaceholders: Boolean = false //是否启动占位图
)
