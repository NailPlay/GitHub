package com.mbrains.data.services

import com.mbrains.data.api.GithubApi
import com.mbrains.data.models.Commits
import com.mbrains.data.models.ReposResponse
import com.mbrains.utils.NetworkUtils
import io.reactivex.Single
import retrofit2.Response

class ApiServies {
    var api: GithubApi = NetworkUtils.retrofit.create(GithubApi::class.java)

    fun getRepos(user: String, page: Int, perPage: Int): Single<Response<ReposResponse>> {
        return api.getRepos(user, page, perPage)
    }

    fun getCommit(fullname: String): Single<Response<List<Commits>>> {
        return api.getCommits(fullname)
    }
}