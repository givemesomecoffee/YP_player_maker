package givemesomecoffee.ru.playlistmaker.presentation.screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.presentation.utils.initSecondaryScreen
import givemesomecoffee.ru.playlistmaker.presentation.utils.validateEvent
import givemesomecoffee.ru.playlistmaker.presentation.view.custom_cell.CustomCell

class SettingsActivity : AppCompatActivity() {

    private var share: CustomCell? = null
    private var support: CustomCell? = null
    private var agreement: CustomCell? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initSecondaryScreen(getString(R.string.title_settings))
        initView()
    }

    private fun initView() {
        share = findViewById(R.id.share)
        support = findViewById(R.id.support)
        agreement = findViewById(R.id.user_agreement)
        share?.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_url))
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        support?.setOnClickListener {
            composeEmail(
                subject = getString(R.string.support_email_header),
                body = getString(R.string.support_email_body)
            )
        }
        agreement?.setOnClickListener {
            openWebPage(getString(R.string.user_agreement_url))
        }
    }

    private fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        validateEvent(intent)
    }

    private fun composeEmail(subject: String, body: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, listOf(getString(R.string.support_email)).toTypedArray())
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        validateEvent(intent)
    }
}