package com.homemade.store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.homemade.store.databinding.ActivityStoreBinding
import com.homemade.store.databinding.StoreDataFragmentBinding
import com.test.utils.Bases.BaseActivity

class StoreActivity : BaseActivity() {
    lateinit var binding: ActivityStoreBinding
    var step = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageButton.setOnClickListener {
            if(step>1) step--
            binding.storeSteps.setStep(step)
            onBackPressed()
        }
    }
    fun gotoNextFragment(){
        step++
        binding.storeSteps.setStep(step)
    }

}