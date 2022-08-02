package com.example.vamatask.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.vamatask.R
import com.example.vamatask.screens.list.ListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showListFragment()
    }

    private fun showListFragment() {
        showFragment(ListFragment())
//        supportActionBar?.title = getString(R.string.app_name)
//        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun showFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commitAllowingStateLoss()
    }
}