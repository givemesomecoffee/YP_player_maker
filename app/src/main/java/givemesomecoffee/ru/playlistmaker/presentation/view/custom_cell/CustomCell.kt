package givemesomecoffee.ru.playlistmaker.presentation.view.custom_cell

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.isVisible
import givemesomecoffee.ru.playlistmaker.R

class CustomCell(context: Context, attributes: AttributeSet? = null) :
    LinearLayout(context, attributes) {
    init {
        attributes?.let { initView(context, it) }
    }

    private fun initView(context: Context, attributes: AttributeSet) {
        inflate(context, R.layout.item_cell, this).apply {
            val attrs = context.obtainStyledAttributes(attributes, R.styleable.CustomCell)
            val type = attrs.getInteger(R.styleable.CustomCell_cell_type, 0)
            val icon = attrs.getDrawable(R.styleable.CustomCell_cell_icon)
            val title = attrs.getString(R.styleable.CustomCell_cell_title)
            findViewById<TextView>(R.id.title)?.text = title
            findViewById<ImageView>(R.id.right_icon)?.apply {
                isVisible = type == TYPE_SIMPLE
                icon?.let { setImageDrawable(it) }
            }
            findViewById<SwitchCompat>(R.id.switch_control).isVisible = type == TYPE_SWITCH
            attrs.recycle()
        }

    }

    companion object{
        const val TYPE_SIMPLE = 0
        const val TYPE_SWITCH = 1
    }

}