package com.flicker.flickersearch.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.flicker.domain.api.SearchResponse
import com.flicker.flickersearch.R
import com.flicker.flickersearch.core.model.DataStatus
import com.flicker.flickersearch.ui.detail.DetailActivity
import com.flicker.flickersearch.utils.DEFAULT_QUERY
import com.flicker.flickersearch.utils.hide
import com.flicker.flickersearch.utils.hideKeyboard
import com.flicker.flickersearch.utils.isConnectingToInternet
import com.flicker.flickersearch.utils.show
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModel()
    val queryAdapter: QueryResultAdapter by lazy { QueryResultAdapter(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar_home)

        supportActionBar?.let {
            it.setDisplayShowHomeEnabled(true)
            it.title = "Flickr Search"
        }
        initView()
        initViewModel()

    }


    private fun initViewModel() {
        fetchSearchApi()
        viewModel.mSearchLiveData.observe(this, Observer {
            when (it?.status) {
                DataStatus.SUCCESS -> {
                    showResult(it.data)
                }
                DataStatus.LOADING -> {
                }
                DataStatus.ERROR -> {
                    showError()
                }
            }
        })
    }


    private fun showError(mErrormessage: String? = getString(R.string.something_went_wrong)) {
        Snackbar.make(cod_home, mErrormessage.toString(), Snackbar.LENGTH_LONG).show()
    }

    private fun showResult(data: SearchResponse?) {
        data?.photos?.listphotos?.let {
            if (it.isNotEmpty()) {
                queryRecyclerView.show()
                empty_view.hide()
                queryAdapter.updateItems(it)
            } else {
                queryRecyclerView.hide()
                empty_view.show()
            }

        } ?: run {
            queryRecyclerView.hide()
            empty_view.show()
        }

    }

    private fun initView() {
        queryRecyclerView.let {
            it.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            it.setHasFixedSize(true)
            it.isNestedScrollingEnabled = false
            it.adapter = queryAdapter.apply {
                onRowclicked = { mPhoto ->
                    startDetailActivity(mPhoto.id,mPhoto.title)
                }
            }
        }

        btn_search.setOnClickListener {
            if (!edit_search.text.isNullOrEmpty()) {
                fetchSearchApi(edit_search.text.toString())
                hideKeyboard()
            }
        }
        edit_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                fetchSearchApi(edit_search.text.toString())
                hideKeyboard()
                true
            } else {
                false
            }
        }
    }

    private fun fetchSearchApi(mQuery: String? = DEFAULT_QUERY) {
        if (isConnectingToInternet()) {
            viewModel.getQueryResult(mQuery.toString())
        } else {
            showError(getString(R.string.not_connected_to_internet))
        }
    }

    fun startDetailActivity(photoID: String?, title: String?) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("photoID", photoID)
        intent.putExtra("title", title)
        startActivity(intent)
    }
}
