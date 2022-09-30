package br.com.carlosscotus.npbrasil.presentation.games

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import br.com.carlosscotus.npbrasil.BuildConfig
import br.com.carlosscotus.npbrasil.R
import br.com.carlosscotus.npbrasil.databinding.FragmentGamesBinding
import br.com.carlosscotus.npbrasil.framework.imageloader.ImageLoader
import br.com.carlosscotus.npbrasil.presentation.detail.GameDetailArg
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

    private val gamesAdapter: GamesAdapter by lazy {
        GamesAdapter(imageLoader) { game, view ->
            game?.run {
                findNavController().navigate(
                    GamesFragmentDirections.actionGamesFragmentToGameDetailFragment(
                        GameDetailArg(
                            id,
                            title,
                            imageUrl,
                            productId
                        ),
                        game.title
                    ),
                    FragmentNavigatorExtras(view to getString(R.string.shared_item_transition))
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = if (_binding == null) {
        FragmentGamesBinding.inflate(inflater, container, false)
            .apply {
                _binding = this
            }.root.also {
                observerCollect()
            }
    } else {
        binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMenu()
        setupRecyclerview()
        setupCardViewConnect()
        observerAdapterState()
    }

    private fun setupRecyclerview() {
        with(binding.recyclerview) {
            setHasFixedSize(false)
            adapter = gamesAdapter.withLoadStateFooter(
                GamesLoadMoreStateAdapter {
                    gamesAdapter.retry()
                }
            )
        }
    }

    private fun setupMenu() {
        activity?.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_top, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.STARTED)
    }

    private fun setupCardViewConnect() {
        binding.fragmentGameError.refresh.setOnClickListener {
            gamesAdapter.refresh()
        }
    }

    private fun observerCollect() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.gamesPaginate().collect { data ->
                    gamesAdapter.submitData(lifecycle, data)
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

    companion object {
        const val UI_STATE_LOADING = 0
        const val UI_STATE_SUCCESS = 1
        const val UI_STATE_ERROR = 2
    }
}