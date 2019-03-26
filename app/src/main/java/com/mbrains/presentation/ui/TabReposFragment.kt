package com.mbrains.presentation.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mbrains.R
import com.mbrains.presentation.ui.repos.ReposFragment

class TabReposFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =  inflater.inflate(R.layout.fragment_tab_repos, container, false)
        val manager = childFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.tabrepos_frame, ReposFragment())
        transaction.commit()
        return v
    }

}
