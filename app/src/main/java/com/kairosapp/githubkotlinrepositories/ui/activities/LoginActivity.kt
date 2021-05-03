package com.kairosapp.githubkotlinrepositories.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kairosapp.githubkotlinrepositories.GithubUtils
import com.kairosapp.githubkotlinrepositories.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val githubUtils: GithubUtils by lazy {
        GithubUtils()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            startGitHubLogin()
        }
    }

    private fun startGitHubLogin() {
        val authIntent = Intent(Intent.ACTION_VIEW, githubUtils.buildAuthGitHubUrl())
        startActivity(authIntent)
    }
}