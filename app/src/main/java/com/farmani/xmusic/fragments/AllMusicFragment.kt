package com.farmani.xmusic.fragments

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmani.xmusic.adapter.MusicAdapter
import com.farmani.xmusic.allSongs
import com.farmani.xmusic.databinding.FragmentAllMusicBinding
import com.farmani.xmusic.getAllAudioFromDevice
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class AllMusicFragment : Fragment() {
    private var binding: FragmentAllMusicBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllMusicBinding.inflate(inflater, container, false)
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