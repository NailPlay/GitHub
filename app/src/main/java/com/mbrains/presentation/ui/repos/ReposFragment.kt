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
import android.widget.Toast
import com.mbrains.R
import com.mbrains.data.datasource.NetworkState
import com.mbrains.data.datasource.Status
import com.mbrains.data.models.Repos
import kotlinx.android.synthetic.main.fragment_repos.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ReposFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ReposFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ReposFragment : Fragment() {

    private lateinit var reposViewModel: ReposViewModel

    private lateinit var reposAdapter: ReposAdapter

    var request: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reposViewModel = ViewModelProviders.of(this)
            .get(ReposViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvRepos.layoutManager = linearLayoutManager

        reposAdapter = ReposAdapter({
            reposViewModel.retry()
        }, object : ReposAdapter.ItemClickListener {
            override fun onItemClicked(repos: Repos) {
                if (!repos.url.isNullOrBlank()) {
                    // val i = Intent(Intent.ACTION_VIEW)
                    // i.data = Uri.parse(repos.url)
                    // startActivity(i)
                    Toast.makeText(context, "Repos: " + repos!!.name, Toast.LENGTH_LONG).show()
                }
            }
        })
        rvRepos.adapter = reposAdapter


        etQuery.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                request = etQuery.text.toString();
                loadRequest(request);
                return@OnKeyListener true
            }
            false
        })

        swipeContainer.setOnRefreshListener {
            loadRequest(request);
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_repos, container, false)

        return rootView
    }

    fun loadRequest(name: String) {

        reposViewModel.initSearch(name)


        reposViewModel.reposList!!.observe(this@ReposFragment, Observer<PagedList<Repos>> {
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
            when(networkState.status){
                Status.RUNNING -> {
                    if(!swipeContainer.isRefreshing)
                        progressBar.visibility = View.VISIBLE

                }
                Status.SUCCESS -> {
                    swipeContainer.isRefreshing = false;
                    progressBar.visibility = View.GONE

                }
                Status.FAILED -> {
                    Toast.makeText(context, "Error: " + networkState.message, Toast.LENGTH_LONG).show()
                    swipeContainer.isRefreshing = false;
                    progressBar.visibility = View.GONE
                }
            }

        })
    }


}
