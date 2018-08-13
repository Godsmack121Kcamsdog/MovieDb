package search.android.moviedb.com.films.utils

object API {

    const val API_KEY = "API KEY WAS REMOVED"
    const val IMAGE_PATH = "http://image.tmdb.org/t/p/w185//"

    private const val YOUTUBE = "https://www.youtube.com/embed/"

    private const val style = "<style>\n" +
            "    body{\n" +
            "    background-color:black\n" +
            "    }\n" +
            "</style>"
    /**
     * html insertion for good trailer`s fitting into WebView window
     */
    fun videoInsert(youTubeId: String): String {
        return "<html>" + style +
                "<body>" +
                "<iframe style=\"position:absolute; " +
                "top:0; left:0\" width=\"100%\" height=\"100%\" " +
                "src=\"" + YOUTUBE + youTubeId +
                "\" frameborder=\"0\" allowfullscreen></iframe>" +
                "</body>" +
                "</html>"
    }
}