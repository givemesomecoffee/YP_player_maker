package givemesomecoffee.ru.playlistmaker.presentation.view.main_menu_item

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import givemesomecoffee.ru.playlistmaker.R

class MainMenuItem(context: Context, attributes: AttributeSet? = null) :
    LinearLayout(context, attributes) {
    init {
        attributes?.let { initView(context, it) }
    }

    private fun initView(context: Context, attributes: AttributeSet) {
        inflate(context, R.layout.item_main_menu, this).apply {
            val attrs = context.obtainStyledAttributes(attributes, R.styleable.MainMenuItem)
            val icon = attrs.getDrawable(R.styleable.MainMenuItem_main_menu_icon)
            val title = attrs.getString(R.styleable.MainMenuItem_main_menu_title)
            findViewById<TextView>(R.id.content)?.apply {
                text = title
                setCompoundDrawablesRelativeWithIntrinsicBounds(icon, null, null, null)
            }
            attrs.recycle()
        }
    }
}
