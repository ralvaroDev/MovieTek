package pe.ralvaro.movietek.util

import pe.ralvaro.movietek.data.remote.models.NetDates
import pe.ralvaro.movietek.data.remote.models.NetMovie
import pe.ralvaro.movietek.data.remote.models.NetUpcomingContainer

object TestData {

    const val userNameOK = "admin"
    const val passwordOK = "Password*123"
    val correctCredentials = Pair(userNameOK, passwordOK)

    const val userNameMalformed = "admin fe__"

    const val userNameWrong = "admin3f23f"
    const val passwordWrong = "Passwosdf*1234"
    val wrongCredentials = Pair(userNameWrong, passwordWrong)


    val netDates = NetDates("2023-08-15", "2023-08-01")

    val movie1 = NetMovie(
        false,
        "/backdrop_path_1.jpg",
        listOf(28, 12, 14),
        12345,
        "en",
        "Awesome Movie 1",
        "An exciting movie about adventure and discovery.",
        123.45,
        "/poster_path_1.jpg",
        "2023-08-10",
        "Awesome Movie 1",
        false,
        8.7,
        1500
    )

    val movie2 = NetMovie(
        false,
        "/backdrop_path_2.jpg",
        listOf(35, 18),
        67890,
        "es",
        "Película Increíble 2",
        "Una emocionante película sobre amor y superación.",
        98.76,
        "/poster_path_2.jpg",
        "2023-08-20",
        "Película Increíble 2",
        false,
        9.2,
        2500
    )

    val netMovieList = listOf(movie1, movie2)

    val netUpcomingContainer = NetUpcomingContainer(netDates, 1, netMovieList, 1, 2)


}