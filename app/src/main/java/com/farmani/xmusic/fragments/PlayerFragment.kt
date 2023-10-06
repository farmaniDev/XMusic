package com.farmani.xmusic.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cleveroad.play_widget.PlayLayout
import com.farmani.xmusic.currentSong
import com.farmani.xmusic.databinding.FragmentPlayerBinding
import com.farmani.xmusic.playMusic

class PlayerFragment : Fragment() {
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
        playLayout = binding!!.playLayout
        playMusic(requireContext(), currentSong!!.filePath)
        playLayout.startRevealAnimation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}