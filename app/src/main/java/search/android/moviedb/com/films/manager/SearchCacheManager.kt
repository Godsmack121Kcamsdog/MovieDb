package search.android.moviedb.com.films.manager

import android.arch.persistence.room.Room
import android.content.Context
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import search.android.moviedb.com.films.App
import search.android.moviedb.com.films.db.SearchDatabase
import search.android.moviedb.com.films.db.models.Film
import search.android.moviedb.com.films.db.models.FilmKey
import search.android.moviedb.com.films.db.models.FilmTrailer

/**
 * Manager for working with database
 */
class SearchCacheManager private constructor(context: Context) {

    private val db: SearchDatabase

    init {
        db = Room.databaseBuilder(context, SearchDatabase::class.java, DB_NAME).build()
    }

    companion object {
        private const val DB_NAME = "search.search.android.moviedb.com.films.db"
        private var _instance: SearchCacheManager? = null

        val instance: SearchCacheManager
            get() {
                if (_instance == null) {
                    _instance = SearchCacheManager(App.instance!!.applicationContext)
                }
                return _instance as SearchCacheManager
            }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
//                                     SEARCH PART                                                //
////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * is used for getting keyword of films to load
     */
    fun findByName(name: String): Single<FilmKey> {
        return db.keysDao().findByName(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * @return Single class with list of Films attached to keyword, founded by id
     */
    fun findFilms(id: Int): Single<List<Film>> {
        return db.filmsDao().findById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * @return Single class with list of Videos attached to film, founded by id
     */
    fun findTrailers(filmId: Int): Single<List<FilmTrailer>> {
        return db.trailersDao().findById(filmId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }


////////////////////////////////////////////////////////////////////////////////////////////////////
//                                     INSERT PART                                                //
////////////////////////////////////////////////////////////////////////////////////////////////////

    fun insert(entity: FilmKey) {
        Completable.fromAction {
            db.keysDao().insert(entity)
            println(entity.name + " was saved to search.android.moviedb.com.films.db")
        }.subscribeOn(Schedulers.io())
                .doOnError(::onError)
                .subscribe()
    }

    fun insert(item: Film) {
        Completable.fromAction { db.filmsDao().insert(item) }.subscribe()
    }

    fun insert(item: FilmTrailer) {
        Completable.fromAction { db.trailersDao().insert(item) }.subscribe()
    }


    fun insertFilms(items: List<Film>) {
        Completable.fromAction {
            for (i in items) {
                db.filmsDao().insert(i)
            }
        }.subscribeOn(Schedulers.io())
                .doOnError(::onError)
                .subscribe()
    }

    fun insertTrailers(items: List<FilmTrailer>) {
        Completable.fromAction {
            for (i in items) {
                db.trailersDao().insert(i)
            }
        }.subscribeOn(Schedulers.io())
                .doOnError(::onError)
                .subscribe()
    }

    fun insertAll(vararg entities: FilmKey) {

    }

    private fun onError(e: Throwable) {
        System.err.println(e.message + "\n" + e.cause.toString())
    }

}
