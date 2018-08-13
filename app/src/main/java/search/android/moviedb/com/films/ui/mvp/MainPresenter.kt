package search.android.moviedb.com.films.ui.mvp

import android.widget.Toast
import info.movito.themoviedbapi.model.MovieDb
import io.reactivex.observers.DisposableSingleObserver
import search.android.moviedb.com.films.adapters.MovieDBAdapter
import search.android.moviedb.com.films.api.MovieDbApi
import search.android.moviedb.com.films.db.models.Film
import search.android.moviedb.com.films.db.models.FilmKey
import search.android.moviedb.com.films.manager.SearchCacheManager
import search.android.moviedb.com.films.utils.Connection

class MainPresenter(val view: MainContract.View) : MainContract.EventListener {

    /**
     * search films at database at first
     * If it does not exist --> throws an error,
     * then search online and write results to database in case of success
     */
    override fun search(name: String, age: Int, language: String, includeAdult: Boolean, page: Int) {
        if (name.isEmpty()) return
        view.disableButton()
        SearchCacheManager.instance
                .findByName(name)
                .subscribe(object : DisposableSingleObserver<FilmKey>() {

                    override fun onSuccess(t: FilmKey) {
                        SearchCacheManager.instance.findFilms(t.id)
                                .doOnError(::onFailure)
                                .doOnSuccess {
                                    onSuccess(it)
                                    view.enableButton()
                                }.subscribe()
                    }

                    override fun onError(e: Throwable) {
                        if (Connection.isNetworkAvailable(view.getContext())) {
                            MovieDbApi.instance.search(name, age, language, includeAdult, page)
                                    .doOnError(::onFailure)
                                    .doOnSuccess {
                                        onSuccess(save(it, name, age))
                                        view.enableButton()
                                    }
                                    .subscribe()
                        }
                    }
                })
    }


    /**
     * for attaching films to one keyword keyword`s id is attached to every film
     */
    override fun save(films: List<MovieDb>, name: String, age: Int): List<Film> {
        val word = FilmKey(films[0].id, name)
        val dbFilms = ArrayList<Film>()

        films.forEach { movie ->
            val film = MovieDBAdapter.castToFilm(movie, age, word.id)
            dbFilms.add(film)
        }

        SearchCacheManager.instance.insert(word)
        SearchCacheManager.instance.insertFilms(dbFilms)
        return dbFilms
    }

    override fun onSuccess(o: List<Film>) {
        view.setFilms(o)
    }

    override fun onFailure(err: Throwable) {
        Toast.makeText(view.getContext(), "error", Toast.LENGTH_SHORT).show()
    }
}