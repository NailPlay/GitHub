package com.mbrains.presentation.ui.favorites

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.mbrains.data.database.LiveRealmResults
import com.mbrains.data.models.Repos
import com.mbrains.domain.RealmManager
import io.realm.RealmResults


class FavoritesViewModel : ViewModel() {

    private val realmManager = RealmManager()

    private lateinit var favoriteList: LiveData<List<Repos>>

    init{
        favoriteList =   LiveRealmResults<Repos>(realmManager.findAll() as RealmResults<Repos>)
    }

    fun getFavorite(): LiveData<List<Repos>> {
        return favoriteList
    }

}