package br.com.carlosscotus.npbrasil.presentation.games

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import br.com.carlosscotus.core.data.GameFilters
import br.com.carlosscotus.npbrasil.R
import br.com.carlosscotus.npbrasil.databinding.FragmentGamesBinding
import br.com.carlosscotus.npbrasil.framework.imageloader.ImageLoader
import br.com.carlosscotus.npbrasil.presentation.detail.GameDetailArg
import br.com.carlosscotus.npbrasil.presentation.detail.setTitle
import br.com.carlosscotus.npbrasil.presentation.games.adapters.GamesAdapter
import br.com.carlosscotus.npbrasil.presentation.games.adapters.GamesLoadMoreStateAdapter
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GamesFragment : Fragment() {

    private var _binding: FragmentGamesBinding? = null
    private val binding: FragmentGamesBinding
        get() = _binding!!

    private val viewModel: GamesViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementReturnTransition = MaterialContainerTransform().apply {
            setPathMotion(MaterialArcMotion())
            scrimColor = Color.TRANSPARENT
        }

        viewModel.gameSaveFilterActionUIStateLiveData.load()
    }

    private val gamesAdapter: GamesAdapter by lazy {
        GamesAdapter(imageLoader) { game, view ->
            game?.run {
                findNavController().navigate(
                    GamesFragmentDirections.actionGamesFragmentToGameDetailFragment(
                        GameDetailArg(
                            id,
                            title,
                            imageUrl,
                            price,
                            featured,
                            priceDiscount,
                            discountPercentage,
                            hasDiscount,
                            description,
                            productId,
                            releaseDate
                        ),
                        game.title
                    ),
                    FragmentNavigatorExtras(view to view.transitionName)
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentGamesBinding.inflate(inflater, container, false)
        .apply {
            _binding = this
        }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(getString(R.string.title_home))

        setupRecyclerview()
        setDefaultFilter()
        setLoadObserverDetailUIState()
        setupCardViewConnect()
        observerAdapterState()
    }

    private fun setupRecyclerview() {
        postponeEnterTransition()
        with(binding.recyclerview) {
            setHasFixedSize(false)
            adapter = gamesAdapter.withLoadStateFooter(
                GamesLoadMoreStateAdapter {
                    gamesAdapter.retry()
                }
            )
            (view?.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }

    private fun setupCardViewConnect() {
        binding.fragmentGameError.refresh.setOnClickListener {
            gamesAdapter.refresh()
        }
    }

    private fun setDefaultFilter() {
        viewModel.setDefaultFilter {
            binding.chipGroup.check(it)
        }
    }

    private fun setLoadObserverDetailUIState() {
        binding.chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            viewModel.gameSaveFilterActionUIStateLiveData.saveFilter(
                if (checkedIds.first() == R.id.chip_all) {
                    GameFilters.SWITCH_ONLY
                } else if (checkedIds.first() == R.id.chip_news) {
                    GameFilters.SWITCH_COMING_SOON
                } else if (checkedIds.first() == R.id.chip_new_releases) {
                    GameFilters.SWItCH_NEWS_RELEASE
                } else GameFilters.SWITCH_SALES
            )
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is GameSaveFilterActionUIStateLiveData.UIState.Success -> {
                    gamesAdapter.submitData(viewLifecycleOwner.lifecycle, state.data)
                }
            }
        }
    }

    private fun observerAdapterState() {
        lifecycleScope.launch {
            gamesAdapter.loadStateFlow.collectLatest { state ->
                binding.flipperGames.displayedChild = when (state.refresh) {
                    is LoadState.Loading -> {
                        binding.fragmentGameShimmer.root.startShimmer()
                        UI_STATE_LOADING
                    }
                    is LoadState.Error -> {
                        binding.fragmentGameShimmer.root.stopShimmer()
                        UI_STATE_ERROR
                    }
                    else -> {
                        binding.fragmentGameShimmer.root.stopShimmer()
                        UI_STATE_SUCCESS
                    }
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