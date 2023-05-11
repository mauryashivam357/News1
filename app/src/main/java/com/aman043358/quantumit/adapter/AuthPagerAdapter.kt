package com.aman043358.quantumit.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aman043358.quantumit.fragment.LoginFragment
import com.aman043358.quantumit.fragment.SignUpFragment

class AuthPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LoginFragment()
            else -> SignUpFragment()
        }
    }

    override fun getItemCount() = 2
}