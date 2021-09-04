package com.homemade.products

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.floriaapp.core.domain.model.FilterClass.Companion.filterData
import com.floriaapp.core.domain.model.provider_.productsVendor.ProviderProductsResponseItem
import com.floriaapp.core.ui.ProductsViewModel
import com.homemade.products.adapter.ProductsPagedAdapter
import com.homemade.products.databinding.FragmentProductsBinding
import com.test.utils.Bases.BaseFragment
import com.test.utils.Bases.Communication
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class productsFragment : BaseFragment(), ProductsPagedAdapter.OnItemClickOfProduct {

    var allProductsAdapter = ProductsPagedAdapter(this)
    lateinit var communication: Communication


    lateinit var binding: FragmentProductsBinding
    private val productsViewModel: ProductsViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProductsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filterData.isFilterd =false


        initAdapter()

        allProductsAdapter.addLoadStateListener { loadState ->

            // getting the error
            val error = when {
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            when(loadState.refresh is LoadState.Loading) {
                true -> showProgress()
                //false-> dismissProgressDialog()

            }
            when(loadState.source.refresh is LoadState.NotLoading) {
                true -> dismissProgressDialog()
                //false-> dismissProgressDialog()

            }



            error.let {
                // Toast.makeText(requireContext(), it?.error.toString(), Toast.LENGTH_LONG).show()
            }
        }



        productsViewModel.SuccessMessage.observe(viewLifecycleOwner, Observer {
           // dismissProgressDialog()
            launchProducts()
        })
    }

    private fun launchProducts() {
        with(lifecycleScope) {
          //  allProductsAdapter.submitData(lifecycle, PagingData.empty())
                launch {
                    productsViewModel.getAllProducts().collect() {
                        Log.i("data", it.toString())
                        allProductsAdapter.submitData(it)



                    }


                }

        }
    }

    private fun initAdapter() {
        binding.rvAllProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvAllProducts.adapter = allProductsAdapter
    }

    override fun onResume() {
        super.onResume()
        launchProducts()
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        communication = context as Communication
    }

    override fun onItemClicked(
        position: Int,
        item: ProviderProductsResponseItem,
        typeChoosen: Int
    ) {
       // showProgress()
        productsViewModel.toggleProductPinnedOrStatus(item.id,typeChoosen)
    }

    override fun onMoreClicked(item: ProviderProductsResponseItem, location: IntArray) {
        showMoreDialog(location,productsViewModel,item.id)
    }


}