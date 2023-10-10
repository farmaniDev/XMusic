package com.farmani.xmusic.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.cleveroad.play_widget.PlayLayout
import com.farmani.xmusic.R
import com.farmani.xmusic.allSongs
import com.farmani.xmusic.currentSong
import com.farmani.xmusic.databinding.FragmentPlayerBinding
import com.farmani.xmusic.favSongs
import com.farmani.xmusic.mainList
import com.farmani.xmusic.playMusic
import com.farmani.xmusic.playerList
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class PlayerFragment : Fragment() {
    var timer: Timer? = null
    lateinit var playLayout: PlayLayout
    private var binding: FragmentPlayerBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.favIV.setOnClickListener {
            if (currentSong!!.isFav) {
                binding!!.favIV.setImageResource(R.drawable.baseline_favorite_border)
                allSongs.find { it.title == currentSong!!.title }!!.isFav = false
                favSongs.remove(currentSong)
            } else {
                binding!!.favIV.setImageResource(R.drawable.baseline_favorite)
                allSongs.find { it.title == currentSong!!.title }!!.isFav = true
                favSongs.add(currentSong!!)
            }
        }
        playLayout = binding!!.playLayout
        playMusic(requireContext(), currentSong!!.filePath)
        startTimer()
        playLayout.startRevealAnimation()
        playLayout.setImageURI(currentSong!!.coverArtUri)
        playLayout.setOnButtonsClickListener(object : PlayLayout.OnButtonsClickListener {
            override fun onShuffleClicked() {

            }

            override fun onSkipPreviousClicked() {
                skipSong(false)
            }

            override fun onSkipNextClicked() {
                skipSong(true)
            }

            override fun onRepeatClicked() {

            }

            override fun onPlayButtonClicked() {
                if (playLayout.isOpen) {
                    playLayout.startDismissAnimation()
                    playerList.last().pause()
                } else {
                    playLayout.startRevealAnimation()
                    playerList.last().play()
                }
            }
        })
        playLayout.setOnProgressChangedListener(object : PlayLayout.OnProgressChangedListener {
            override fun onPreSetProgress() {
                cancelTimer()
            }

            override fun onProgressChanged(progress: Float) {
                playerList.last().seekTo((playerList.last().duration * progress).toLong())
                startTimer()
            }
        })
    }

    private fun startTimer() {
        timer = Timer()
        timer!!.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                // ExoPlayer doesn't allow us to use another threat like run to use our code so we use Coroutines
                lifecycleScope.launch {
                    playLayout.setProgress(playerList.last().currentPosition.toFloat() / playerList.last().duration)
                }
            }
        }, 0, 1000)
    }

    private fun cancelTimer() {
        if (timer == null) return
        timer!!.cancel()
        timer = null
    }

    private fun skipSong(isNextClicked: Boolean) {
        try {
            val index = if (isNextClicked) 1 else -1
            val newSongIndex =
                mainList.indexOf(mainList.find { it.title == currentSong!!.title }) + index
            currentSong = mainList[newSongIndex]
            playerList.last().stop()
            playLayout.setImageURI(currentSong!!.coverArtUri)
            playMusic(requireContext(), currentSong!!.filePath)
            if (!playLayout.isOpen)
                playLayout.startRevealAnimation()
        } catch (_: IndexOutOfBoundsException) {

        }
    }

    override fun onResume() {
        super.onResume()
        if (currentSong!!.isFav)
            binding!!.favIV.setImageResource(R.drawable.baseline_favorite)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        playerList.last().stop()
        binding = null
    }
}