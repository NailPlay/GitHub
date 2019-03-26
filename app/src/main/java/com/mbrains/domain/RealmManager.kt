package com.mbrains.domain

import android.util.Log
import com.mbrains.data.models.Repos
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmManager {

    val realm: Realm by lazy {
        val config = RealmConfiguration.Builder()
            .name("mbrains.realm")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.getInstance(config)
        //Realm.getDefaultInstance()
    }

    fun findAll(): List<Repos> {
        return realm.where(Repos::class.java).findAll()
    }

    fun insertNoDublicate(repos: Repos) {
        var findDuplicate = realm.where(Repos::class.java).equalTo("fullName", repos.fullName).findFirst()
        if (findDuplicate == null) {
            Log.d("NAIL", "Уникальный элемент")
            realm.beginTransaction()
            var newId: Long = 1
            if (realm.where(Repos::class.java).max("id") != null) {
                newId = realm.where(Repos::class.java).max("id") as Long + 1
            }
            var reposRealmObj = realm.createObject(Repos::class.java, newId)
            reposRealmObj.name = repos.name
            reposRealmObj.fullName = repos.fullName
            reposRealmObj.avatar_url = repos.owner_?.avatarUrl
            reposRealmObj.description = repos.description
            reposRealmObj.forks_count = repos.forks_count
            reposRealmObj.html_url = repos.html_url
            reposRealmObj.startCount = repos.startCount
            reposRealmObj.language = repos.language
            realm.commitTransaction()
        }
    }


    fun deleteById(id: Int) {
        realm.beginTransaction()
        val results = realm.where(Repos::class.java).equalTo("id", id).findAll()
        results.deleteAllFromRealm()
        realm.commitTransaction()
    }
}