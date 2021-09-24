package com.mahmoud.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahmoud.newsapp.R

class NewsDetailsActivity : AppCompatActivity() {

    lateinit var icon_up: ImageView
    lateinit var icon_back: ImageView
    lateinit var img_news: ImageView
    lateinit var txt_source_name: TextView
    lateinit var txt_news_name: TextView
    lateinit var txt_news_des: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        icon_up = findViewById(R.id.icon_up)
        icon_back = findViewById(R.id.icon_back)
        img_news = findViewById(R.id.img_news)
        txt_source_name = findViewById(R.id.txt_source_name)
        txt_news_name = findViewById(R.id.txt_news_name)
        txt_news_des = findViewById(R.id.txt_news_des)

        icon_back.setOnClickListener(View.OnClickListener {
            finish()
        })

        val intent = getIntent()

        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val imgUrl = intent.getStringExtra("imgUrl")
        val source = intent.getStringExtra("source")

        txt_news_name.text = title
        txt_news_des.text = description
        txt_source_name.text = source
        if (imgUrl != null) {
            Glide.with(this).load(imgUrl).into(img_news)
        } else {
            img_news.setImageResource(R.drawable.default_news_img)
        }


    }
}