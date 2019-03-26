package com.mbrains.presentation.ui.favorites

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mbrains.data.models.Repos
import com.mbrains.presentation.ui.callback.ItemClickListener
import com.mbrains.presentation.ui.viewholder.ReposViewHolder
import com.mcxtzhang.swipemenulib.SwipeMenuLayout
import kotlinx.android.synthetic.main.repo_item.view.*


class FavoritesAdapter(
    private var items: List<Repos>,
    private var listenerItem : ItemClickListener,
    private var listenerDelete: ItemClickListener
) : RecyclerView.Adapter<ReposViewHolder>() {


    fun updateItems(item: List<Repos>) {
        this.items = item
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
       return ReposViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.view.repo_name.text = items[position].name
        holder.view.repo_description.text = items[position].description
        holder.view.repo_language.text = items[position].language
        holder.view.repo_stars.text = items[position].startCount.toString()
        holder.view.repo_forks.text = items[position].forks_count
        holder.view.cardViewContent.setOnClickListener { v -> listenerItem.onItemClicked(items[position]) }
        holder.view.btnSwipe.text = "Delete"
        holder.view.btnSwipe.setBackgroundColor(Color.RED)
        holder.view.btnSwipe.setOnClickListener {
            (holder.itemView.swipeMenu as SwipeMenuLayout).quickClose()
            listenerDelete.onItemClicked(items[position])
        }

    }



    override fun getItemCount(): Int {
        return items.size
    }



}