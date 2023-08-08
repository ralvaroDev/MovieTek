package pe.ralvaro.movietek.util

object TestData {

    const val userNameOK = "admin"
    const val passwordOK = "Password*123"
    val correctCredentials = Pair(userNameOK, passwordOK)

    const val userNameMalformed = "admin fe__"

    const val userNameWrong = "admin3f23f"
    const val passwordWrong = "Passwosdf*1234"
    val wrongCredentials = Pair(userNameWrong, passwordWrong)

}