package com.kairosapp.githubkotlinrepositories.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.threetenabp.AndroidThreeTen
import com.kairosapp.githubkotlinrepositories.*
import com.kairosapp.githubkotlinrepositories.api.GithubService
import com.kairosapp.githubkotlinrepositories.api.RepositoryRetrieverImpl
import com.kairosapp.githubkotlinrepositories.databinding.ActivityRepositoryDetailsBinding
import com.kairosapp.githubkotlinrepositories.ui.adapter.RepositoryIssuesByWeekRecyclerAdapter
import com.kairosapp.githubkotlinrepositories.ui.viewmodel.RepositoryDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "RepositoryDetailsAct"

@AndroidEntryPoint
class RepositoryDetailsActivity : AppCompatActivity() {


    private lateinit var binding: ActivityRepositoryDetailsBinding

    private val repositoryDetailsViewModel: RepositoryDetailsViewModel by lazy {
        val factory = viewModelFactory {
            // I couldn't find how to inject the RepositoryRetriever necessary for RepositoryDetailsViewModel
            // due to the need of repository owner and name parameters for the viewmodel
            RepositoryDetailsViewModel(
                RepositoryRetrieverImpl(
                        Retrofit.Builder()
                            .baseUrl("https://api.github.com")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(GithubService::class.java)),
                intent.extras?.getString(REPOSITORY_OWNER)!!,
                intent.extras?.getString(REPOSITORY_NAME)!!
            )
        }
        ViewModelProviders.of(this, factory)
            .get(RepositoryDetailsViewModel::class.java)
    }

    inline fun <VM : ViewModel> viewModelFactory(
        crossinline f: () -> VM
    ) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>) =
                f() as T
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidThreeTen.init(this)

        binding = ActivityRepositoryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progressRepositoryDetails = binding.progressRepositoryDetails

        val listIssuesByWeek = binding.listIssues
        listIssuesByWeek.layoutManager = LinearLayoutManager(this)

        repositoryDetailsViewModel.state.observe(this, { state ->
            when (state) {
                is RepositoryDetailsViewModel.State.Loading -> {
                    Log.d(TAG, "onCreate: ${repositoryDetailsViewModel.state.value}")
                    binding.detailsScrollView.visibility = View.GONE
                    progressRepositoryDetails.visibility = View.VISIBLE

                }
                is RepositoryDetailsViewModel.State.Loaded -> {
                    Log.d(TAG, "onCreate: ${repositoryDetailsViewModel.state.value}")
                    Log.d(TAG, "onCreate: ${state.issues.size}")
                    progressRepositoryDetails.visibility = View.GONE
                    setTextViews()
                    listIssuesByWeek.adapter = RepositoryIssuesByWeekRecyclerAdapter(this, state.issues)
                    binding.detailsScrollView.visibility = View.VISIBLE
                }
                is RepositoryDetailsViewModel.State.Error -> {
                    Log.d(TAG, "onCreate: ${repositoryDetailsViewModel.state.value}")
                    binding.detailsScrollView.visibility = View.GONE
                    progressRepositoryDetails.visibility = View.GONE

                }
                is RepositoryDetailsViewModel.State.NotStarted -> {
                    Log.d(TAG, "onCreate: ${repositoryDetailsViewModel.state.value}")
                    binding.detailsScrollView.visibility = View.GONE
                    progressRepositoryDetails.visibility = View.GONE

                }
            }
        })

        Log.d(TAG, "onCreate: ${intent.extras?.getString(REPOSITORY_OWNER)}")
    }

    private fun setTextViews() {
        binding.textRepositoryName.text =
            intent.extras?.getString(REPOSITORY_NAME)
        binding.textRepositoryDescription.text =
            intent.extras?.getString(REPOSITORY_DESCRIPTION)
        binding.textWatchersCount.text =
            intent.extras?.getInt(REPOSITORY_WATCHERS_COUNT).toString()
        binding.textStargazersCount.text =
            intent.extras?.getInt(REPOSITORY_STARS_COUNT).toString()
        binding.textForksCount.text =
            intent.extras?.getInt(REPOSITORY_FORKS_COUNT).toString()
    }
}