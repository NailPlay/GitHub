package com.mbrains.presentation.ui

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity;
import com.mbrains.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Получаем ViewPager и устанавливаем в него адаптер
        val viewPager = findViewById<ViewPager>(R.id.viewpager)
        viewPager.adapter = PagerAdapter(this, supportFragmentManager)

        // Передаём ViewPager в TabLayout
        val tabLayout = findViewById<TabLayout>(R.id.sliding_tabs)
        tabLayout.setupWithViewPager(viewPager)
    }



    override fun onBackPressed() {
        val fm = supportFragmentManager
        for (frag in fm.fragments) {
            if (frag.isVisible) {
                val childFm = frag.childFragmentManager
                if (childFm.backStackEntryCount > 0) {
                    childFm.popBackStack()
                    return
                }
            }
        }
        super.onBackPressed()
    }


}