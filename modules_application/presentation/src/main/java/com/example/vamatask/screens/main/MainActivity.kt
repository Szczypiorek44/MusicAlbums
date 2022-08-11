package com.example.vamatask.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.data.models.Album
import com.example.vamatask.R
import com.example.vamatask.screens.albumdetails.AlbumDetailsFragment
import com.example.vamatask.screens.albumlist.AlbumListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            showListFragment()
        }

        setupFragmentManager()
    }

    private fun showListFragment() {
        showFragment(
            AlbumListFragment()
        )
    }

    private fun showDetailsFragment(album: Album) {
        showFragment(
            AlbumDetailsFragment.newInstance(album),
            true
        )
    }

    private fun setupFragmentManager() {
        supportFragmentManager.addFragmentOnAttachListener { _, fragment ->
            setNecessaryFragmentCallbacks(fragment)
        }
        supportFragmentManager.fragments.forEach { fragment ->
            setNecessaryFragmentCallbacks(fragment)
        }
    }

    private fun setNecessaryFragmentCallbacks(fragment: Fragment) {
        when (fragment) {
            is AlbumListFragment -> fragment.setOnAlbumClick(onAlbumClick())
        }
    }


    private fun showFragment(fragment: Fragment, shouldAddToBackStack: Boolean = false) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        if (shouldAddToBackStack) {
            fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        }
        fragmentTransaction.commit()
    }

    private fun onAlbumClick(): (album: Album) -> Unit {
        return { showDetailsFragment(it) }
    }
}