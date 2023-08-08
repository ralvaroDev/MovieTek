package pe.ralvaro.movietek.ui.home

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pe.ralvaro.movietek.databinding.FragmentHomeBinding
import pe.ralvaro.movietek.ui.BaseFragment

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onViewCreated() {


    }

}