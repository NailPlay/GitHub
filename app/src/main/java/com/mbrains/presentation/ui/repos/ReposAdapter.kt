package com.mbrains.presentation.ui.repos

import android.arch.paging.PagedListAdapter
import android.graphics.Color
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mbrains.R
import com.mbrains.data.datasource.NetworkState
import com.mbrains.data.models.Repos
import com.mbrains.presentation.ui.callback.ItemClickListener
import com.mbrains.presentation.ui.viewholder.NetworkStateViewHolder
import com.mbrains.presentation.ui.viewholder.ReposViewHolder
import com.mcxtzhang.swipemenulib.SwipeMenuLayout
import kotlinx.android.synthetic.main.repo_item.view.*

class ReposAdapter(
    private val retryCallback: () -> Unit,
    var listenerItem: ItemClickListener,
    var listenerAdd: ItemClickListener
) :
    PagedListAdapter<Repos, RecyclerView.ViewHolder>(ReposDiffCallback) {


    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.repo_item) {
            ReposViewHolder.create(parent)
        } else {
            NetworkStateViewHolder.create(parent, retryCallback)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.repo_item -> {
                var repos = getItem(position)
                (holder as ReposViewHolder).bindTo(getItem(position))
                holder.itemView.cardViewContent.setOnClickListener({ v -> listenerItem.onItemClicked(repos!!) })
                holder.view.btnSwipe.text = "Add"
                holder.view.btnSwipe.setBackgroundColor(Color.DKGRAY)
                holder.view.btnSwipe.setOnClickListener({
                    listenerAdd.onItemClicked(repos!!)
                    (holder.itemView.swipeMenu as SwipeMenuLayout).quickClose()

                })
            }
            R.layout.network_item -> (holder as NetworkStateViewHolder).bindTo(networkState)
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_item
        } else {
            R.layout.repo_item
        }
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        if (currentList != null) {
            if (currentList!!.size != 0) {
                val previousState = this.networkState
                val hadExtraRow = hasExtraRow()
                this.networkState = newNetworkState
                val hasExtraRow = hasExtraRow()
                if (hadExtraRow != hasExtraRow) {
                    if (hadExtraRow) {
                        notifyItemRemoved(super.getItemCount())
                    } else {
                        notifyItemInserted(super.getItemCount())
                    }
                } else if (hasExtraRow && previousState !== newNetworkState) {
                    notifyItemChanged(itemCount - 1)
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    companion object {
        val ReposDiffCallback = object : DiffUtil.ItemCallback<Repos>() {
            override fun areItemsTheSame(item0: Repos, item1: Repos): Boolean {
                return item0.id == item1.id
            }

            override fun areContentsTheSame(item0: Repos, item1: Repos): Boolean {
                return item0 == item1
            }

        }
    }


}