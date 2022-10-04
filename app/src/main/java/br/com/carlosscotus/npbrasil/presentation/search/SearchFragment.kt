package br.com.carlosscotus.npbrasil.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import br.com.carlosscotus.npbrasil.R
import br.com.carlosscotus.npbrasil.presentation.detail.setCollapsingToolbar
import br.com.carlosscotus.npbrasil.presentation.detail.setTitle

class SearchFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCollapsingToolbar()
        setTitle(getString(R.string.title_search))
    }
}