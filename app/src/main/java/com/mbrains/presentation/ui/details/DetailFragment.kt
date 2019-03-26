package com.mbrains.presentation.ui.details

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mbrains.R
import com.mbrains.data.models.Commits
import com.mbrains.domain.ApiManager
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_detail.view.*
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"

class DetailFragment : Fragment() {

    private var name: String? = null
    private var full_name: String? = null
    private var html_url: String? = null
    private var avatar_url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_PARAM1)
            full_name = it.getString(ARG_PARAM2)
            html_url = it.getString(ARG_PARAM3)
            avatar_url = it.getString(ARG_PARAM4)
        }
    }

    private val commitsList = Collections.emptyList<Commits>()

    private var manager: ApiManager = ApiManager()
    private var compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_detail, container, false)
        v.tvName.text = name.toString()
        v.tvFullname.text = full_name.toString()
        v.tvHtmlrl.text = html_url.toString()
        Picasso.with(context).load(avatar_url).error(R.drawable.no_image).into(v.avatar)

        val adapter = CommitAdapter(commitsList)
        v.rvCommit.adapter = adapter
        val layoutManager = LinearLayoutManager(activity)
        v.rvCommit.layoutManager = layoutManager
        v.rvCommit.itemAnimator = DefaultItemAnimator()

        v.progressCommit.visibility = View.VISIBLE
        compositeDisposable.add(manager.getListOfCommits(full_name ?: "").subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ repos ->
                adapter.updateItems(repos)
                v.progressCommit.visibility = View.GONE
            }, { t ->
                Toast.makeText(context, "Error: " + t.message, Toast.LENGTH_LONG).show()
                v.progressCommit.visibility = View.GONE
            })
        )
        return v
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }

    companion object {
        @JvmStatic
        fun newInstance(name: String, full_name: String, html_url: String, avatar_url: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, name)
                    putString(ARG_PARAM2, full_name)
                    putString(ARG_PARAM3, html_url)
                    putString(ARG_PARAM4, avatar_url)
                }
            }
    }
}
