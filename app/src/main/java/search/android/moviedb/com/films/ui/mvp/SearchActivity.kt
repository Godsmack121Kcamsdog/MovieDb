package search.android.moviedb.com.films.ui.mvp

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import search.android.moviedb.com.films.R
import search.android.moviedb.com.films.adapters.FilmsAdapter
import search.android.moviedb.com.films.db.models.Film
import search.android.moviedb.com.films.ui.OverviewActivity
import search.android.moviedb.com.films.utils.Language
import search.android.moviedb.com.films.utils.SpaceItemDecoration


class SearchActivity : AppCompatActivity(), MainContract.View {

    private lateinit var button: Button
    private lateinit var spinner: ProgressBar
    private lateinit var adapter: FilmsAdapter
    private var presenter: MainPresenter? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                Toast.makeText(this, "home", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                Toast.makeText(this, "dashboard", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                Toast.makeText(this, "news", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tabletSize = resources.getBoolean(R.bool.isTablet)
        if (tabletSize) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val search: SearchView = findViewById(R.id.search)
        val recycler: RecyclerView = findViewById(R.id.recycler)

        presenter = MainPresenter(this)
        button = findViewById(R.id.btn)
        spinner = findViewById(R.id.progressBar)
        adapter = FilmsAdapter(this)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        recycler.addItemDecoration(SpaceItemDecoration(3))

        button.setOnClickListener {
            if (search.query.toString() != "") {
                spinner.visibility = View.VISIBLE
                presenter!!.search(search.query.toString(), 18, Language.EN, true, 0)
            }
        }
    }

    //this one should look another way, but I`m bored to implement it, hope you understand ^_^
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        button.performClick()
    }

    override fun getContext(): Context {
        return this
    }

    /**
     * This two methods(disable/enable) is required because of case when you
     * click "Find" button too many times in a second
     * As a result - data is loaded into database many times
     */
    override fun disableButton() {
        button.isClickable = false
    }

    override fun enableButton() {
        button.isClickable = true
        spinner.visibility = View.GONE
    }

    override fun setFilms(list: List<Film>) {
        adapter.updateContent(list)
    }

    /**
     * Called when FilmsAdapter`s item is clicked for getting more info
     */
    override fun openOverview(f: Film) {
        val yourIntent = Intent(this, OverviewActivity::class.java)
        val b = Bundle()
        b.putSerializable("film", f)
        yourIntent.putExtras(b)
        startActivity(yourIntent)
    }
}
