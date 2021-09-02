package com.homemade.bills

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.homemade.bills.databinding.FragmentBillsBinding


class BillsFragment : Fragment() {
    lateinit var binding: FragmentBillsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBillsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

}