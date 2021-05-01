package com.kairosapp.githubkotlinrepositories.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kairosapp.githubkotlinrepositories.R
import com.kairosapp.githubkotlinrepositories.domain.IssuesByWeek
import org.threeten.bp.format.DateTimeFormatter

class RepositoryIssuesByWeekRecyclerAdapter(
    private val context: Context,
    private val issuesByWeekList: List<IssuesByWeek>
) : RecyclerView.Adapter<RepositoryIssuesByWeekRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_issue_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val issuesByWeek = issuesByWeekList[position]
        holder.textWeek.text = context.getString(R.string.week_number, issuesByWeek.week)
        holder.textDateInterval.text = context.getString(
            R.string.date_interval,
            issuesByWeek.weekStartDate.format(DateTimeFormatter.ofPattern("dd/MM/yy")),
            issuesByWeek.weekEndDate.format(DateTimeFormatter.ofPattern("dd/MM/yy"))
        )
        holder.textNumberOfIssues.text = context.getString(R.string.number_of_issues, issuesByWeek.amount)
    }

    override fun getItemCount(): Int {
        return issuesByWeekList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textWeek: TextView = itemView.findViewById(R.id.textWeek)
        val textDateInterval: TextView = itemView.findViewById(R.id.textDateInterval)
        val textNumberOfIssues: TextView = itemView.findViewById(R.id.textNumberOfIssues)
    }
}