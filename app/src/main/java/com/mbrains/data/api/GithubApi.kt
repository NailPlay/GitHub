package com.mbrains.data.api

import com.mbrains.data.models.Repos
import com.mbrains.data.models.ReposResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    // загрузка списка репозиториев
    @GET("search/repositories?sort=stars")
    fun getRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): Single<Response<ReposResponse>>

    // загрузка коммитов
    @GET("")
    fun getCommit(): Unit;



}