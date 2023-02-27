package givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.data.Network
import givemesomecoffee.ru.playlistmaker.feature.search_screen.data.local.SearchHistoryStorage
import givemesomecoffee.ru.playlistmaker.feature.search_screen.data.local.StorageHolder
import givemesomecoffee.ru.playlistmaker.feature.search_screen.data.remote.TracksApi
import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.SearchScreenUi
import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.TrackUi
import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.TracksResponse
import givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation.widget.ItemClickListener
import givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation.widget.TracksAdapter
import givemesomecoffee.ru.playlistmaker.presentation.utils.dpToPx
import givemesomecoffee.ru.playlistmaker.presentation.utils.initSecondaryScreen
import givemesomecoffee.ru.playlistmaker.presentation.view.empty_view.PlaceholderScreen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity(), ItemClickListener {
    private val adapter = TracksAdapter(this)

    private var searchClose: ImageView? = null
    private var searchField: EditText? = null
    private var tracksList: RecyclerView? = null
    private var errorPlaceholder: PlaceholderScreen? = null
    private var emptyPlaceholder: PlaceholderScreen? = null
    private var searchProgress: ProgressBar? = null
    private var search: FrameLayout? = null
    private val tracksApi = Network.retrofit.create(TracksApi::class.java)

    private var localStorage: SearchHistoryStorage? = null

    private var isSearchHistoryShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        localStorage = StorageHolder.getSearchHistoryStorage(this)
        initSecondaryScreen(getString(R.string.title_search))
        initView()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedState?.let { onStateChanged(it) }
        savedInstanceState.getCharSequence(SEARCH_TEXT_KEY)?.let {
            searchField?.setText(it)
        }
    }

    override fun onTrackClicked(track: TrackUi) {
        localStorage?.updateSearchHistory(track)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharSequence(SEARCH_TEXT_KEY, searchField?.text)
    }

    private fun initView() {
        tracksList = findViewById(R.id.tracks_list)
        emptyPlaceholder = findViewById(R.id.search_empty)
        errorPlaceholder = findViewById(R.id.search_error)
        searchProgress = findViewById(R.id.search_progress)
        search = findViewById(R.id.search)
        configureRecycler()
        configureSearch()
    }

    override fun onDestroy() {
        if (isFinishing) savedState = null
        super.onDestroy()

    }

    private fun configureSearch() {
        searchClose = search?.findViewById(R.id.search_close)
        searchField = search?.findViewById(R.id.search_field)
        searchField?.setOnFocusChangeListener { view, hasFocus ->
            showSearchHistory(hasFocus && searchField?.text?.isEmpty() == true)
        }
        searchClose?.setOnClickListener {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(searchField?.windowToken, 0)
            searchField?.text?.clear()
            searchField?.clearFocus()
            onStateChanged(SearchScreenUi())
        }
        searchField?.let {
            it.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    search(it.text?.toString())
                    searchField?.clearFocus()
                    true
                }
                false
            }
        }
        searchField?.apply {
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // TODO("Not yet implemented")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    searchClose?.isVisible = s?.isNotEmpty() == true
                    if (s?.isEmpty() == true && searchField?.hasFocus() == true) {
                        showSearchHistory(true)
                    }
                    if (s?.isNotEmpty() == true && isSearchHistoryShown) {
                        showSearchHistory(false)
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    //  TODO("Not yet implemented")
                }
            })
        }
    }

    private fun showSearchHistory(show: Boolean) {
        if (show) {
            val tracks = localStorage?.getSearchHistory()
            if (tracks?.isNotEmpty() == true) {
                isSearchHistoryShown = true
                adapter.tracks = tracks.toList()
                adapter.notifyDataSetChanged()
                findViewById<Group>(R.id.search_history).isVisible = true
                findViewById<TextView>(R.id.clear_history)?.setOnClickListener {
                    localStorage?.clearSearchHistory()
                    resetSearchHistoryUi()
                }
            }
        } else {
            isSearchHistoryShown = false
            resetSearchHistoryUi()
        }
    }

    private fun resetSearchHistoryUi(){
        findViewById<Group>(R.id.search_history).isVisible = false
        adapter.tracks = emptyList()
        adapter.notifyDataSetChanged()
    }

    private fun configureRecycler() {
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
        tracksList?.adapter = adapter
    }

    private fun search(filter: String?) {
        if (filter.isNullOrEmpty()) {
            onStateChanged(SearchScreenUi())
        } else {
            onStateChanged(SearchScreenUi(loading = true))
            tracksApi.search(filter).enqueue(object : Callback<TracksResponse> {
                override fun onResponse(
                    call: Call<TracksResponse>,
                    response: Response<TracksResponse>
                ) {
                    onStateChanged(SearchScreenUi.mapFrom(response))
                }

                override fun onFailure(call: Call<TracksResponse>, t: Throwable) {
                    onStateChanged(SearchScreenUi.mapFrom(t))
                }
            })
        }
    }

    private fun onStateChanged(state: SearchScreenUi) {
        if (!state.loading) savedState = state
        searchProgress?.isVisible = state.loading
        emptyPlaceholder?.isVisible = state.showEmptyState
        errorPlaceholder?.apply {
            isVisible = state.showError
            if (state.showError) setRetryCallback { search(searchField?.text?.toString()) }
        }
        adapter.tracks = state.data
        adapter.notifyDataSetChanged()
    }

    companion object {
        private var savedState: SearchScreenUi? = null
        const val SEARCH_TEXT_KEY = "search_text"

    }

}