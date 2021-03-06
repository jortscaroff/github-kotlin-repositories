package com.kairosapp.githubkotlinrepositories.api

import com.kairosapp.githubkotlinrepositories.domain.Issue
import com.kairosapp.githubkotlinrepositories.domain.IssuesByWeek
import com.kairosapp.githubkotlinrepositories.domain.Repository
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class RepositoryRetrieverImpl @Inject constructor (private val githubService: GithubService) : RepositoryRetriever {

    override suspend fun getRepositories(): List<Repository> {
        val apiRepositories = githubService.fetchRepositories(
            query = "language:kotlin",
            sort = "stars",
            order = "desc",
            perPage = 100,
            page = 1
        )
        val repositoriesList: MutableList<Repository> = mutableListOf()
        apiRepositories.items.forEach { repository ->
            repositoriesList.add(repository.toModel())
        }
        return repositoriesList
    }

    override suspend fun getRepoSubscribersCount(owner: String, repo: String): Int {
        return githubService.fetchRepoDetails(owner, repo).subscribersCount
    }

    override suspend fun getIssuesByWeek(
        owner: String,
        repo: String
    ): List<IssuesByWeek> {

        val apiIssues = githubService.fetchRepoIssues(
            owner = owner,
            repo = repo,
            since = DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now().minusYears(1)),
            perPage = 100,
            page = 1
        )

        val issuesByWeekList = mutableListOf<IssuesByWeek>()
        var weekStartDate = LocalDateTime.now().minusYears(1)
        var weekEndDate = weekStartDate.plusDays(7)
        for (i in 1 until 52) {
            issuesByWeekList.add(IssuesByWeek(0, i, weekStartDate, weekEndDate))
            weekStartDate = weekStartDate.plusDays(7)
            weekEndDate = weekEndDate.plusDays(7)
        }

        val issueList: MutableList<Issue> = mutableListOf()
        for (apiIssue in apiIssues) {
            issueList.add(apiIssue.toModel())
        }
        issueList.forEach { issue ->
            issuesByWeekList.forEach { issuesByWeek ->
                if ((issuesByWeek.weekStartDate.isBefore(issue.createdAt))
                    && issuesByWeek.weekEndDate.isAfter(issue.createdAt)
                ) {
                    issuesByWeek.amount++
                }
            }
        }
        return issuesByWeekList.filter { it.amount > 0 }
    }
}