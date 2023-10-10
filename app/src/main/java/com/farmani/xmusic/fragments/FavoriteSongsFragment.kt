package com.farmani.xmusic.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmani.xmusic.adapter.MusicAdapter
import com.farmani.xmusic.databinding.FragmentFavoriteSongsBinding
import com.farmani.xmusic.favSongs

class FavoriteSongsFragment : Fragment() {
    private var binding: FragmentFavoriteSongsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteSongsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecView()
    }

    override fun onResume() {
        super.onResume()
        initRecView()
    }

    private fun initRecView() {
        val recyclerView = binding!!.favSongsRV
        val songsAdapter = MusicAdapter(favSongs, requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = songsAdapter
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}