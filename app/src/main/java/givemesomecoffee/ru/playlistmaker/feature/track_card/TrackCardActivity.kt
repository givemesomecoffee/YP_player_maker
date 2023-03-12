package givemesomecoffee.ru.playlistmaker.feature.track_card

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.data.TracksRepository
import givemesomecoffee.ru.playlistmaker.feature.track_card.model.TrackUi
import givemesomecoffee.ru.playlistmaker.feature.track_card.widget.TrackInfoItemDecoration
import givemesomecoffee.ru.playlistmaker.feature.track_card.widget.TrackInfoAdapter
import givemesomecoffee.ru.playlistmaker.navigation.Screens
import givemesomecoffee.ru.playlistmaker.presentation.utils.dpToPx
import givemesomecoffee.ru.playlistmaker.presentation.utils.initSecondaryScreen

class TrackCardActivity : AppCompatActivity() {

    private var id: String? = null

    private val tracksApi = TracksRepository.getInstance()

    private val infoAdapter = TrackInfoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSecondaryScreen("")
        setContentView(R.layout.activity_track_card)
        initView()
        obtainArguments()
        id?.let { init(it) }

    }

    private fun init(id: String) {
        tracksApi.getTrack(id) {
            updateScreen(TrackUi.mapFrom(it, this))
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun updateScreen(track: TrackUi) {
        Glide.with(this).load(track.artworkUrl100.replaceAfterLast(URL_DELIMITER, IMAGE_SIZE))
            .fitCenter()
            .transform(RoundedCorners(this.dpToPx(8)))
            .placeholder(R.drawable.ic_placeholder).into(findViewById(R.id.track_image))
        findViewById<TextView>(R.id.track_title).text = track.trackName
        findViewById<TextView>(R.id.track_artist).text = track.artistName
        findViewById<TextView>(R.id.track_duration).text = track.trackTime
        infoAdapter.info = track.info
        infoAdapter.notifyDataSetChanged()

    }

    private fun initView() {
        findViewById<RecyclerView>(R.id.track_info).apply {
            adapter = infoAdapter
            layoutManager = LinearLayoutManager(context)

            if (itemDecorationCount == 0) {
                addItemDecoration(
                    TrackInfoItemDecoration(
                        verticalOffset = R.dimen.track_description_vertical_offset,
                        context = context
                    )
                )
            }
        }
    }

    private fun obtainArguments() {
        id = intent.getStringExtra(Screens.TrackCard.ID_ARG_NAME)
    }

    companion object {
        const val URL_DELIMITER = "/"
        const val IMAGE_SIZE = "512x512bb.jpg"
    }
}