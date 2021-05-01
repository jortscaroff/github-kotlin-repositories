package com.kairosapp.githubkotlinrepositories.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kairosapp.githubkotlinrepositories.api.RepositoryRetriever
import com.kairosapp.githubkotlinrepositories.domain.IssuesByWeek
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime

class RepositoryDetailsViewModel (private val owner: String, private val repositoryName: String) : ViewModel() {

    var state = MutableLiveData<State>()

    private val handler = CoroutineExceptionHandler { _, exception -> state.value = State.Error(exception) }

    init {
        state.value = State.NotStarted

        viewModelScope.launch(handler) {
            state.value = State.Loading
            //val resultList = RepositoryRetriever().getIssues(owner, repositoryName, LocalDateTime.now().minusYears(1))
            //state.value = State.Loaded(resultList)
            val resultList2 = RepositoryRetriever().getIssuesByWeek(owner, repositoryName, LocalDateTime.now().minusYears(1))
            state.value = State.Loaded(resultList2)
        }
    }

    sealed class State() {
        object NotStarted: State()
        object Loading: State()
        data class Loaded(val issues: List<IssuesByWeek>): State()
        data class Error(val error: Throwable): State()
    }
}