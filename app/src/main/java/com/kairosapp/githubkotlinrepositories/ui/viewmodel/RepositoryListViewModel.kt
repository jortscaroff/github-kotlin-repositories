package com.kairosapp.githubkotlinrepositories.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kairosapp.githubkotlinrepositories.api.RepositoryRetriever
import com.kairosapp.githubkotlinrepositories.domain.Repository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class RepositoryListViewModel : ViewModel() {

    var state = MutableLiveData<State>()

    private val handler = CoroutineExceptionHandler { _, exception -> state.value = State.Error(exception) }

    init {
        state.value = State.NotStarted

        viewModelScope.launch(handler) {
            state.value = State.Loading
            val resultList = RepositoryRetriever().getRepositories()
            state.value = State.Loaded(resultList)
        }
    }

    sealed class State() {
        object NotStarted: State()
        object Loading: State()
        data class Loaded(val repositories: List<Repository>): State()
        data class Error(val error: Throwable): State()
    }
}