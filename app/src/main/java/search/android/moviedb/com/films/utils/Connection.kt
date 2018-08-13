package search.android.moviedb.com.films.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

object Connection {

    /**
     * function for network connectivity check
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val networkTypes = intArrayOf(ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI)
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            for (networkType in networkTypes) {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.type == networkType)
                    return true
            }
        } catch (e: Exception) {
            Toast.makeText(context, "no network", Toast.LENGTH_SHORT).show()
            return false
        }

        Toast.makeText(context, "no network", Toast.LENGTH_SHORT).show()
        return false
    }
}