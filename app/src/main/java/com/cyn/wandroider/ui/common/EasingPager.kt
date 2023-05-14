package com.cyn.wandroider.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.cyn.wandroider.ui.http.BaseRes
import com.cyn.wandroider.ui.http.HttpState
import com.cyn.wandroider.ui.http.ListWrapper
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/11
 *    desc   :
 */

// 处理网络返回的分页数据
/**
  * @description 处理网络返回的分页数据
  * @param config 自定义的页面分页加载规则
  * @param callAction 网络请求，获取数据源
  * @return
  */
fun <T: Any> ViewModel.easingPager(
    config: AppPagingConfig = AppPagingConfig(),
    callAction: suspend (page: Int) -> BaseRes<ListWrapper<T>>
): Flow<PagingData<T>> {
    // 封装 pager
    return pager(config, 0){ loadParams->
        // 1. 拿到当前需要加载数据的页码
        val page = loadParams.key ?: 0
        // 2. 处理网络请求的数据
        val response = try {
            HttpState.Success(callAction.invoke(page))
        }catch (e: Exception){
            HttpState.Error(e.message.toString())
        }

        // 3. 返回分页源的数据-PagingSource.LoadResult
        when(response){
            is HttpState.Success -> {
                val data = response.result.data
                val hasNotNext = (data!!.datas.size < loadParams.loadSize) && data.over
                // 成功时返回的数据
                PagingSource.LoadResult.Page(
                    data = data.datas,
                    prevKey = if (page > 1) page-1 else null,
                    nextKey = if (hasNotNext) null else page + 1
                )
            }
            is HttpState.Error -> {
                // 失败返回的数据
                PagingSource.LoadResult.Error(Exception(response.errorStr))
            }
            else -> TODO()
        }
    }
}

/**
  * @description 分页列表 数据加载逻辑
  * @param config 自定义的页面分页加载规则
  * @param initialKey 初始页的页数
  * @param loadData 加载规则，用来加载的数据。传入分页源加载配置，传出分页源的数据
  * @return 转化成flow数据流
  */
fun <K: Any, V: Any> ViewModel.pager(
    config: AppPagingConfig = AppPagingConfig(),
    initialKey: K? = null,
    loadData: suspend (PagingSource.LoadParams<K>)->PagingSource.LoadResult<K, V>
): Flow<PagingData<V>> {
    // 1. 配置config
    val baseConfig = PagingConfig(
        config.pageSize,
        initialLoadSize = config.initialLoadSize,
        prefetchDistance = config.maxSize,
        enablePlaceholders = config.enablePlaceholders
    )
    // 2. 封装成Pager对象，在viewModelScope作用域缓存flow
    // 这样做的好处是 将 flow 的结果缓存起来，并在 viewModelScope 范围内共享
    // 利用viewModelScope的特性，保存数据，避免数据重新加载
    return Pager(
        config = baseConfig,
        initialKey = initialKey,
        pagingSourceFactory = {
            object : PagingSource<K, V>() {
                override fun getRefreshKey(state: PagingState<K, V>): K? {
                    // 修改列表数据-当前页码
                    return initialKey
                }

                override suspend fun load(params: LoadParams<K>): LoadResult<K, V> {
                    // 自定义加载规则
                    return loadData.invoke(params)
                }
            }
        }
    ).flow.cachedIn(viewModelScope)
}