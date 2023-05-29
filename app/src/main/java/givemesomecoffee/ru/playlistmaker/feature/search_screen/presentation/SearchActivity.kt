package givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.core.navigation.Screens
import givemesomecoffee.ru.playlistmaker.core.navigation.goToScreen
import givemesomecoffee.ru.playlistmaker.core.presentation.utils.dpToPx
import givemesomecoffee.ru.playlistmaker.core.presentation.utils.initSecondaryScreen
import givemesomecoffee.ru.playlistmaker.core.presentation.view.empty_view.PlaceholderScreen
import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.SearchScreenUi
import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.TrackUi
import givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation.widget.ItemClickListener
import givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation.widget.TracksAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity(), ItemClickListener {
    private val viewModel by viewModel<SearchActivityViewModel>()

    private val adapter = TracksAdapter(this)

    private var searchClose: ImageView? = null
    private var searchField: EditText? = null
    private var tracksList: RecyclerView? = null
    private var errorPlaceholder: PlaceholderScreen? = null
    private var emptyPlaceholder: PlaceholderScreen? = null
    private var searchProgress: ProgressBar? = null
    private var search: FrameLayout? = null

    private val handler = Handler(Looper.getMainLooper())
    private var isClickAllowed = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initSecondaryScreen(getString(R.string.title_search))
        initView()
    }

    override fun onTrackClicked(track: TrackUi) {
        if (clickDebounce()) {
            viewModel.updateSearchHistory(track)
            goToScreen(Screens.TrackCard(track.trackId, track.trackSource))
        }
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    private fun initView() {
        tracksList = findViewById(R.id.tracks_list)
        emptyPlaceholder = findViewById(R.id.search_empty)
        errorPlaceholder = findViewById(R.id.search_error)
        searchProgress = findViewById(R.id.search_progress)
        search = findViewById(R.id.search)
        findViewById<TextView>(R.id.clear_history)?.setOnClickListener {
            viewModel.clearSearchHistory()
        }
        configureRecycler()
        configureSearch()
        viewModel.state.observe(this, ::onStateChanged)
    }

    private fun configureSearch() {
        searchClose = search?.findViewById(R.id.search_close)
        searchField = search?.findViewById(R.id.search_field)
        searchField?.setOnFocusChangeListener { view, hasFocus ->
            viewModel.searchInputChanged(searchField?.text?.toString(), hasFocus)
        }
        searchClose?.setOnClickListener {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(searchField?.windowToken, 0)
            searchField?.text?.clear()
            searchField?.clearFocus()
            viewModel.searchInputChanged(searchField?.text?.toString(), false)
        }
        searchField?.apply {
            doOnTextChanged { text, _, _, _ ->
                searchClose?.isVisible = text?.isNotEmpty() == true
                viewModel.searchInputChanged(text?.toString(), searchField?.hasFocus() == true)
            }
        }
    }

    private fun configureRecycler() {
        if (tracksList?.itemDecorationCount == 0) {
            tracksList?.addItemDecoration(object : ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.set(0, this@SearchActivity.dpToPx(8), 0, this@SearchActivity.dpToPx(8))
                }
            })
        }
        tracksList?.adapter = adapter
    }

    private fun onStateChanged(state: SearchScreenUi) {
        searchProgress?.isVisible = state.loading
        emptyPlaceholder?.isVisible = state.showEmptyState
        errorPlaceholder?.apply {
            isVisible = state.showError
            if (state.showError) state.errorCallback?.let { setRetryCallback(it) }
        }
        findViewById<Group>(R.id.search_history).isVisible = state.showHistory
        adapter.tracks = state.data
        adapter.notifyDataSetChanged()
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

}