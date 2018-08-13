package search.android.moviedb.com.films.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import search.android.moviedb.com.films.R
import search.android.moviedb.com.films.api.MovieDbApi
import search.android.moviedb.com.films.db.models.Film
import search.android.moviedb.com.films.utils.API
import search.android.moviedb.com.films.utils.Connection


class OverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.overview_activity)

        val tabletSize = resources.getBoolean(R.bool.isTablet)
        if (tabletSize) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        val i = intent
        val bundle = i.extras
        val film = bundle!!.getSerializable("film") as Film

        val title: TextView = findViewById(R.id.title_film)
        val overview: TextView = findViewById(R.id.overview_film)
        val image: ImageView = findViewById(R.id.image_film)
        val web: WebView = findViewById(R.id.web_view)

        title.text = film.title
        overview.text = film.overview
        Glide.with(this).load(API.IMAGE_PATH + film.image).into(image)

        web.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                return false
            }
        }
        web.settings.javaScriptEnabled = true

        if (Connection.isNetworkAvailable(this)) {
            MovieDbApi.instance.trailer(film.filmId, film.lang)
                    .doOnSuccess { it ->
                        if (!it.isEmpty()) {
                            val data = API.videoInsert(it[0].key)
                            web.loadData(data, "text/html", "utf-8")
                        }
                        else web.visibility = View.GONE
                    }
                    .subscribe()
        }
    }
}