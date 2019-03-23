package com.mbrains.presentation.ui.favorites

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mbrains.data.models.Repos
import com.mbrains.presentation.ui.callback.ItemClickListener
import com.mbrains.presentation.ui.viewholder.ReposViewHolder

class FavoritesAdapter(
    var listener : ItemClickListener
) : RecyclerView.Adapter<ReposViewHolder>() {

    private val reposList: List<Repos>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        return ReposViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return reposList!!.count()
    }

    override fun onBindViewHolder(viewHolder: ReposViewHolder, position: Int) {

    }
}