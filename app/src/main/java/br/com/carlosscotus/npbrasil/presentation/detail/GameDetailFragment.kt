package br.com.carlosscotus.npbrasil.presentation.detail

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import br.com.carlosscotus.npbrasil.BuildConfig
import br.com.carlosscotus.npbrasil.databinding.FragmentGameDetailBinding
import br.com.carlosscotus.npbrasil.framework.imageloader.ImageLoader
import br.com.carlosscotus.npbrasil.presentation.MainActivity
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            setPathMotion(MaterialArcMotion())
            scrimColor = Color.TRANSPARENT
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentGameDetailBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageGame.transitionName = gameDetailFragmentArgs.gameDetailArgs.id

        postponeEnterTransition()
        setLoadObserverDetailUIState()
    }

    private fun setLoadObserverDetailUIState() {
        gameDetailViewModel.gameDetailActionUIState.load(
            gameDetailFragmentArgs.gameDetailArgs.productId
        )

        gameDetailViewModel.gameDetailActionUIState.state.observe(viewLifecycleOwner) { state ->
            binding.viewFlipper.displayedChild = when(state) {
                GameDetailActionUIStateLiveData.UIState.Loading -> {
                    binding.fragmentGameDetailShimmer.root.startShimmer()
                    UI_STATE_LOADING
                }
                is GameDetailActionUIStateLiveData.UIState.Success -> {
                    binding.fragmentGameDetailShimmer.root.stopShimmer()
                    setupGameDetail(state)
                    UI_STATE_SUCCESS
                }
                is GameDetailActionUIStateLiveData.UIState.Error -> {
                    binding.fragmentGameDetailShimmer.root.stopShimmer()
                    UI_STATE_ERROR
                }
            }
        }
    }

    private fun setupGameDetail(state: GameDetailActionUIStateLiveData.UIState.Success) {

        imageLoader.load(
            binding.imageGame,
            "${
                BuildConfig.IMAGE_LOADER_URL.replace(
                    "c_scale,w_300",
                    "c_scale,w_600"
                )
            }${gameDetailFragmentArgs.gameDetailArgs.imageUrl}",
            object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    setTitle(gameDetailFragmentArgs.gameDetailArgs.title)
                    setCollapsingToolbar()
                    binding.gameDescription.text = Html.fromHtml(
                        state.data.description,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )

                    if (!state.data.hasDiscount) {
                        binding.gamePrice.text = state.data.price
                        binding.gamePrice.paintFlags = Paint.ANTI_ALIAS_FLAG
                    } else {
                        binding.gamePrice.text = state.data.price
                        binding.gamePriceDiscount.text = state.data.priceDiscount
                        binding.gamePrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                    return false
                }

            }
        )
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

fun Fragment.setTitle(text: String) {
    (requireActivity() as? MainActivity)?.setTitle(text)
}

fun Fragment.setCollapsingToolbar(isCollapse: Boolean = true, animated: Boolean = false) {
    (requireActivity() as? MainActivity)?.setCollapsingToolbar(isCollapse, animated)
}