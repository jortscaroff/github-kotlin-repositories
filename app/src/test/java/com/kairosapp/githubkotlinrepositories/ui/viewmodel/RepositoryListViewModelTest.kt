package com.kairosapp.githubkotlinrepositories.ui.viewmodel

import com.kairosapp.githubkotlinrepositories.BaseTest
import com.kairosapp.githubkotlinrepositories.api.RepositoryRetriever
import com.kairosapp.githubkotlinrepositories.data.OwnerApi
import com.kairosapp.githubkotlinrepositories.data.RepositoryApi
import com.kairosapp.githubkotlinrepositories.data.RepositoryApiResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class RepositoryListViewModelTest : BaseTest() {

    private lateinit var repositoryRetrieverMock : RepositoryRetriever

    @Before
    fun setUp() {
        repositoryRetrieverMock = Mockito.mock(RepositoryRetriever::class.java)
    }

    @Test
    fun testThatFinalStateIsLoadedIfGetRepositoriesSuccessful() = runBlockingTest {
        val repositoryList = RepositoryApiResponse(listOf(repositoryApi1, repositoryApi2)).toModel()
        Mockito.`when`(repositoryRetrieverMock.getRepositories()).thenReturn(repositoryList)

        val repositoryListViewModel = RepositoryListViewModel(repositoryRetrieverMock)

        Mockito.verify(repositoryRetrieverMock).getRepositories()

        val finalState = repositoryListViewModel.state.value
        val expectedFinalState = RepositoryListViewModel.State.Loaded(repositoryList)

        assertEquals(expectedFinalState, finalState)
    }

    @Test
    fun testThatFinalStateIsErrorIfGetRepositoriesFailed() = runBlockingTest {
        val error = Throwable("Error")
        Mockito.`when`(repositoryRetrieverMock.getRepositories()).thenAnswer {
            throw error
        }

        val repositoryListViewModel = RepositoryListViewModel(repositoryRetrieverMock)

        Mockito.verify(repositoryRetrieverMock).getRepositories()

        val finalState = repositoryListViewModel.state.value
        val expectedFinalState = RepositoryListViewModel.State.Error(error)

        assertEquals(expectedFinalState, finalState)
    }

    companion object {
        val repositoryApi1 = RepositoryApi(
            1,
            "Name 1",
            "Full Name 1",
            OwnerApi("ownerlogin1", 1, "avatarurl1"),
            false,
            "htmlurl1",
            "Description 1",
            20,
            10,
            10,
            2
        )
        val repositoryApi2 = RepositoryApi(
            2,
            "Name 2",
            "Full Name 2",
            OwnerApi("ownerlogin2", 1, "avatarurl2"),
            false,
            "htmlurl2",
            "Description 2",
            20,
            10,
            10,
            2
        )
    }
}