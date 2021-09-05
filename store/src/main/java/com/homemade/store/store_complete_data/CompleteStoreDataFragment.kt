package com.homemade.store.store_complete_data

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.homemade.store.R
import com.homemade.store.StoreActivity
import com.homemade.store.databinding.CompleteStoreDataFragmentBinding

class CompleteStoreDataFragment : Fragment() {

    lateinit var binding: CompleteStoreDataFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CompleteStoreDataFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNextAdd.setOnClickListener {
            (activity as StoreActivity).gotoNextFragment()
            findNavController().navigate(R.id.action_completeStoreDataFragment_to_storePaymentFragment)
        }
    }

}