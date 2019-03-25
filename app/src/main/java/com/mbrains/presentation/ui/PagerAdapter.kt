package com.mbrains.presentation.ui

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.mbrains.R
import com.mbrains.presentation.ui.favorites.FavoritesFragment
import com.mbrains.presentation.ui.repos.ReposFragment

class PagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 ->
                TabReposFragment()
            else ->
                TabFavoriteFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> mContext.getString(R.string.repository)
            else -> {
                return mContext.getString(R.string.favorites)
            }
        }
    }

}