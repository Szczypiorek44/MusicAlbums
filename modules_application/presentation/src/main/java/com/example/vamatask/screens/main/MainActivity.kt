package com.example.vamatask.screens.main

import android.os.Bundle
import android.view.WindowManager
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

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        showListFragment()
    }

    private fun showListFragment() {
        showFragment(
            AlbumListFragment(onAlbumClick = { showDetailsFragment(it) })
        )
    }

    private fun showDetailsFragment(album: Album) {
        showFragment(
            AlbumDetailsFragment(album),
            true
        )
    }

    private fun showFragment(fragment: Fragment, shouldAddToBackStack: Boolean = false) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        if (shouldAddToBackStack) {
            fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }
}