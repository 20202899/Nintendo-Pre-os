package br.com.carlosscotus.npbrasil.presentation.communicate

import android.os.Parcelable
import android.view.View
import kotlinx.parcelize.Parcelize

class ActivityFragmentCommunicate private constructor() {
    @Parcelize
    enum class EventCommunicate: Parcelable {
        FILTER_ACTION
    }

    companion object {
        const val FILTER_EVENT_TAG = "filterTag"
    }
}