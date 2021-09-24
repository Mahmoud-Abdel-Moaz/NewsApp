package com.mahmoud.newsapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahmoud.newsapp.R
import com.mahmoud.newsapp.adapters.NewsAdapter
import com.mahmoud.newsapp.pojo.Article
import com.mahmoud.newsapp.repository.NewsRepository
import com.mahmoud.newsapp.util.Resource

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var rvBreakingNews: RecyclerView
    lateinit var edit_search: EditText
    lateinit var articles: List<Article>
    lateinit var searchResultArticles: List<Article>
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchResultArticles = ArrayList()
        articles = ArrayList()
        rvBreakingNews = findViewById(R.id.recyclerview)
        edit_search = findViewById(R.id.edit_search)
        var repository = NewsRepository()
        var viewModelProviderFactory = NewsViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
        setupRecyclerView()

        viewModel.breakingNews.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { newsResponse ->
                        articles = newsResponse.articles
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(TAG, "An Error Happened: $message")
                    }
                }

            }
        })
        edit_search.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                searchForArticles(s.toString())
            }
        })

    }

    private fun searchForArticles(word: String) {
        searchResultArticles = ArrayList()
        if (!word.isEmpty()) {
            articles.forEach { article ->
                if (article.title.contains(word)) {
                    searchResultArticles = searchResultArticles + article
                }
            }
        } else {
            searchResultArticles = articles
        }

        newsAdapter.differ.submitList(searchResultArticles)
    }


    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(this@MainActivity)
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}