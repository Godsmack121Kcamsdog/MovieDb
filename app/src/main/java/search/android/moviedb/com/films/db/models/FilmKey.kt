package search.android.moviedb.com.films.db.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Stands for entered keyword
 */
@Entity(tableName = "keys")
class FilmKey(@PrimaryKey val id: Int, val name: String)