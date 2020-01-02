package com.flicker.flickersearch.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebSettings.ZoomDensity
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.flicker.domain.api.SearchDetailResponse
import com.flicker.flickersearch.R
import com.flicker.flickersearch.core.model.DataStatus
import com.flicker.flickersearch.utils.hide
import com.flicker.flickersearch.utils.isConnectingToInternet
import com.flicker.flickersearch.utils.show
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by Pratik Behera on 2019-12-31.
 */
class DetailActivity: AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar_detail)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_arrow_left)
            it.title = intent?.extras?.getString("title")
        }
        initView()
        initViewModel()

    }

    private fun initView() {
        webview_detail?.let {
            it.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    it.loadUrl("javascript:(function() { " +
                            "var head = document.getElementsByClassName('view global-nav-view')[0].style.display='none'; " +
                            "})()")
                }
            }


            val zoomDensity = ZoomDensity.FAR
            it.settings.javaScriptEnabled = true
            it.settings.defaultTextEncodingName = "utf-8"
            it.settings.loadWithOverviewMode = true
            it.settings.defaultZoom = zoomDensity
            it.settings.setSupportZoom(true)
            it.settings.builtInZoomControls = true
            it.requestFocus(View.FOCUS_DOWN)
            it.isHorizontalScrollBarEnabled = false

            it.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
        }
    }

    private fun initViewModel() {
        intent?.extras?.getString("photoID")?.let {
            if(it.isNotEmpty()) {
                fetchDetailApi(it)
            }
        }
        viewModel.mDetailLiveData.observe(this, Observer {
            when (it?.status) {
                DataStatus.SUCCESS -> {
                    showResult(it.data)
                    GlobalScope.launch(Dispatchers.Main) {
                        delay(1700)
                        frame_loadingview.hide()
                    }
                }
                DataStatus.LOADING -> {
                    frame_loadingview.show()
                }
                DataStatus.ERROR -> {
                    frame_loadingview.hide()
                    showError()
                }
            }
        })
    }

    private fun showResult(data: SearchDetailResponse?) {
        data?.photo?.let { mData ->
            /*val thumbnailLink = getFlickrImageLink(mData.id, mData.secret, mData.server, mData.farm!!.toInt(), SMALL_360)
            img_detail.loadImageFromLink(thumbnailLink)*/
            mData.urls?.listurl?.let { mUrl ->
                if(mUrl.isNotEmpty()) {
                    webview_detail?.loadUrl(mUrl.first().imageurl)
                    GlobalScope.launch(Dispatchers.Main) {
                        delay(1500)
                        webview_detail.show()
                    }
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showError(mErrormessage: String? = getString(R.string.something_went_wrong)) {
        Snackbar.make(cod_detail, mErrormessage.toString(), Snackbar.LENGTH_LONG).show()
    }

    private fun fetchDetailApi(mPhotoID: String) {
        if (isConnectingToInternet()) {
            viewModel.getDetailResult(mPhotoID)
        } else {
            showError(getString(R.string.not_connected_to_internet))
        }
    }

}