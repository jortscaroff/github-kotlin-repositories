package com.kairosapp.githubkotlinrepositories.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kairosapp.githubkotlinrepositories.R
import com.kairosapp.githubkotlinrepositories.data.RepositoryResult

class RepositoryRecyclerAdapter(private val repositories: RepositoryResult) : RecyclerView.Adapter<RepositoryRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_repository_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = repositories.items[position]
        holder.textUsername.text = repository.owner.login.orEmpty()
        holder.textName.text = repository.name.orEmpty()
        holder.textDescription.text = repository.description.orEmpty()
    }

    override fun getItemCount() = repositories.items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textUsername: TextView = itemView.findViewById(R.id.username)
        val textName: TextView = itemView.findViewById(R.id.repositoryName)
        val textDescription: TextView = itemView.findViewById(R.id.repositoryDescription)
    }
}