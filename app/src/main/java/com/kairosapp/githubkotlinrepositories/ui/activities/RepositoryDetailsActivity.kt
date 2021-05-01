package com.kairosapp.githubkotlinrepositories.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kairosapp.githubkotlinrepositories.*
import com.kairosapp.githubkotlinrepositories.databinding.ActivityRepositoryDetailsBinding
import com.kairosapp.githubkotlinrepositories.ui.viewmodel.RepositoryDetailsViewModel

private const val TAG = "RepositoryDetailsAct"

class RepositoryDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRepositoryDetailsBinding

    private val repositoryDetailsViewModel: RepositoryDetailsViewModel by lazy {
        val factory = viewModelFactory {
            RepositoryDetailsViewModel(
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
        binding = ActivityRepositoryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progressRepositoryDetails = binding.progressRepositoryDetails

        repositoryDetailsViewModel.state.observe(this, { state ->
            when (state) {
                is RepositoryDetailsViewModel.State.Loading -> {
                    Log.d(TAG, "onCreate: ${repositoryDetailsViewModel.state.value}")
                    binding.detailsScrollView.visibility = View.GONE
                    progressRepositoryDetails.visibility = View.VISIBLE

                }
                is RepositoryDetailsViewModel.State.Loaded -> {
                    Log.d(TAG, "onCreate: ${repositoryDetailsViewModel.state.value}")
                    progressRepositoryDetails.visibility = View.GONE
                    setTextViews()
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