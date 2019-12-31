package com.flicker.flickersearch.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flicker.domain.api.SearchDetailResponse

import com.flicker.flickersearch.api.repository.HomeRepository
import com.flicker.flickersearch.core.model.Data
import com.flicker.flickersearch.core.model.DataStatus
import com.flicker.domain.api.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Pratik Behera on 2019-12-30.
 */
class DetailViewModel(val repository: HomeRepository) : ViewModel() {

    private val mSearchDetailResponse: MutableLiveData<Data<SearchDetailResponse>> = MutableLiveData()

    val mDetailLiveData: LiveData<Data<SearchDetailResponse>> = mSearchDetailResponse


    fun getDetailResult(photoID: String) {
        mSearchDetailResponse.postValue(Data(DataStatus.LOADING))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val fetchResult = repository.getDetailResult(photoID)
                mSearchDetailResponse.postValue(Data(DataStatus.SUCCESS, fetchResult))
            } catch (exception: java.lang.Exception) {
                mSearchDetailResponse.postValue(Data(DataStatus.ERROR, error = exception))
            }
        }
    }

}