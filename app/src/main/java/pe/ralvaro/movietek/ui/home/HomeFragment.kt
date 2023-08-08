package pe.ralvaro.movietek.ui.home

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pe.ralvaro.movietek.databinding.FragmentHomeBinding
import pe.ralvaro.movietek.ui.BaseFragment
import pe.ralvaro.movietek.ui.LoginActivity
import pe.ralvaro.movietek.ui.addFlags

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onViewCreated() {

        binding.btnLogout.setOnClickListener {

            lifecycleScope.launch {
                homeViewModel.logout()
                delay(1000)
                startActivity(
                    Intent(requireContext(), LoginActivity::class.java).apply {
                        this.addFlags()
                    }
                )
                requireActivity().finish()
            }

        }

    }

}