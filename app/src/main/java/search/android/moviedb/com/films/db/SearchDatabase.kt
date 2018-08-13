package search.android.moviedb.com.films.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import search.android.moviedb.com.films.db.models.Film
import search.android.moviedb.com.films.db.models.FilmKey
import search.android.moviedb.com.films.db.models.FilmTrailer

@Database(entities = [FilmKey::class, Film::class, FilmTrailer::class], version = 1, exportSchema = false)
abstract class SearchDatabase : RoomDatabase() {
    abstract fun keysDao(): KeysDao
    abstract fun filmsDao(): FilmsDao
    abstract fun trailersDao(): TrailersDao
}