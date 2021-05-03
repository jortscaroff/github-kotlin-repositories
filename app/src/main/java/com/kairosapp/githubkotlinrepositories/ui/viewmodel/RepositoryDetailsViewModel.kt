package com.kairosapp.githubkotlinrepositories.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kairosapp.githubkotlinrepositories.api.RepositoryRetriever
import com.kairosapp.githubkotlinrepositories.domain.IssuesByWeek
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class RepositoryDetailsViewModel @Inject constructor (
    private val repositoryRetriever : RepositoryRetriever,
    private val owner: String,
    private val repositoryName: String) : ViewModel() {

    var state = MutableLiveData<State>()

    private val handler = CoroutineExceptionHandler { _, exception -> state.value = State.Error(exception) }

    init {
        state.value = State.NotStarted

        viewModelScope.launch(handler) {
            state.value = State.Loading
            val resultList = repositoryRetriever.getIssuesByWeek(owner, repositoryName, LocalDateTime.now().minusYears(1))
            state.value = State.Loaded(resultList)
        }
    }

    sealed class State() {
        object NotStarted: State()
        object Loading: State()
        data class Loaded(val issues: List<IssuesByWeek>): State()
        data class Error(val error: Throwable): State()
    }
}