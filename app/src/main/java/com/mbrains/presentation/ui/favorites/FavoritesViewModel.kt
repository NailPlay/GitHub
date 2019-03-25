package com.mbrains.presentation.ui.favorites

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.mbrains.data.models.Repos
import com.mbrains.domain.RealmManager
import io.realm.RealmResults
import io.realm.Realm

import com.mbrains.data.database.LiveRealmResults




class FavoritesViewModel : ViewModel() {

    val realmManager = RealmManager()
    private lateinit var favoriteList: LiveData<List<Repos>>

    init{
        favoriteList =   LiveRealmResults<Repos>(realmManager.findAll() as RealmResults<Repos>)
    }

    fun getFavorite(): LiveData<List<Repos>> {
        return favoriteList!!
    }

    override fun onCleared() {
        super.onCleared()
    }

}