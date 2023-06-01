package com.example.aisle.ui.dashboard.notes

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aisle.AisleApplication
import com.example.aisle.data.AisleRepository
import com.example.aisle.data.model.NotesApiResponse
import com.example.aisle.utils.Resource
import com.example.aisle.utils.hasInternetConnection
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NotesViewModel(private val aisleRepository: AisleRepository, app: Application) :
    AndroidViewModel(app) {

    private val _getNotesResult = MutableLiveData<Resource<NotesApiResponse>>()
    val getNotesResult: LiveData<Resource<NotesApiResponse>> =
        _getNotesResult

    fun getNotes(number: String) {
        viewModelScope.launch {
            safeGetNotesCall(number)
        }
    }

    private suspend fun safeGetNotesCall(auth: String) {
        _getNotesResult.postValue(Resource.Loading())
        try {
            val connectivityManager = getApplication<AisleApplication>().getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
            if (hasInternetConnection(connectivityManager)) {
                val response = aisleRepository.getNotes("32c7794d2e6a1f7316ef35aa1eb34541")
                _getNotesResult.postValue(handleGetNotesResponse(response))
            } else {
                _getNotesResult.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _getNotesResult.postValue(Resource.Error("Network Failure"))
                else -> _getNotesResult.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleGetNotesResponse(response: Response<NotesApiResponse>): Resource<NotesApiResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}