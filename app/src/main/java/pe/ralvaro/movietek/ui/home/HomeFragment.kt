package pe.ralvaro.movietek.ui.home

import android.content.Intent
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import pe.ralvaro.movietek.R
import pe.ralvaro.movietek.databinding.FragmentHomeBinding
import pe.ralvaro.movietek.ui.BaseFragment
import pe.ralvaro.movietek.ui.LoginActivity
import pe.ralvaro.movietek.ui.addFlags
import pe.ralvaro.movietek.utils.launchAndRepeatWithViewLifecycle

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onViewCreated() {
        setListenerIntent()

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
            // Receive paging fixed state to scroll to top and fix the paging
            launch {
                homeViewModel.isPagingFixed.first().let {
                    if (!it) {
                        adapter.retry()
                        // adding delay to ensure scrolling
                        delay(1500)
                        binding.rvMovies.scrollToPosition(0)
                        homeViewModel.notifyPagingFixed()
                    }
                }
            }
            // Hear refresh state to handle progression
            launch {
                adapter.loadStateFlow.collect {
                    binding.lySwipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                }
            }
            // Collect user name to set the top bar title
            launch {
                homeViewModel.userName.first().let {
                    setWelcomeMessage(it)
                }
            }
        }

        binding.btnLogout.setOnClickListener {
            homeViewModel.closeSession()
        }

    }

    // In case of close request this fun will run
    private fun setListenerIntent() {
        launchAndRepeatWithViewLifecycle {
            homeViewModel.intentFinishChannel.collect {
                val intent = Intent(requireActivity(), LoginActivity::class.java).addFlags()
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    private fun setWelcomeMessage(username: String) {
        val firstWord = getString(R.string.welcome_msg)
        val secondWord = username.plus("!")
        val spannableString = SpannableString("$firstWord $secondWord")
        spannableString.setSpan(
            AbsoluteSizeSpan(16, true),
            0,
            firstWord.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            AbsoluteSizeSpan(24, true),
            firstWord.length + 1,
            firstWord.length + secondWord.length + 1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvTitleTopBar.text = spannableString
    }

    private fun navigateToDetails(idMovie: Int) {
        val action = HomeFragmentDirections.toDetail(idMovie)
        findNavController().navigate(action)
    }

}