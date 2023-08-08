package pe.ralvaro.movietek.utils

object PasswordValidator {

    fun isValid(password: String): Boolean {
        if (password.trim().find { it == ' ' } != null) return false
        if (password.replace(" ", "").length < 4) return false
        return true
    }

}