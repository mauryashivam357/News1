package com.aman043358.quantumit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.aman043358.quantumit.adapter.RCVAdapter
import com.aman043358.quantumit.api.Api
import com.aman043358.quantumit.databinding.ActivityHomeBinding
import com.aman043358.quantumit.datamodel.Articles
import com.aman043358.quantumit.datamodel.News
import com.aman043358.quantumit.utils.AuthUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity() {

    val newsAPI = Api.createNewsAPI
    val articlesLst = ArrayList<Articles>()
    lateinit var adapter: RCVAdapter

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rcv.layoutManager = LinearLayoutManager(this)


        CoroutineScope(Dispatchers.IO).launch {
            val response = newsAPI.get()

            withContext(Dispatchers.Main){
                if (response.isSuccessful) {
                    response.body()?.let { news ->


                        binding.progressIndicator.visibility = View.INVISIBLE
                        binding.rcv.visibility = View.VISIBLE
                        binding.cv.visibility = View.VISIBLE
                        adapter = RCVAdapter(news)
                        binding.rcv.adapter = adapter

                        for (i in news.articles.indices) {
                            articlesLst.add(news.articles[i])
                        }
                    }

                } else {
                    binding.progressIndicator.visibility = View.INVISIBLE
                    Toast.makeText(this@HomeActivity, "fail", Toast.LENGTH_SHORT).show()
                }
            }
        }


        binding.sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filterArticles = ArrayList<Articles>()
                if (newText != null) {
                    for (i in articlesLst.indices) {
                        if (articlesLst[i].title.lowercase(Locale.ROOT).contains(newText))
                            filterArticles.add(articlesLst[i])
                    }
                }

                if (filterArticles.isEmpty()) {
                    Toast.makeText(baseContext, "no news found", Toast.LENGTH_SHORT).show()
                } else {
                    adapter.setFilterNews(News(filterArticles))
                }

                return true
            }
        })

        binding.btnLogout.setOnClickListener {
            AuthUtils.logout()
            startActivity(Intent(this@HomeActivity, MainActivity::class.java))
            finish()
        }
    }
}