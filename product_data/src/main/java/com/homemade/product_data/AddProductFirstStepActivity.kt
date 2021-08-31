package com.homemade.product_data

import android.os.Bundle
import com.homemade.product_data.databinding.ActivityAddProductFirstStepBinding
import com.test.utils.Bases.BaseActivity

class AddProductFirstStepActivity : BaseActivity() {
    lateinit var binding:ActivityAddProductFirstStepBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductFirstStepBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}