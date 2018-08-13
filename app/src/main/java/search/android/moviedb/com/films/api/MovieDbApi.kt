package search.android.moviedb.com.films.api

import info.movito.themoviedbapi.TmdbApi
import info.movito.themoviedbapi.model.MovieDb
import info.movito.themoviedbapi.model.Video
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import search.android.moviedb.com.films.utils.API

//Of course I can implement it by myself with Retrofit,
//but there was an android API link at https://www.themoviedb.org/
//so...why not?
class MovieDbApi {

    companion object {
        private var _instance: MovieDbApi? = null

        val instance: MovieDbApi
            get() {
                if (_instance == null) {
                    _instance = MovieDbApi()
                }
                return _instance as MovieDbApi
            }
    }

    /**
     * TmdbApi().search stands for http://api.themoviedb.org/3/search/
     * @return Single<> class with List of movies from server response
     */
    fun search(name: String, age: Int, language: String, includeAdult: Boolean, page: Int): Single<List<MovieDb>> {
        return Single.create<List<MovieDb>> { emitter ->
            emitter.onSuccess(TmdbApi(API.API_KEY).search.searchMovie(name, age, language, includeAdult, page).results)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * TmdbApi().movies stands for http://api.themoviedb.org/3/movie/
     * @return Single<> class with List of Videos from movie with id
     * @param id from server response
     */
    fun trailer(id: Int, language: String): Single<List<Video>> {
        return Single.create<List<Video>> { emitter ->
            emitter.onSuccess(TmdbApi(API.API_KEY).movies.getVideos(id, language))
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
