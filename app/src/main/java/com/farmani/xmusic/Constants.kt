package com.farmani.xmusic

import androidx.media3.common.Player
import com.farmani.xmusic.model.Music

val allSongs = mutableListOf<Music>()
var currentSong: Music? = null
var playerList = mutableListOf<Player>()