package givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.Track
import givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation.widget.TracksAdapter
import givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation.widget.dpToPx
import givemesomecoffee.ru.playlistmaker.presentation.utils.initSecondaryScreen

class SearchActivity : AppCompatActivity() {
    private var searchClose: ImageView? = null
    private var searchField: EditText? = null
    private var tracksList: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initSecondaryScreen(getString(R.string.title_search))
        initView()

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getCharSequence(SEARCH_TEXT_KEY)?.let {
            searchField?.setText(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharSequence(SEARCH_TEXT_KEY, searchField?.text)
    }

    private fun initView() {
        tracksList = findViewById(R.id.tracks_list)
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
        tracksList?.adapter = TracksAdapter(Track.getMockData())
        val search = findViewById<FrameLayout>(R.id.search)
        searchClose = search.findViewById(R.id.search_close)
        searchField = search.findViewById(R.id.search_field)
        searchClose?.setOnClickListener {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(searchField?.windowToken, 0)
            searchField?.text?.clear()
            searchField?.clearFocus()
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
                }

                override fun afterTextChanged(s: Editable?) {
                    //  TODO("Not yet implemented")
                }
            })
        }
    }

    companion object {
        const val SEARCH_TEXT_KEY = "search_text"
    }
}