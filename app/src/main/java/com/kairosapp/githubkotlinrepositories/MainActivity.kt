package com.kairosapp.githubkotlinrepositories

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kairosapp.githubkotlinrepositories.databinding.ActivityMainBinding
import com.kairosapp.githubkotlinrepositories.ui.adapter.RepositoryRecyclerAdapter
import com.kairosapp.githubkotlinrepositories.ui.viewmodel.RepositoryListViewModel

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val repositoryListViewModel: RepositoryListViewModel by lazy {
        ViewModelProvider(this).get(RepositoryListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progressListRepositories = binding.progressListRepositories

        val listRepositories = binding.listRepositories
        listRepositories.layoutManager = LinearLayoutManager(this)

        repositoryListViewModel.state.observe(this, { state ->
            when (state) {
                is RepositoryListViewModel.State.Loading -> {
                    progressListRepositories.visibility = View.VISIBLE
                    listRepositories.visibility = View.GONE
                }
                is RepositoryListViewModel.State.Loaded -> {
                    progressListRepositories.visibility = View.GONE
                    listRepositories.visibility = View.VISIBLE
                    listRepositories.adapter = RepositoryRecyclerAdapter(state.repositoryResult)
                }
                is RepositoryListViewModel.State.Error -> {
                    progressListRepositories.visibility = View.GONE
                    listRepositories.visibility = View.GONE
                    AlertDialog.Builder(this).setTitle("Error loading repositories")
                        .setMessage("Please check you are connected to the Internet and try again.")
                        .setPositiveButton(android.R.string.ok) { _, _ -> }
                        .setIcon(android.R.drawable.ic_dialog_alert).show()
                }
                is RepositoryListViewModel.State.NotStarted -> {
                    progressListRepositories.visibility = View.GONE
                    listRepositories.visibility = View.GONE
                }
            }
        })
    }
}