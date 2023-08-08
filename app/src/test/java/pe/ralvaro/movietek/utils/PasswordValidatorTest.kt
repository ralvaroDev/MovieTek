package pe.ralvaro.movietek.utils

import org.junit.Test

/**
 * Test for [PasswordValidator]
 */
class PasswordValidatorTest {

    @Test
    fun passwordValidator_correctPassword_returnTrue() {
        val result = PasswordValidator.isValid("ralvaro")
        assert(result)
    }

    @Test
    fun passwordValidator_passwordWithSpaces_returnFalse() {
        val result = PasswordValidator.isValid("ralvaro alvaro")
        assert(!result)
    }

    @Test
    fun passwordValidator_passwordWithLessThanFourCharacters_returnFalse() {
        val result = PasswordValidator.isValid("ral")
        assert(!result)
    }

    @Test
    fun passwordValidator_passwordWithSpecialCharacters_returnTrue() {
        val result = PasswordValidator.isValid("ralvaro@")
        assert(result)
    }

}