package pe.ralvaro.movietek.ui.home

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pe.ralvaro.movietek.databinding.FragmentHomeBinding
import pe.ralvaro.movietek.ui.BaseFragment
import pe.ralvaro.movietek.ui.LoginActivity
import pe.ralvaro.movietek.ui.addFlags
import pe.ralvaro.movietek.utils.launchAndRepeatWithViewLifecycle
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onViewCreated() {

        val adapter = MovieAdapterPager {
            navigateToDetails(it)
        }

        binding.rvMovies.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter { adapter.retry() },
            footer = MovieLoadStateAdapter { adapter.retry() }
        )

        binding.lySwipeRefresh.setOnRefreshListener {
            adapter.refresh()
        }

        launchAndRepeatWithViewLifecycle {
            launch {
                homeViewModel.moviePagingFlow.collect {
                    adapter.submitData(it)
                }
            }
            launch {
                adapter.loadStateFlow.collect {
                    binding.lySwipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                }
            }
        }


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

    private fun navigateToDetails(idMovie: Int) {
        val action = HomeFragmentDirections.toDetail(idMovie)
        findNavController().navigate(action)
    }

}