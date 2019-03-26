package com.mbrains.presentation.ui.favorites

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mbrains.R
import com.mbrains.data.models.Repos
import com.mbrains.domain.RealmManager
import com.mbrains.presentation.ui.callback.ItemClickListener
import com.mbrains.presentation.ui.details.DetailFragment
import kotlinx.android.synthetic.main.fragment_favorites.view.*
import java.util.*


class FavoritesFragment : Fragment() {

    val realmManager = RealmManager()
    private lateinit var favoritesViewModel: FavoritesViewModel

    private val recyclerView: RecyclerView? = null
    private var adapter: FavoritesAdapter? = null
    private val favoriteList = Collections.emptyList<Repos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.fragment_favorites, container, false)
        adapter = FavoritesAdapter(favoriteList, object : ItemClickListener {
            override fun onItemClicked(repos: Repos) {
                val transaction = getParentFragment()!!.childFragmentManager.beginTransaction()
                val name = repos.name ?: "-"
                val fullname = repos.fullName ?: "-"
                val html_url = repos.html_url ?: "-"
                val avatar_url = repos.avatar_url ?: "-"
                transaction.replace(
                    R.id.tabfavorite_frame,
                    DetailFragment.newInstance(name, fullname, html_url, avatar_url)
                )
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }, object : ItemClickListener {
            override fun onItemClicked(repos: Repos) {
                realmManager.deleteById(repos.id!!)
                Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show()
            }

        }
        )
        v.rvFavorite.adapter = adapter

        favoritesViewModel.getFavorite().observe(this, Observer { peple ->
            Log.d("NAIL", "Size: " + peple!!.size.toString())
            peple?.forEach { x ->
                Log.d("NAIL", x.id.toString() + " - " + x.name.toString())
            }

            var list = realmManager.findAll()
            adapter!!.updateItems(list)

        })

        val layoutManager = LinearLayoutManager(activity)
        v.rvFavorite.layoutManager = layoutManager
        v.rvFavorite.itemAnimator = DefaultItemAnimator()
        return v
    }


}
