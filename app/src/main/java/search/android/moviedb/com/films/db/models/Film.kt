package search.android.moviedb.com.films.db.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Stands for Films loaded by keyword and attached to FilmKey column
 */
@Entity(tableName = "films")
class Film(val title: String,
           val age: Int,
           val overview: String,
           val image: String,
           val lang: String,
           val filmId: Int,
           @ForeignKey(entity = FilmKey::class, parentColumns = ["id"], childColumns = ["itemId"])
           val id: Int) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var itemId: Int? = null

    override fun toString(): String {
        return "Film (title='$title', age=$age, overview='$overview', image='$image', lang='$lang', itemId=$itemId)"
    }


}
