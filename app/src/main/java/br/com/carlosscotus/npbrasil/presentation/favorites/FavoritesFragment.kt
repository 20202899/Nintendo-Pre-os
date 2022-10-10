package br.com.carlosscotus.npbrasil.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ListAdapter
import br.com.carlosscotus.npbrasil.R
import br.com.carlosscotus.npbrasil.databinding.FragmentFavoritesBinding
import br.com.carlosscotus.npbrasil.framework.imageloader.ImageLoader
import br.com.carlosscotus.npbrasil.presentation.common.getGenericAdapterOf
import br.com.carlosscotus.npbrasil.presentation.detail.setTitle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding
        get() = _binding!!

    @Inject
    lateinit var imageLoader: ImageLoader

    private val viewModel: FavoritesViewModel by viewModels()

    private val favoritesAdapter by lazy {
        getGenericAdapterOf { FavoriteViewHolder.create(it, imageLoader) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFavoritesBinding.inflate(inflater, container, false).apply {
      _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(getString(R.string.menu_title_favorites))

        binding.recyclerview.run {
            setHasFixedSize(false)
            adapter = favoritesAdapter
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when(state) {
                FavoritesViewModel.UIState.Empty -> favoritesAdapter.submitList(emptyList())
                is FavoritesViewModel.UIState.Success -> favoritesAdapter.submitList(state.list)
            }
        }

        viewModel.getFavorites()
    }
}