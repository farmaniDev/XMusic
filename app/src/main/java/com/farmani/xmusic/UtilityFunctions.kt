package com.farmani.xmusic

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.farmani.xmusic.model.Music

fun getAllAudioFromDevice(context: Context) {
    val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    val projection = arrayOf(
//        MediaStore.Audio.AudioColumns.DATA,
        MediaStore.Audio.Media.DATA,
//        MediaStore.Audio.AudioColumns.TITLE,
        MediaStore.Audio.Media.TITLE,
//        MediaStore.Audio.AudioColumns.ALBUM,
        MediaStore.Audio.Media.ALBUM,
//        MediaStore.Audio.AudioColumns.ARTIST,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.ALBUM_ID // For cover we need Album Id
    )
    val cursor: Cursor? = context.contentResolver.query(
        uri,
        projection,
        null,
        null,
        null
    )

    if (cursor != null) {
        while (cursor.moveToNext()) {
            // indexes are based on projection's items
            val path: String = cursor.getString(0)
            val title: String = cursor.getString(1)
            val album: String = cursor.getString(2)
            val artist: String = cursor.getString(3)
            val pathUri = Uri.parse("content://media/external/audio/albumart")
            val albumArtUri = ContentUris.withAppendedId(pathUri, cursor.getLong(4))
            allSongs.add(Music(title, artist, album, path, albumArtUri))
        }
        cursor.close()
    }
}

fun playMusic(context: Context, filePath: String) {
    val player = ExoPlayer.Builder(context).build()
    val mediaItem = MediaItem.fromUri(Uri.parse(filePath))
    player.setMediaItem(mediaItem)
    player.prepare()
    player.play()
    // On multiple click app still works
    player.addListener(object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            if (playbackState == Player.STATE_ENDED) {
                player.release()
            }
        }
    })
}