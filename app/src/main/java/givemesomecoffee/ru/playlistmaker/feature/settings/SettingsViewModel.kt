package givemesomecoffee.ru.playlistmaker.feature.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import givemesomecoffee.ru.playlistmaker.feature.settings.domain.SettingsInteractor
import givemesomecoffee.ru.playlistmaker.feature.settings.model.SettingsScreenStateUi

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableLiveData<SettingsScreenStateUi>()
    val state: LiveData<SettingsScreenStateUi> = _state

    private val settingsInteractor = SettingsInteractor.create(application)

    fun sync() {
        _state.value = SettingsScreenStateUi(settingsInteractor.isDarkTheme())
    }

    fun switchTheme() {
        val prev = _state.value
        prev?.let {
            if (settingsInteractor.isDarkTheme() == prev.isDarkTheme) {
                _state.value = it.copy(isDarkTheme = !prev.isDarkTheme)
                settingsInteractor.setDarkTheme(!prev.isDarkTheme)
            }
        }
    }
}