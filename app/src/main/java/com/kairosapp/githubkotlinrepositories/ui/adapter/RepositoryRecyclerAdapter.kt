package com.kairosapp.githubkotlinrepositories.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kairosapp.githubkotlinrepositories.*
import com.kairosapp.githubkotlinrepositories.domain.Repository
import com.kairosapp.githubkotlinrepositories.ui.activities.RepositoryDetailsActivity

class RepositoryRecyclerAdapter(private val context: Context, private val repositories: List<Repository>) : RecyclerView.Adapter<RepositoryRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_repository_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = repositories[position]
        holder.textUsername.text = repository.owner.login.orEmpty()
        holder.textName.text = repository.name.orEmpty()
        holder.textDescription.text = repository.description.orEmpty()
        holder.repositoryPosition = position
    }

    override fun getItemCount() = repositories.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textUsername: TextView = itemView.findViewById(R.id.textOwner)
        val textName: TextView = itemView.findViewById(R.id.textName)
        val textDescription: TextView = itemView.findViewById(R.id.textDescription)
        var repositoryPosition = 0

        init {
            itemView.setOnClickListener {
                val intent = Intent(context, RepositoryDetailsActivity::class.java, )
                intent.putExtra(REPOSITORY_NAME, repositories[repositoryPosition].name)
                intent.putExtra(REPOSITORY_OWNER, repositories[repositoryPosition].owner.login)
                intent.putExtra(REPOSITORY_DESCRIPTION, repositories[repositoryPosition].description)
                intent.putExtra(REPOSITORY_WATCHERS_COUNT, repositories[repositoryPosition].watchersCount)
                intent.putExtra(REPOSITORY_STARS_COUNT, repositories[repositoryPosition].stargazersCount)
                intent.putExtra(REPOSITORY_FORKS_COUNT, repositories[repositoryPosition].forksCount)
                context.startActivity(intent)
            }
        }

    }
}