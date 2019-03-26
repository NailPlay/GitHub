package com.mbrains.presentation.ui.details

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mbrains.data.models.Commits
import com.mbrains.presentation.ui.viewholder.CommitViewHolder
import kotlinx.android.synthetic.main.commit_item.view.*
import org.joda.time.format.DateTimeFormat


class CommitAdapter(
    var items: List<Commits>
) : RecyclerView.Adapter<CommitViewHolder>() {

    fun updateItems(item: List<Commits>) {
        this.items = item
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
        return CommitViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
        holder.view.message.text = items.get(position).commit?.message
        holder.view.nameAuthor.text = items.get(position).commit?.author?.name
        val dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val dt = dtf.parseDateTime(items.get(position).commit?.author?.date)
        val date = dt.toLocalDate()
        holder.view.date.text = date.toString()
    }


    override fun getItemCount(): Int {
        return items.size
    }

}