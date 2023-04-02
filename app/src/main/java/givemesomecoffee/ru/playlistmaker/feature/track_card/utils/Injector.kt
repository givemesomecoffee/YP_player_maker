package givemesomecoffee.ru.playlistmaker.feature.track_card.utils

import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.playerApi(playerContractImpl: PlayerContract): PlayerApi{
    val temp = Player(playerContractImpl)
    temp.init(this, playerContractImpl)
    return temp
}