package com.mahmoud.newsapp.adapters

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahmoud.newsapp.R
import com.mahmoud.newsapp.pojo.Article
import com.mahmoud.newsapp.ui.NewsDetailsActivity
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.Level.parse

class NewsAdapter(context: Context) : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.news_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        var article = differ.currentList[position]
        holder.itemView.apply {
            if (article.urlToImage != null) {
                Glide.with(this).load(article.urlToImage).into((findViewById(R.id.img_news)))
            } else {
                (findViewById<ImageView>(R.id.img_news)).setImageResource(R.drawable.default_news_img)
            }
            //  Glide.with(this).load(article.urlToImage).into((findViewById(R.id.img_news)))
            (findViewById<TextView>(R.id.txt_name)).text = article.title
            (findViewById<TextView>(R.id.txt_source_name)).text = article.source.name

            (findViewById<TextView>(R.id.txt_news_date)).text = article.publishedAt

            setOnClickListener {
                val intent = Intent(context, NewsDetailsActivity::class.java)
                intent.putExtra("title", article.title)
                intent.putExtra("description", article.description)
                intent.putExtra("imgUrl", article.urlToImage)
                intent.putExtra("source", article.source.name)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}