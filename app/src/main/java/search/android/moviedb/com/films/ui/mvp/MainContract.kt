package search.android.moviedb.com.films.ui.mvp

import android.content.Context
import info.movito.themoviedbapi.model.MovieDb
import search.android.moviedb.com.films.db.models.Film

interface MainContract {

    interface View {
        fun getContext(): Context
        fun setFilms(list: List<Film>)
        fun openOverview(f: Film)
        fun enableButton()
        fun disableButton()
    }

    interface EventListener {
        fun search(name: String, age: Int, language: String, includeAdult: Boolean, page: Int)
        fun save(films: List<MovieDb>, name: String, age: Int): List<Film>
        fun onSuccess(o: List<Film>)
        fun onFailure(err: Throwable)
    }
}