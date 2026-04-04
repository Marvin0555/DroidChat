package com.example.droidchat.ui.feature.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidchat.R
import com.example.droidchat.data.repository.AuthRepository
import com.example.droidchat.model.NetworkException
import com.example.droidchat.ui.validator.FormValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Int

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val formValidator: FormValidator<SignInFormState>,
    private val authRepository: AuthRepository,
) : ViewModel() {

    var formState by mutableStateOf(SignInFormState())
        private set

    fun onFormEvent(event: SignInFormEvent){
        when(event){
            is SignInFormEvent.EmailChanged -> {
                formState = formState.copy(
                    email = event.email, emailError = null
                )
            }
            is SignInFormEvent.PasswordChanged -> {
                formState = formState.copy(
                    password = event.password, passwordError = null
                )
            }
            is SignInFormEvent.Submit -> {
                doSignIn()
            }
        }
    }

    private fun doSignIn() {
//        var isFormValid = true
//        //resetFormErrorState()
//        if(formState.email.isBlank()){
//            formState = formState.copy(emailError = R.string.error_message_email_invalid)
//            isFormValid = false
//        }
//
//        if(formState.password.isBlank()){
//            formState = formState.copy(passwordError = R.string.error_message_password_invalid)
//            isFormValid = false
//        }

        if(isValidForm()) {
            formState = formState.copy(isLoading = true)
            viewModelScope.launch {
                authRepository.signIn(
                    username = formState.email,
                    password = formState.password

                ).fold(
                    onSuccess = {
                        formState = formState.copy(
                            isLoading = false,
                            isLoginSuccess = true
                        )
                    },
                    onFailure = {
                        formState = formState.copy(
                            isLoading = false,
                            apiErrorMessageResId = if (it is NetworkException.ApiException) {
                                when(it.statusCode) {
                                    401 -> R.string.error_message_api_form_validation_failed
                                    else -> R.string.common_generic_error_message
                                }
                            } else  R.string.common_generic_error_message
                        )
                    }
                )
            }
        }
    }

    private fun isValidForm(): Boolean {
            return !formValidator.validate(formState = formState).also {
                formState = it
            }.hasError
    }

    private fun resetFormErrorState() {
        formState = formState.copy(
            emailError = null,
            passwordError = null,
        )
    }

    fun errorMessageShown(){
        formState = formState.copy(apiErrorMessageResId = null)
    }

    fun signInSuccessShow() {
        formState = formState.copy(isLoginSuccess = false)
    }

}