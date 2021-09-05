package com.homemade.store.store_created

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.homemade.store.R
import com.homemade.store.StoreActivity
import com.homemade.store.databinding.FragmentStoreCreatedBinding

class StoreCreatedFragment : Fragment() {

    lateinit var binding: FragmentStoreCreatedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoreCreatedBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnHome.setOnClickListener {

        }
    }

}