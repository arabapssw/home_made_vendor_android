package com.homemade.store.payment_method

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.homemade.store.R
import com.homemade.store.StoreActivity
import com.homemade.store.databinding.FragmentStorePaymentBinding
import com.homemade.store.databinding.StoreDataFragmentBinding


class StorePaymentFragment : Fragment() {
    lateinit var binding: FragmentStorePaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStorePaymentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNextAdd.setOnClickListener {
            (activity as StoreActivity).gotoNextFragment()
            findNavController().navigate(R.id.action_storePaymentFragment_to_storeCreatedFragment)
        }
    }


}