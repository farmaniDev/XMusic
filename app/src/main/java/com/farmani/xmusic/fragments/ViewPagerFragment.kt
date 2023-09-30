package com.farmani.xmusic.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.farmani.xmusic.adapter.ViewPagerAdapter
import com.farmani.xmusic.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayout

class ViewPagerFragment : Fragment() {
    private var binding: FragmentViewPagerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = binding!!.viewPager
        val tabLayout = binding!!.tabLayout
        val adapter = ViewPagerAdapter(
            mutableListOf(AllMusicFragment(), FavoriteSongsFragment()),
            requireActivity().supportFragmentManager,
            lifecycle
        )

        viewPager.adapter = adapter
        tabLayout.addTab(tabLayout.newTab().setText("All Music"))
        tabLayout.addTab(tabLayout.newTab().setText("Favorite"))

        // It's anonymous class so we use object as the name to create an object from it
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        // Change tabLayout based on viewPager
        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}