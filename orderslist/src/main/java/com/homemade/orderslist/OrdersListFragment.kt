package com.homemade.orderslist

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.homemade.orderslist.databinding.FragmentOrdersListBinding
import com.test.utils.*
import com.test.utils.Bases.BaseFragment


class OrdersListFragment : BaseFragment() {

    lateinit var binding:FragmentOrdersListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrdersListBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(@NonNull view: View, @Nullable savedInstanceState: Bundle?) {
        initViewPager()

    }


    private fun initViewPager() {
        binding.pager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> OrderFragment.newInstance(PENDING)
                    1 -> OrderFragment.newInstance(ACCEPTED)
                    2 -> OrderFragment.newInstance(PREPARING)
                    3 -> OrderFragment.newInstance(SHIPPING)
                    4 -> OrderFragment.newInstance(DELIVERING)
                    5 -> OrderFragment.newInstance(DELIVERED)
                    else -> {
                        OrderFragment()
                    }
                }
            }

            override fun getItemCount(): Int {
                return 6
            }
        }
        binding.pager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = when (position) {
                0 ->resources.getString(com.homemade.orderslist.R.string.new_word)
                1 ->resources.getString(com.homemade.orderslist.R.string.accept_word)
                2 ->resources.getString(com.homemade.orderslist.R.string.prepare_word)
                3 ->resources.getString(com.homemade.orderslist.R.string.ready_ship_word)
                4 ->resources.getString(com.homemade.orderslist.R.string.ready_deliver)
                5 ->resources.getString(com.homemade.orderslist.R.string.ready_done)
                else -> "New"
            }
        }.attach()
    }



}