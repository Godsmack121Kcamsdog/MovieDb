package search.android.moviedb.com.films.db

import android.arch.persistence.room.*
import io.reactivex.Maybe
import io.reactivex.Single
import search.android.moviedb.com.films.db.models.FilmTrailer

@Dao
interface TrailersDao {
    @Query("SELECT * FROM trailers")
    fun getItems(): Single<List<FilmTrailer>>

    @Query("SELECT * FROM trailers WHERE id LIKE :id")
    fun findById(id: Int): Single<List<FilmTrailer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: FilmTrailer)

    @Delete
    fun delete(entity: FilmTrailer)
}