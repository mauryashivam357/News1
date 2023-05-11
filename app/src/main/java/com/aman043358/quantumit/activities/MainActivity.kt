package com.aman043358.quantumit.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.aman043358.quantumit.adapter.AuthPagerAdapter
import com.aman043358.quantumit.databinding.ActivityMainBinding
import com.aman043358.quantumit.viewModels.SharedViewModel
import com.aman043358.quantumit.utils.AuthUtils
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SharedViewModel
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (AuthUtils.isUserLoggedIn()){
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            finish()
        }

        tabLayout = binding.tabLayout
        viewPager = binding.viewPager2
        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        tabLayout.addTab(tabLayout.newTab().setText("LOGIN"))
        tabLayout.addTab(tabLayout.newTab().setText("SIGN UP"))

        viewPager.adapter = AuthPagerAdapter(supportFragmentManager, lifecycle)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewPager.currentItem = it.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        viewModel.onTabChangeListener.observe(this) { tab ->
            viewPager.currentItem = tab.position
        }
    }
}