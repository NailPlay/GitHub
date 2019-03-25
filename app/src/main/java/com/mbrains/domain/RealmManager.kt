package com.mbrains.domain

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

    fun find(id: Long): Repos? {
        return realm.where(Repos::class.java).equalTo("id", id).findFirst()
    }

    fun findAll(): List<Repos> {
        return realm.where(Repos::class.java).findAll()
    }

    fun insert(name: String) {
        //var repos = realm.where(Repos::class.java).equalTo("name", name).findFirst()
       // if (repos == null) {
            realm.beginTransaction()
            var newId: Long = 1
            if (realm.where(Repos::class.java).max("id") != null) {
                newId = realm.where(Repos::class.java).max("id") as Long + 1
            }
            val note = realm.createObject(Repos::class.java, newId)
            note.name = name
            realm.commitTransaction()
     //   }
    }



    fun deleteById(id: Long) {
        realm.beginTransaction()
        val results = realm.where(Repos::class.java!!).equalTo("id", id).findAll()
        results.deleteAllFromRealm()
        realm.commitTransaction()
    }
}