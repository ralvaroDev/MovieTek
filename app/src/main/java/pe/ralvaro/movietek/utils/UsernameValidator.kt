package pe.ralvaro.movietek.utils

object UsernameValidator {

    fun isValid(userName: String): Boolean {
        if (userName.trim().find { it == ' ' } != null) return false
        if (userName.replace(" ", "").length < 4) return false
        val regex = "[A-Za-z0-9]+".toRegex()
        return regex.matches(userName.replace(" ", ""))
    }

}