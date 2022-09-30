package br.com.carlosscotus.npbrasil.presentation.detail

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import br.com.carlosscotus.npbrasil.BuildConfig
import br.com.carlosscotus.npbrasil.databinding.FragmentGameDetailBinding
import br.com.carlosscotus.npbrasil.framework.imageloader.ImageLoader
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GameDetailFragment : Fragment() {

    private var _binding: FragmentGameDetailBinding? = null
    private val binding: FragmentGameDetailBinding
        get() = _binding!!

    @Inject
    lateinit var imageLoader: ImageLoader

    private val gameDetailViewModel: GameDetailViewModel by viewModels()

    private val gameDetailFragmentArgs: GameDetailFragmentArgs by navArgs()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentGameDetailBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameDetailViewModel.loadGameDetail(gameDetailFragmentArgs.gameDetailArgs.productId)

        binding.fabAddFavorite.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        setupBottomSheet()
        observerFavoriteUIState()
        observerDetailUIState()
    }

    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.layoutMoreOptions.root)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        binding.layoutMoreOptions.close.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private fun observerFavoriteUIState() {
//        gameDetailViewModel.favoriteUIState.observe(viewLifecycleOwner) { state ->
//            when(state) {
//                GameDetailViewModel.FavoriteUIState.Loading -> {}
//                is GameDetailViewModel.FavoriteUIState.FavoriteIcon -> {
//                    binding.fabAddFavorite.setImageResource(R.drawable.ic_round_favorite_24)
//                }
//            }
//        }
    }

    private fun observerDetailUIState() {
        gameDetailViewModel.detailUIState.observe(viewLifecycleOwner) { state ->
            binding.viewFlipper.displayedChild = when(state) {
                GameDetailViewModel.DetailUIState.Loading -> {
                    binding.fragmentGameDetailShimmer.root.startShimmer()
                    UI_STATE_LOADING
                }
                is GameDetailViewModel.DetailUIState.Success -> {
                    binding.gameDescription.text = Html.fromHtml(
                        state.data.description,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )

                    binding.fabAddFavorite.isVisible = true

                    imageLoader.load(
                        binding.imageGame,
                        "${
                            BuildConfig.IMAGE_LOADER_URL.replace(
                                "c_scale,w_300",
                                "c_scale,w_600"
                            )
                        }${state.data.imageUrl}"
                    )

                    binding.fragmentGameDetailShimmer.root.stopShimmer()
                    UI_STATE_SUCCESS
                }
                is GameDetailViewModel.DetailUIState.Error -> {
                    binding.fragmentGameDetailShimmer.root.stopShimmer()
                    UI_STATE_ERROR
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val UI_STATE_LOADING = 0
        const val UI_STATE_SUCCESS = 1
        const val UI_STATE_ERROR = 2
    }
}