package com.homemade.products

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
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
//    private val cartViewModel: CartViewModel by viewModel()


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


//        binding.navIcon.setOnClickListener {
//            communication.openDrawer()
//        }
//        binding.customSearch.filterIcon.setOnClickListener {
//            NavigationUtils.goToDestination2(requireContext(), FilterActivity::class.java)
//        }
//        binding.customSearch.editText.isFocusable = false
//        binding.customSearch.editText.keyListener = null
//
//        binding.customSearch.editText.setOnClickListener {
//
//            val intent = Intent(requireActivity(), SearchActivity::class.java)
//            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                requireActivity(),
//                binding.customSearch.editText,
//                "edit"
//            )
//            startActivity(intent, options.toBundle())
//        }


//        cartViewModel.CartMethods.observe(viewLifecycleOwner, Observer {
//            //launchProducts()
//            dismissProgressDialog()
//            it.cartCount?.let {
//                (requireActivity() as BaseActivity).getSharedPrefrenceEdit()
//                    .putInt(CART_COUNTER, it).commit()
//            }
//            communication.invokeCartCounter()
//            showSuccessDialog {
//                communication.goToCartTab()
//            }
//
//
//        })

        allProductsAdapter.addLoadStateListener { loadState ->

            // getting the error
            val error = when {
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            error.let {
              //  Toast.makeText(requireContext(), it?.error.toString(), Toast.LENGTH_LONG).show()
            }
        }
//        cartViewModel.Error.observe(viewLifecycleOwner, Observer {
//            dismissProgressDialog()
//            if (it== resources.getString(com.test.utils.R.string.unauthenticated_string)){
//                showAuthenticationDialog(function = {
//                    NavigationUtils.goToDestinationWithClearTasks(requireContext(), Class.forName(
//                        LOGIN_CLASS_NAME
//                    ))
//                })
//            }else showErrorDialog(it)
//        })


//        categoryViewModel.FavouriteResponse.observe(this, Observer {
//            //  showToast(it.data.message)
//            showSuccessFavouriteMessage(it.data.message)
//        })

    }

    private fun launchProducts() {
        with(lifecycleScope) {
            allProductsAdapter.submitData(lifecycle, PagingData.empty())

//            if (filterData.isFilterd == true) {
//                Log.i("data", FilterClass.filterData.toString())
//                launch {
//                    categoryViewModel.filterProducts(filter = FilterData(filterData.min_price,
//                        filterData.max_price, filterData.categoriesList)).collect() {
//                         allProductsAdapter.submitData(it)
//                       // Log.i("data", it.toString())
//
//
//                    }
//                }
//            } else {
                launch {
                    productsViewModel.getAllProducts().collect() {
                        Log.i("data", it.toString())
                        allProductsAdapter.submitData(it)
                    }
                }
      //      }


           // allProductsAdapter.notifyDataSetChanged()
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

//    override fun onItemClicked(position: Int, item: categoryProductItem) {
//
//        communication.goToProductDetails(item.id)
//
//    }
//
//    override fun addToCartClicked(position: Int, item: categoryProductItem) {
//        showProgress()
//        cartViewModel.addToCart(item.id, 1)
//
//    }
//
//    override fun favoriteFunction(position: Int, item: categoryProductItem, typeOfFavourite: Int) {
//        categoryViewModel.favouriteAction(item.id, typeOfFavourite)
//    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        communication = context as Communication
    }

    override fun onItemClicked(position: Int, item: ProviderProductsResponseItem) {

    }

    override fun addToCartClicked(position: Int, item: ProviderProductsResponseItem) {
    }

    override fun favoriteFunction(position: Int, item: ProviderProductsResponseItem, typeOfFavourite: Int) {
    }

}