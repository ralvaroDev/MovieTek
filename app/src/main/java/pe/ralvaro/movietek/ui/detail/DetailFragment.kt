package pe.ralvaro.movietek.ui.detail

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import pe.ralvaro.movietek.R
import pe.ralvaro.movietek.data.models.Movie
import pe.ralvaro.movietek.databinding.FragmentDetailBinding
import pe.ralvaro.movietek.ui.BaseFragment
import pe.ralvaro.movietek.utils.Result
import pe.ralvaro.movietek.utils.launchAndRepeatWithViewLifecycle

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val detailViewModel : DetailViewModel by viewModels()

    override fun onViewCreated() {

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        launchAndRepeatWithViewLifecycle {
            detailViewModel.movieDetails.collect {
                when(it) {
                    is Result.Error -> {
                        Toast.makeText(
                            requireContext(),
                            R.string.error_details_toast,
                            Toast.LENGTH_LONG
                        ).show()
                        findNavController().popBackStack()
                    }
                    Result.Loading -> {}
                    is Result.Success -> {
                        drawSuccessScreen(it.data)
                    }
                }
            }
        }

    }

    private fun drawSuccessScreen(data: Movie) {
        binding.tvTitleMovieTopBar.apply {
            text = data.title
            isSelected = true
        }
        binding.ivPoster.load(data.posterPath) {
            crossfade(true)
            error(R.drawable.img_not_found)
            fallback(R.drawable.img_not_found)
        }
        binding.tvOverview.text = data.overview
        binding.tvReleaseDate.text = data.releaseDate
        val newCoverageScale = ((data.voteAverage / 10.0) * 5.0).toFloat()
        binding.ratingBar.rating = newCoverageScale
    }

}