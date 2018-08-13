package search.android.moviedb.com.films.adapters

import info.movito.themoviedbapi.model.MovieDb
import info.movito.themoviedbapi.model.Video
import search.android.moviedb.com.films.db.models.Film
import search.android.moviedb.com.films.db.models.FilmTrailer

/**
 * Simple adapting server models to custom
 */
object MovieDBAdapter {

    fun castToFilm(movie: MovieDb, age: Int, id: Int): Film {

        val film: Film = when {
            movie.backdropPath != null //if movie backdropImagePath exist
            -> Film(movie.title, age, movie.overview, movie.backdropPath, movie.originalLanguage, movie.id, id)

            movie.posterPath != null //if movie posterImagePath exist
            -> Film(movie.title, age, movie.overview, movie.posterPath, movie.originalLanguage, movie.id, id)

            else -> Film(movie.title, age, movie.overview, "", movie.originalLanguage, movie.id, id)
        }
        return film
    }

    fun castToTrailer(video: Video, id: Int): FilmTrailer {
        val trailer = FilmTrailer(video.key, video.name, id)
        trailer.iso639 = video.iso639
        trailer.type = video.type
        trailer.site = video.site
        trailer.size = video.size
        trailer.trailerId = video.id
        return trailer
    }
}