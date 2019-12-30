package com.flicker.flickersearch.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.flicker.domain.api.SearchResponse
import com.flicker.flickersearch.R
import com.flicker.flickersearch.core.model.DataStatus
import com.flicker.flickersearch.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModel()
    val queryAdapter: QueryResultAdapter by lazy { QueryResultAdapter(this) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        initViewModel()
    }

    private fun initRecyclerView() {
        queryRecyclerView.let {
            it.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            it.setHasFixedSize(true)
            it.isNestedScrollingEnabled = false
            it.adapter = queryAdapter
        }
    }

    private fun initViewModel() {
        viewModel.getQueryResult("nature")


        viewModel.mSearchLiveData.observe(this, Observer {
            when (it?.status) {
                DataStatus.SUCCESS -> {
                    showResult(it.data)
                }
                DataStatus.LOADING -> {}
                DataStatus.ERROR -> {}
            }
        })
    }

    private fun showResult(data: SearchResponse?) {
        data?.photos?.listphotos?.let {
            queryAdapter.updateItems(it)
        }

    }
}
