package com.mbrains.presentation.ui.favorites

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.recyclerview.R.attr.layoutManager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mbrains.R
import com.mbrains.data.models.Repos
import com.mbrains.domain.RealmManager
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_favorites.view.*
import android.arch.lifecycle.ViewModelProviders
import com.mbrains.presentation.ui.repos.ReposViewModel
import java.util.Collections.emptyList
import android.support.v7.widget.RecyclerView
import com.mbrains.presentation.ui.callback.ItemClickListener
import com.mbrains.presentation.ui.details.DetailFragment
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FavoritesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FavoritesFragment : Fragment() {

    val realmManager =  RealmManager()
    private lateinit var favoritesViewModel: FavoritesViewModel

    private val recyclerView: RecyclerView? = null
    private var adapter: FavoritesAdapter? = null
    private val favoriteList = Collections.emptyList<Repos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.fragment_favorites, container, false)
        adapter = FavoritesAdapter(favoriteList, object : ItemClickListener {
            override fun onItemClicked(repos: Repos) {
                Log.d("NAIL","onClick")
                if (!repos.name.isNullOrBlank()) {

                    val transaction = getParentFragment()!!.childFragmentManager.beginTransaction()
                    val name = repos.name!!
                    transaction.replace(R.id.tabfavorite_frame, DetailFragment.newInstance(name, ""))
                    transaction.addToBackStack(null);
                    transaction.commit()




                }
            }
        })
        v.rvFavorite.adapter = adapter

        favoritesViewModel.getFavorite().observe(this, Observer { peple ->

            Log.d("NAIL", "Size: " + peple!!.size.toString())
            peple!!.forEach { x ->
                Log.d("NAIL", x.id.toString() + " - "+ x.name.toString())
            }

            var list = realmManager.findAll();
            adapter!!.updateItems(list)

        })

        val layoutManager = LinearLayoutManager(activity)
        v.rvFavorite.layoutManager = layoutManager
        v.rvFavorite.itemAnimator = DefaultItemAnimator()

        v.button.setOnClickListener({
            var r = Random()
            realmManager.insert(r.nextLong().toString())
        })

        return v
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
