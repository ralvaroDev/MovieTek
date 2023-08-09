package pe.ralvaro.movietek.ui.login

import android.content.Intent
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pe.ralvaro.movietek.databinding.FragmentLoginBinding
import pe.ralvaro.movietek.ui.BaseFragment
import pe.ralvaro.movietek.ui.MainActivity
import pe.ralvaro.movietek.ui.addFlags
import pe.ralvaro.movietek.utils.launchAndRepeatWithViewLifecycle

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated() {

        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            loginViewModel.setUsername(text.toString())
            binding.tilEmail.error = null
        }

        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            loginViewModel.setPassword(text.toString())
            binding.tilPassword.error = null
        }

        binding.btnLogin.setOnClickListener {
            loginViewModel.login()
        }

        launchAndRepeatWithViewLifecycle {
            loginViewModel.loginStatusChannel.collect {
                when (it) {
                    is LoginStatus.Error -> {
                        binding.tilEmail.error = it.message
                        binding.tilPassword.error = it.message


                    }
                    LoginStatus.Loading -> {
                        binding.tilEmail.error = null
                        binding.tilPassword.error = null

                        // Add progress to btnLogin
                    }
                    LoginStatus.Success -> {
                        startActivity(
                            Intent(requireContext(), MainActivity::class.java).apply {
                                addFlags()
                            }
                        )
                        requireActivity().finish()
                    }
                }
            }
        }

    }

}