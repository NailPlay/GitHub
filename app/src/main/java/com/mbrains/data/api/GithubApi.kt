package com.mbrains.data.api

import com.mbrains.data.models.Commits
import com.mbrains.data.models.ReposResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/repositories")
    fun getRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): Single<Response<ReposResponse>>

    @GET("repos/{fullname}/commits")
    fun getCommits(@Path("fullname", encoded = true) fullname: String): Single<Response<List<Commits>>>

}