package com.mbrains.domain

import com.mbrains.data.models.Repos
import com.mbrains.data.services.ApiServies
import io.reactivex.Single

class ApiManager {

    var reposService : ApiServies = ApiServies()

    fun getListOfRepos(user : String, page : Int, perPage : Int) : Single<List<Repos>> {
        return reposService.getRepos(user, page, perPage)
            .onErrorResumeNext({throwable -> Single.error(throwable)})
            .flatMap { response ->
                if (!response.isSuccessful) {
                    Single.error(Throwable(response.code().toString()))
                } else Single.just(response)
            }
            .map {response -> response.body()}
            .map { list -> list.items }
    }
}