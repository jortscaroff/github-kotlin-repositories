package com.kairosapp.githubkotlinrepositories.ui.viewmodel

import com.kairosapp.githubkotlinrepositories.BaseTest
import com.kairosapp.githubkotlinrepositories.api.RepositoryRetriever
import com.kairosapp.githubkotlinrepositories.domain.IssuesByWeek
import com.kairosapp.githubkotlinrepositories.initThreeTen
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.threeten.bp.LocalDateTime

class RepositoryDetailsViewModelTest : BaseTest() {
    private lateinit var repositoryRetrieverMock: RepositoryRetriever
    private lateinit var owner: String
    private lateinit var repositoryName: String
    private lateinit var dateNow: LocalDateTime
    private lateinit var issuesByWeek1: IssuesByWeek
    private lateinit var issuesByWeek2: IssuesByWeek

    init {
        initThreeTen()
    }

    @Before
    fun setUp() {
        dateNow = LocalDateTime.now()
        issuesByWeek1 = IssuesByWeek(5, 1, dateNow.minusDays(7), dateNow)
        issuesByWeek2 = IssuesByWeek(5, 1, dateNow.minusDays(7), dateNow)
        repositoryRetrieverMock = Mockito.mock(RepositoryRetriever::class.java)
        owner = "owner"
        repositoryName = "repository-name"
    }

    @Test
    fun testThatFinalStateIsLoadedIfGetIssuesByWeekAndSubscribersCountSuccessful() =
        runBlockingTest {
            val issuesByWeekList = listOf(issuesByWeek1, issuesByWeek2)
            val subscribersCount = 20

            Mockito.`when`(
                repositoryRetrieverMock.getIssuesByWeek(
                    owner,
                    repositoryName
                )
            ).thenReturn(issuesByWeekList)

            Mockito.`when`(repositoryRetrieverMock.getRepoSubscribersCount(owner, repositoryName))
                .thenReturn(subscribersCount)

            val repositoryDetailsViewModel =
                RepositoryDetailsViewModel(repositoryRetrieverMock, owner, repositoryName)

            Mockito.verify(repositoryRetrieverMock)
                .getIssuesByWeek(owner, repositoryName)
            Mockito.verify(repositoryRetrieverMock).getRepoSubscribersCount(owner, repositoryName)

            val finalState = repositoryDetailsViewModel.state.value
            val expectedFinalState = RepositoryDetailsViewModel.State.Loaded(20, issuesByWeekList)

            Assert.assertEquals(expectedFinalState, finalState)
        }

}