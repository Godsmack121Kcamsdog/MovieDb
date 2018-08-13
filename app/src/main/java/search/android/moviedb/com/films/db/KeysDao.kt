package search.android.moviedb.com.films.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

import io.reactivex.Single
import search.android.moviedb.com.films.db.models.Film
import search.android.moviedb.com.films.db.models.FilmKey

@Dao
interface KeysDao {

    @Query("SELECT * FROM keys")
    fun getAll(): Single<List<FilmKey>>

    @Query("SELECT * FROM keys WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Single<FilmKey>

    @Insert
    fun insertAll(vararg entities: FilmKey)

    @Insert(onConflict = REPLACE)
    fun insert(entity: FilmKey)

    @Delete
    fun delete(entity: FilmKey)
}
