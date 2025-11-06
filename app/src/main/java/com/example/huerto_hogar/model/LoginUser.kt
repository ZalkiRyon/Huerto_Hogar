package com.example.huerto_hogar.model
enum class LoginResult {
    SUCCESS,
    INVALID_CREDENTIALS,
    ERROR
}

data class LoginUser(
    val email: String = "",
    val password: String = "",

    // states for the interface  UI
    val isLoading: Boolean = false,
    val errors: LoginUserErrors = LoginUserErrors(),

    // Result for message confirmation or navigate
    val loginResultEvent: LoginResult? = null,

    // user load if its successful
    val loggedInUser: User? = null
)

data class LoginUserErrors(
    val emailError: String? = null,
    val passwordError: String? = null
)