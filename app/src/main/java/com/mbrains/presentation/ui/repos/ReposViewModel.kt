package com.mbrains.presentation.ui.repos

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.mbrains.data.datasource.NetworkState
import com.mbrains.data.datasource.ReposDataSource
import com.mbrains.data.datasource.ReposDataSourceFactory
import com.mbrains.data.models.Repos
import io.reactivex.disposables.CompositeDisposable

class ReposViewModel : ViewModel()
{

    var reposList: LiveData<PagedList<Repos>>? = null

    private var compositeDisposable = CompositeDisposable()

    private var sourceFactory: ReposDataSourceFactory? = null

    fun initSearch(request: String){
        compositeDisposable.clear()
        sourceFactory = ReposDataSourceFactory(compositeDisposable, request)
        var config = PagedList.Config.Builder()
            .setPageSize(2)
            .setEnablePlaceholders(false)
            .build()
        reposList = LivePagedListBuilder<Int, Repos>(sourceFactory!!, config).build()
    }


    fun retry() {
        sourceFactory!!.reposDataSourceLiveData.value!!.retry()
    }

    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap<ReposDataSource, NetworkState>(
        sourceFactory!!.reposDataSourceLiveData, { it.networkState })

    fun getInitialState(): LiveData<NetworkState> = Transformations.switchMap<ReposDataSource, NetworkState>(
        sourceFactory!!.reposDataSourceLiveData, { it.initialLoad })


}