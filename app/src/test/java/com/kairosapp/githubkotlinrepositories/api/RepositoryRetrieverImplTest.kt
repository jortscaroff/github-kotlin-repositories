package com.kairosapp.githubkotlinrepositories.api

import com.kairosapp.githubkotlinrepositories.BaseTest
import com.kairosapp.githubkotlinrepositories.data.OwnerApi
import com.kairosapp.githubkotlinrepositories.data.RepositoryApi
import com.kairosapp.githubkotlinrepositories.data.RepositoryApiResponse
import com.kairosapp.githubkotlinrepositories.domain.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito

class RepositoryRetrieverImplTest : BaseTest() {

    @ExperimentalCoroutinesApi
    @Test
    fun testThatGetRepositoriesReturnsTheCorrectList() = runBlockingTest {
        val (repositoryRetriever) = createTestData()

        val repositoryList = repositoryRetriever.getRepositories()

        val expectedResult = RepositoryApiResponse(listOf(repositoryApi1, repositoryApi2)).toModel()
        assertEquals(expectedResult, repositoryList)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testThatGetRepositoriesReturnsTheCorrectListWhenRepositoryListIsEmpty() = runBlockingTest {
        val (repositoryRetriever, githubService) = createTestData()

        Mockito.`when`((githubService.fetchRepositories(
            query = "language:kotlin",
            sort = "stars",
            order = "desc",
            perPage = 100,
            page = 1
        ))).thenReturn(
            RepositoryApiResponse(listOf()))
        val repositoryResult = repositoryRetriever.getRepositories()
        val expectedResult = emptyList<Repository>()
        assertEquals(expectedResult, repositoryResult)
    }


    private fun createTestData(): Pair<RepositoryRetriever, GithubService> = runBlocking {
        val githubServiceMock = Mockito.mock(GithubService::class.java)

        val repositoryApiList = RepositoryApiResponse(listOf(repositoryApi1, repositoryApi2))

        Mockito.`when`(githubServiceMock.fetchRepositories(
            query = "language:kotlin",
            sort = "stars",
            order = "desc",
            perPage = 100,
            page = 1
        )).thenReturn(repositoryApiList)

        return@runBlocking Pair(RepositoryRetrieverImpl(githubServiceMock), githubServiceMock)
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