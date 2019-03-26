package com.mbrains.presentation.ui.viewholder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mbrains.R
import com.mbrains.data.models.Repos
import kotlinx.android.synthetic.main.repo_item.view.*

class ReposViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


    fun bindTo(repos: Repos?){
        itemView.repo_name.text = repos?.name
        itemView.repo_stars.text = repos?.startCount.toString()
        itemView.repo_language.text = repos?.language
        itemView.repo_description.text = repos?.description
        itemView.repo_forks.text = repos?.forks_count.toString()
    }

    companion object {
        fun create(parent: ViewGroup): ReposViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.repo_item, parent, false)
            return ReposViewHolder(view)
        }
    }
}