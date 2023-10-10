package com.farmani.xmusic.fragments

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmani.xmusic.R
import com.farmani.xmusic.adapter.MusicAdapter
import com.farmani.xmusic.allSongs
import com.farmani.xmusic.databinding.FragmentAllMusicBinding
import com.farmani.xmusic.getAllAudioFromDevice
import com.farmani.xmusic.mainList
import com.farmani.xmusic.model.Music
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

var menuItemAllSongs: MenuItem? = null
class AllMusicFragment : Fragment() {
    private var binding: FragmentAllMusicBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllMusicBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding!!.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPermission()

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getPermission() {
        Dexter.withContext(requireContext())
            .withPermission(Manifest.permission.READ_MEDIA_AUDIO)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    initRecView()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    // Show request if give permission once
                    token?.continuePermissionRequest()
                }
            }).check()
    }

    private fun initRecView() {
        getAllAudioFromDevice(requireContext())
        val recyclerView = binding!!.allMusicRV
        val songsAdapter = MusicAdapter(allSongs, requireContext())
        mainList.clear()
        mainList.addAll(allSongs)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = songsAdapter
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
        menuItemAllSongs = menu.findItem(R.id.search_menu)
        val searchItem = menuItemAllSongs!!.actionView as SearchView
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
            ((binding!!.allMusicRV.adapter) as MusicAdapter).filterList(tempList)
        }
    }

//    override fun onResume() {
//        super.onResume()
//        mainList.clear()
//        mainList.addAll(allSongs)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}