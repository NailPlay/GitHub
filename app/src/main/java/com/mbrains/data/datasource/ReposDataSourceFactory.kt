package com.mbrains.data.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.mbrains.data.models.Repos
import io.reactivex.disposables.CompositeDisposable

class ReposDataSourceFactory(
     private val compositeDisposable: CompositeDisposable,
     private val request: String)
    : DataSource.Factory<Int, Repos>() {

    val reposDataSourceLiveData = MutableLiveData<ReposDataSource>()

    override fun create(): DataSource<Int, Repos> {
        val reposDataSource = ReposDataSource(request = request, compositeDisposable = compositeDisposable)
        reposDataSourceLiveData.postValue(reposDataSource)
        return  reposDataSource
    }

}