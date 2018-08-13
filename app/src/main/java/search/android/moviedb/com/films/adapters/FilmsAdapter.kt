package search.android.moviedb.com.films.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import search.android.moviedb.com.films.R
import search.android.moviedb.com.films.db.models.Film
import search.android.moviedb.com.films.ui.mvp.MainContract
import search.android.moviedb.com.films.utils.API

/***
 * Just an adapter for recycler view
 * Nothing unusual
 */
class FilmsAdapter(private var view: MainContract.View) : RecyclerView.Adapter<FilmsAdapter.FilmHolder>() {

    private var inflater: LayoutInflater = LayoutInflater.from(view.getContext())
    private val films: MutableList<Film>

    init {
        films = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder {
        val view = inflater.inflate(R.layout.film_card, parent, false)
        return FilmHolder(view)
    }

    override fun onBindViewHolder(holder: FilmHolder, position: Int) {

        holder.title.text = films[position].title
        holder.subtitle.text = films[position].lang
        holder.age.text = "${films[position].age}+"


        Glide.with(view.getContext())
                .load(API.IMAGE_PATH + films[position].image)
                .dontAnimate() //for higher performance
                .priority(Priority.IMMEDIATE) //for higher performance
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(GlideDrawableImageViewTarget(holder.image))

        holder.film = films[position]
    }

    override fun getItemCount(): Int {
        return films.size
    }

    fun updateContent(films: List<Film>) {
        this.films.clear()
        this.films.addAll(films)
        notifyDataSetChanged()
    }

    inner class FilmHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        var film: Film? = null
        val title: TextView = v.findViewById(R.id.title)
        val subtitle: TextView = v.findViewById(R.id.subtitle)
        val age: TextView = v.findViewById(R.id.age_limit)
        val image: ImageView = v.findViewById(R.id.image)

        init {
            title.setOnClickListener(this)
            subtitle.setOnClickListener(this)
            age.setOnClickListener(this)
            image.setOnClickListener(this)
        }

        /** In this way overview is loaded*/
        override fun onClick(v: View?) {
            view.openOverview(film!!)
        }

    }
}
