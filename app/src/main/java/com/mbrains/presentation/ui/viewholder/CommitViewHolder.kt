package com.mbrains.presentation.ui.viewholder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mbrains.R
import com.mbrains.data.models.Commits
import kotlinx.android.synthetic.main.commit_item.view.*

class CommitViewHolder (val view: View) : RecyclerView.ViewHolder(view) {

    fun bindTo(commits: Commits?){
        itemView.message.text = commits?.commit?.message.toString()
        itemView.nameAuthor.text = commits?.commit?.author.toString()
        itemView.date.text = commits?.commit?.author?.date.toString()
    }

    companion object {
        fun create(parent: ViewGroup): CommitViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.commit_item, parent, false)
            return CommitViewHolder(view)
        }
    }
}