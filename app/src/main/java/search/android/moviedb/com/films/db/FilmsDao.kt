package search.android.moviedb.com.films.db

import android.arch.persistence.room.*
import io.reactivex.Single
import search.android.moviedb.com.films.db.models.Film

@Dao
interface FilmsDao {

    @Query("SELECT * FROM films")
    fun getItems(): Single<List<Film>>

    @Query("SELECT * FROM films WHERE id LIKE :id")
    fun findById(id: Int): Single<List<Film>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Film)

    @Delete
    fun delete(entity: Film)
}