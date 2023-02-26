package givemesomecoffee.ru.playlistmaker.presentation.view.empty_view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import givemesomecoffee.ru.playlistmaker.R

class PlaceholderScreen(context: Context, attributes: AttributeSet? = null) :
    LinearLayout(context, attributes) {
    init {
        attributes?.let { initView(context, it) }
    }


    private fun initView(context: Context, attributes: AttributeSet) {
        inflate(context, R.layout.item_placeholder_screen, this).apply {
            val attrs = context.obtainStyledAttributes(attributes, R.styleable.PlaceholderScreen)
            val type = attrs.getInteger(R.styleable.PlaceholderScreen_placeholder_screen_type, 0)
            val icon = attrs.getDrawable(R.styleable.PlaceholderScreen_placeholder_icon)
            val description = attrs.getString(R.styleable.PlaceholderScreen_placeholder_description)
            findViewById<TextView>(R.id.placeholder_description)?.text = description
            findViewById<TextView>(R.id.placeholder_retry)?.apply {
                isVisible = type == TYPE_ERROR
            }
            findViewById<ImageView>(R.id.placeholder_image).setImageDrawable(icon)
            attrs.recycle()
        }

    }

    fun setRetryCallback(callback: ()->Unit){
        findViewById<TextView>(R.id.placeholder_retry)?.setOnClickListener {
            callback()
        }
    }

    companion object{
        const val TYPE_ERROR = 1
        const val TYPE_INFO = 0
    }
}