package com.farmani.xmusic.model

import android.net.Uri

data class Music(
    val title: String,
    val artist: String,
    val albumName: String,
    val filePath: String,
    val coverArtUri: Uri,
)
