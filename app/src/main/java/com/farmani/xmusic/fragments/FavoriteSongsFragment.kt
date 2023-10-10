package com.farmani.xmusic.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmani.xmusic.R
import com.farmani.xmusic.adapter.MusicAdapter
import com.farmani.xmusic.databinding.FragmentFavoriteSongsBinding
import com.farmani.xmusic.favSongs
import com.farmani.xmusic.mainList
import com.farmani.xmusic.model.Music

var menuItemFavSongs: MenuItem? = null
class FavoriteSongsFragment : Fragment() {
    private var binding: FragmentFavoriteSongsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteSongsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecView()
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
        menuItemFavSongs = menu.findItem(R.id.search_menu)
        val searchItem = menuItemFavSongs!!.actionView as SearchView
        searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null)
                    filter(newText)
                return false
            }
        })
    }

    private fun filter(input: String) {
        val tempList = mutableListOf<Music>()
        for (music in mainList) {
            if (music.title.lowercase().contains(input.lowercase())) {
                tempList.add(music)
            }
            ((binding!!.favSongsRV.adapter) as MusicAdapter).filterList(tempList)
        }
    }

    private fun initRecView() {
        val recyclerView = binding!!.favSongsRV
        val songsAdapter = MusicAdapter(favSongs, requireContext())
        mainList.clear()
        mainList.addAll(favSongs)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = songsAdapter
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

//    override fun onResume() {
//        super.onResume()
//        initRecView()
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}