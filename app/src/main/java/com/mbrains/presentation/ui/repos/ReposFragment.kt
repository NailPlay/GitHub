package com.mbrains.presentation.ui.repos

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.mbrains.R
import com.mbrains.data.datasource.NetworkState
import com.mbrains.data.datasource.Status
import com.mbrains.data.models.Repos
import com.mbrains.domain.RealmManager
import com.mbrains.presentation.ui.callback.ItemClickListener
import com.mbrains.presentation.ui.details.DetailFragment
import kotlinx.android.synthetic.main.fragment_repos.*
import kotlinx.android.synthetic.main.fragment_repos.view.*

class ReposFragment : Fragment() {

    private lateinit var reposViewModel: ReposViewModel

    private lateinit var reposAdapter: ReposAdapter

    private var request: String = ""

    val realmManager = RealmManager()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reposViewModel = ViewModelProviders.of(this)
            .get(ReposViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etQuery.setOnEditorActionListener { v, actionId, event ->
            if (actionId === EditorInfo.IME_ACTION_DONE) {
                request = etQuery.text.toString()
                loadRequest(request)
                return@setOnEditorActionListener true
            }
            false
        }

        etQuery.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                request = etQuery.text.toString()
                loadRequest(request)
                return@OnKeyListener true
            }
            false
        })

        swipeContainer.setOnRefreshListener {
            loadRequest(request)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_repos, container, false)

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.rvRepos.layoutManager = linearLayoutManager

        reposAdapter = ReposAdapter({
            reposViewModel.retry()
        }, object : ItemClickListener {
            override fun onItemClicked(repos: Repos) {
                val transaction = parentFragment!!.childFragmentManager.beginTransaction()
                val name = repos.name ?: "-"
                val fullname = repos.fullName ?: "-"
                val html_url = repos.html_url ?: "-"
                val avatar_url = repos.owner_!!.avatarUrl ?: ""
                transaction.replace(
                    R.id.tabrepos_frame,
                    DetailFragment.newInstance(name, fullname, html_url, avatar_url)
                )
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }, object : ItemClickListener {
            override fun onItemClicked(repos: Repos) {
                Toast.makeText(context, "Added", Toast.LENGTH_LONG).show()

                realmManager.insertNoDublicate(repos)
            }

        })
        view.rvRepos.adapter = reposAdapter

        reposViewModel.reposList?.observe(this@ReposFragment, Observer<PagedList<Repos>> {
            reposAdapter?.submitList(it)
        })

        return view
    }

    private fun loadRequest(name: String) {
        Log.d("NAIL", "loadRequest")

        reposViewModel.initSearch(name)
        reposViewModel.reposList?.observe(this@ReposFragment, Observer<PagedList<Repos>> {
            Log.d("NAIL", "it: " + it!!.size.toString())
            reposAdapter.submitList(it)
        })
        reposViewModel.getNetworkState().observe(this@ReposFragment, Observer<NetworkState> {
            reposAdapter.setNetworkState(it)
        })
        reposViewModel.getInitialState().observe(this@ReposFragment, Observer { networkState ->
            Log.d(
                "NAIL", networkState!!.status.toString() + " - "
                        +
                        (networkState?.status == Status.SUCCESS)
            )
            when (networkState.status) {
                Status.RUNNING -> {
                    if (!swipeContainer.isRefreshing)
                        progressBar.visibility = View.VISIBLE

                }
                Status.SUCCESS -> {
                    swipeContainer.isRefreshing = false
                    progressBar.visibility = View.GONE

                }
                Status.FAILED -> {
                    Toast.makeText(context, "Error: " + networkState.message, Toast.LENGTH_LONG).show()
                    swipeContainer.isRefreshing = false
                    progressBar.visibility = View.GONE
                }
            }

        })
    }


}
