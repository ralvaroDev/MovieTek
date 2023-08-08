package pe.ralvaro.movietek.utils

import org.junit.Test

/**
 * Test for [UsernameValidator]
 */
class UsernameValidatorTest {

    @Test
    fun usernameValidator_correctUsername_returnTrue() {
        val result = UsernameValidator.isValid("ralvaro")
        assert(result)
    }

    @Test
    fun usernameValidator_usernameWithSpaces_returnFalse() {
        val result = UsernameValidator.isValid("ralvaro alvaro")
        assert(!result)
    }

    @Test
    fun usernameValidator_usernameWithSpecialCharacters_returnFalse() {
        val result = UsernameValidator.isValid("ralvaro@")
        assert(!result)
    }

    @Test
    fun usernameValidator_usernameWithLessThanFourCharacters_returnFalse() {
        val result = UsernameValidator.isValid("ral")
        assert(!result)
    }

}