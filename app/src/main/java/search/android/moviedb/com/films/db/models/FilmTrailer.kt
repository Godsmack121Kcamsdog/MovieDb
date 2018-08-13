package search.android.moviedb.com.films.db.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "trailers")
class FilmTrailer(val key: String, val name: String,
                  @ForeignKey(entity = Film::class, parentColumns = ["id"], childColumns = ["itemId"])
                  val id: Int) {

    @PrimaryKey(autoGenerate = true)
    var itemId: Int? = null

    @ColumnInfo(name = "trailerId")
    var trailerId: String? = null

    @ColumnInfo(name = "iso639")
    var iso639: String? = null

    @ColumnInfo(name = "site")
    var site: String? = null

    @ColumnInfo(name = "size")
    var size: Int? = null

    @ColumnInfo(name = "type")
    var type: String? = null

}