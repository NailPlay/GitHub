package com.mbrains.presentation.ui.favorites

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mbrains.R
import com.mbrains.data.models.Repos
import com.mbrains.presentation.ui.callback.ItemClickListener
import com.mbrains.presentation.ui.viewholder.NetworkStateViewHolder
import com.mbrains.presentation.ui.viewholder.ReposViewHolder
import com.mbrains.R.id.imageView
import android.graphics.Picture
import kotlinx.android.synthetic.main.repo_item.view.*


class FavoritesAdapter(
    var items: List<Repos>,
    var listener : ItemClickListener
) : RecyclerView.Adapter<ReposViewHolder>() {


    fun updateItems(item: List<Repos>) {
        this.items = item
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
       return ReposViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.view.repo_name.text = items.get(position).name!!
        holder.view.repo_name.setOnClickListener({ v -> listener.onItemClicked(items.get(position)) })

    }



    override fun getItemCount(): Int {
        return items.size
    }



}