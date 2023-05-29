package givemesomecoffee.ru.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import givemesomecoffee.ru.playlistmaker.core.data.local.SettingsStorage
import givemesomecoffee.ru.playlistmaker.core.di.CoreComponent
import givemesomecoffee.ru.playlistmaker.core.di.FeaturesComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.startKoin

class App : Application(), KoinComponent {
    private var darkTheme = false

    private val localStorage: SettingsStorage by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(buildList {
                addAll(CoreComponent.modules)
                addAll(FeaturesComponent.modules)
            })
        }
        darkTheme = localStorage.isDarkTheme() == true
        switchTheme()
        localStorage.addOnSettingsChangedListener() {
            darkTheme = it
            switchTheme()
        }

    }

    override fun onTerminate() {
        localStorage.removeOnSettingsChangedListener()
        super.onTerminate()
    }

    private fun switchTheme() {
        AppCompatDelegate.setDefaultNightMode(
            if (darkTheme) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}