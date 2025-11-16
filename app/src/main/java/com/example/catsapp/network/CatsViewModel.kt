package com.example.catsapp.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for fetching and exposing a list of cats from the repository.
 * Handles success and error states using StateFlow.
 */
class CatsViewModel(
    private val repository: CatRepository
) : ViewModel() {

    private val _cats = MutableStateFlow<List<CatImage>>(emptyList())
    val cats: StateFlow<List<CatImage>> = _cats

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadCats() {
        viewModelScope.launch {
            when (val result = repository.getCats()) {
                is Result.Ok -> _cats.value = result.value
                is Result.Err -> _error.value = result.message
            }
        }
    }
}
