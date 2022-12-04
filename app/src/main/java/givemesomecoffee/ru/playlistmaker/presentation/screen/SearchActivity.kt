package givemesomecoffee.ru.playlistmaker.presentation.screen

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.presentation.utils.initSecondaryScreen

class SearchActivity : AppCompatActivity() {
    private var searchClose: ImageView? = null
    private var searchField: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initSecondaryScreen(getString(R.string.title_search))
        initView()
    }

    private fun initView() {
        val search = findViewById<FrameLayout>(R.id.search)
        searchClose = search.findViewById(R.id.search_close)
        searchField = search.findViewById(R.id.search_field)
        searchClose?.setOnClickListener {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
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
}