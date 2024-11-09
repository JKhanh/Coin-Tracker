package com.jkhanh.shared.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class BasePagingSource<Value : Any>: PagingSource<Int, Value>() {
    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        return state.anchorPosition?.let { state.closestPageToPosition(it)?.prevKey?.plus(1) }
    }
}