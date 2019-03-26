package com.mbrains.data.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.mbrains.data.models.Repos
import com.mbrains.domain.ApiManager
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers


class ReposDataSource(
    private val compositeDisposable: CompositeDisposable,
    private val request: String
) : PageKeyedDataSource<Int, Repos>() {

    var manager: ApiManager = ApiManager()

    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    private var retryCompletable: Completable? = null

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ }, { throwable -> Log.d("nail", throwable.message) })
            )
        }
    }

    private fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Repos>) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            manager.getListOfRepos(request, 1, 10)
                .subscribe({ repos ->
                    setRetry(null)
                    networkState.postValue(NetworkState.LOADED)
                    initialLoad.postValue(NetworkState.LOADED)
                    callback.onResult(repos, 1, 2)
                }, { t ->
                    val error = NetworkState.error(t.message)
                    setRetry(Action { loadInitial(params, callback) })
                    networkState.postValue(error)
                    initialLoad.postValue(error)
                })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repos>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            manager.getListOfRepos(request, params.key + 1, 10)
                .subscribe({ repos ->
                    setRetry(null)
                    networkState.postValue(NetworkState.LOADED)
                    callback.onResult(repos, params.key + 1)
                }, { t ->
                    setRetry(Action { loadAfter(params, callback) })
                    networkState.postValue(NetworkState.error(t.message))
                })

        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repos>) {
    }

}