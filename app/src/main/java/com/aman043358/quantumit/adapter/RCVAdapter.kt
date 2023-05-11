package com.aman043358.quantumit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aman043358.quantumit.R
import com.aman043358.quantumit.databinding.RowLayoutBinding
import com.aman043358.quantumit.datamodel.News
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class RCVAdapter(var news: News) : RecyclerView.Adapter<RCVAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.tvPublishedAt.text = dateFormat(news.articles[pos].publishedAt)
            binding.tvSourceName.text = news.articles[pos].source.name
            binding.tvTitle.text = news.articles[pos].title
            binding.tvDiscription.text = news.articles[pos].description

            Glide.with(binding.root.context)
                .load(news.articles[pos].urlToImage)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(binding.iv)
        }
    }

    fun setFilterNews(filterNews: News){
        news = filterNews
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pos = position)
    }

    override fun getItemCount(): Int {
        return news.articles.size
    }

    fun dateFormat(inputDate: String): String{
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val outputFormat = SimpleDateFormat("dd MMM, yyyy, hh:mm a")
        val date: Date = inputFormat.parse(inputDate)!!
        return "published at ${outputFormat.format(date)}"
    }
}