package com.flicker.flickersearch.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.flicker.flickersearch.api.repository.HomeRepository
import com.flicker.flickersearch.core.model.Data
import com.flicker.flickersearch.core.model.DataStatus
import com.flicker.domain.api.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Pratik Behera on 2019-12-30.
 */
class HomeViewModel(val repository: HomeRepository) : ViewModel() {

    private val mSearchResponse: MutableLiveData<Data<SearchResponse>> = MutableLiveData()

    val mSearchLiveData: LiveData<Data<SearchResponse>> = mSearchResponse


    fun getQueryResult(query: String) {
        mSearchResponse.postValue(Data(DataStatus.LOADING))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val fetchResult = repository.getSearchResult(query)
                mSearchResponse.postValue(Data(DataStatus.SUCCESS, fetchResult))
            } catch (exception: java.lang.Exception) {
                mSearchResponse.postValue(Data(DataStatus.ERROR, error = exception))
            }
        }
    }

}