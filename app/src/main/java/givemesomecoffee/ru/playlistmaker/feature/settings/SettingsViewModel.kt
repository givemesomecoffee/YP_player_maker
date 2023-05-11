package givemesomecoffee.ru.playlistmaker.feature.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import givemesomecoffee.ru.playlistmaker.feature.settings.domain.SettingsInteractor
import givemesomecoffee.ru.playlistmaker.feature.settings.model.SettingsScreenStateUi

class SettingsViewModel( private val settingsInteractor: SettingsInteractor) : ViewModel() {

    private val _state = MutableLiveData<SettingsScreenStateUi>()
    val state: LiveData<SettingsScreenStateUi> = _state

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