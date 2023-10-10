package com.farmani.xmusic.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.farmani.xmusic.R
import com.farmani.xmusic.currentSong
import com.farmani.xmusic.fragments.menuItemAllSongs
import com.farmani.xmusic.fragments.menuItemFavSongs
import com.farmani.xmusic.model.Music

class MusicAdapter(var musicList: MutableList<Music>, var context: Context) :
    RecyclerView.Adapter<MusicAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val artist: TextView
        val coverArt: ImageView

        init {
            view.apply {
                title = findViewById(R.id.titleTV)
                artist = findViewById(R.id.artistTV)
                coverArt = findViewById(R.id.coverArt)
            }
            view.setOnClickListener {
                currentSong = musicList[absoluteAdapterPosition]
                if (menuItemAllSongs != null) {
                    menuItemAllSongs!!.collapseActionView()
                    menuItemAllSongs!!.isVisible = false
                }
                if (menuItemFavSongs != null) {
                    menuItemFavSongs!!.collapseActionView()
                    menuItemFavSongs!!.isVisible = false
                }
                Navigation.findNavController(view)
                    .navigate(R.id.action_viewPagerFragment_to_playerFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            title.text = musicList[position].title
            artist.text = musicList[position].artist
            Glide.with(context).load(musicList[position].coverArtUri)
                .transform(CenterCrop(), RoundedCorners(25)).into(coverArt)
        }
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    fun filterList(list: MutableList<Music>) {
        musicList = list
        notifyDataSetChanged()
    }
}