package com.homemade.store.store_data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.homemade.store.R
import com.homemade.store.StoreActivity
import com.homemade.store.databinding.StoreDataFragmentBinding

class StoreDataFragment : Fragment() {

    private lateinit var binding: StoreDataFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StoreDataFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNextAdd.setOnClickListener {
            (activity as StoreActivity).gotoNextFragment()
            findNavController().navigate(R.id.action_storeDataFragment_to_completeStoreDataFragment)
        }
    }

}