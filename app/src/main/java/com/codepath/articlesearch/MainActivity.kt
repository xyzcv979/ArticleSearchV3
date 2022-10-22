package com.codepath.articlesearch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.articlesearch.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Call helper method to swap the FrameLayout with the fragment
        replaceFragment(ArticleListFragment())

        // Swapping fragments when clicking button nav view
        val fragmentManager: FragmentManager = supportFragmentManager

        // define your fragments here
        val articleFragment: Fragment = ArticleListFragment()
        val bookFragment: Fragment = BestSellerBooksFragment()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // handle navigation selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.article_search -> fragment = articleFragment
                R.id.best_selling_books -> fragment = bookFragment
            }
            fragmentManager.beginTransaction().replace(R.id.article_frame_layout, fragment).commit()
            true
        }

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.best_selling_books

    }

    private fun replaceFragment(articleListFragment: ArticleListFragment) {
        val fragmentManager = supportFragmentManager
        // Begin the transaction
        val fragmentTransaction = fragmentManager.beginTransaction()
        // Replace the contents of the container with the new fragment
        fragmentTransaction.replace(R.id.article_frame_layout, articleListFragment)
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        fragmentTransaction.commit()
    }

}