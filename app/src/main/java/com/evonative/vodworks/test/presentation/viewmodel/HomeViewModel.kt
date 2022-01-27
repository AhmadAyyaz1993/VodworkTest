package com.evonative.vodworks.test.presentation.viewmodel

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evonative.vodworks.test.VodWorksApplication
import com.evonative.vodworks.test.data.model.ItemData
import com.evonative.vodworks.test.data.network.ApiRepository
import com.evonative.vodworks.test.others.Utils
import com.evonative.vodworks.test.presentation.model.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {

    private val _userFlow = MutableStateFlow<UIState<List<ItemData>>>(UIState.Loading())
    val userFlow: StateFlow<UIState<List<ItemData>>> = _userFlow

    fun loadItems() {
        viewModelScope.launch {
            if (Utils.isNetworkAvailable(VodWorksApplication.getContext())) {
                apiRepository.getItemData()
                    .catch { exception ->
                        _userFlow.value = UIState.Error(exception.message)
                    }
                    .collect {
                        _userFlow.value = UIState.Success(it)
                    }
            }else{
                Toast.makeText(VodWorksApplication.getContext(),"Please check you connectivity.",Toast.LENGTH_SHORT).show();
            }
        }
    }
}