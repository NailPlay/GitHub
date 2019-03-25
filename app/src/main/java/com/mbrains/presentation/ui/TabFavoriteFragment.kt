package com.mbrains.presentation.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mbrains.R
import com.mbrains.presentation.ui.favorites.FavoritesFragment


class TabFavoriteFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v =  inflater.inflate(R.layout.fragment_tab_favorite, container, false)
        val manager = childFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.tabfavorite_frame, FavoritesFragment())
        transaction.commit()
        return v
    }

}
