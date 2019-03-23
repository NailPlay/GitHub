package com.mbrains.presentation.ui.callback

import com.mbrains.data.models.Repos

interface ItemClickListener {
    fun onItemClicked(repos: Repos)
}