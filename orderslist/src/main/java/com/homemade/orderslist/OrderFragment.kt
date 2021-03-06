package com.homemade.orderslist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.floriaapp.core.domain.model.provider_.orders.OrderListResponseItem
import com.floriaapp.core.ui.OrderViewModel
import com.homemade.orderslist.adapter.OrderPagedListAdapter
import com.homemade.orderslist.databinding.FragmentOrderBinding
import com.homemade.orderslist.details.OrderDetailsActivity
import com.test.utils.ADD_PRODUCT_FIRST
import com.test.utils.Bases.BaseActivity
import com.test.utils.Bases.BaseFragment
import com.test.utils.Ext.showToast
import com.test.utils.ORDER_ID
import com.test.utils.SPLASH_DELAY
import com.test.utils.STATUS_ORDER
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class OrderFragment : BaseFragment(), OrderPagedListAdapter.OnItemClickOfProduct {
    lateinit var binding: FragmentOrderBinding
    private val orderViewModel: OrderViewModel by viewModel()
    lateinit var orderPagedListAdapter: OrderPagedListAdapter
    var statusID: Int = 0

    companion object {
        fun newInstance(someInt: Int): OrderFragment {
            val myFragment = OrderFragment()
            val args = Bundle()
            args.putInt(STATUS_ORDER, someInt)
            myFragment.arguments = args
            return myFragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderPagedListAdapter = OrderPagedListAdapter(this)
        binding.rvOrders.adapter = orderPagedListAdapter

        statusID = arguments?.getInt(STATUS_ORDER, 0)!!;
        //requireActivity().showToast(statusId.toString())


        orderViewModel.SuccessOrderDone.observe(viewLifecycleOwner, Observer {
            showSuccessFavouriteMessage(it.message)
            dismissProgressDialog()
            getOrdersList()
        })

        orderViewModel.Error.observe(viewLifecycleOwner, Observer {
            dismissProgressDialog()
            requireActivity().showToast(it)
        })

        binding.btnNewOrder.setOnClickListener {
            val intent = Intent(requireContext(), Class.forName(ADD_PRODUCT_FIRST))
            context?.startActivity(intent)
        }



        orderPagedListAdapter.addLoadStateListener { loadState ->

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

    }

    override fun onResume() {
        super.onResume()
        getOrdersList()

    }

    private fun getOrdersList() {
        lifecycleScope.launch {
            orderViewModel.getOrdersLists(statusID).collect() {
                dismissProgressDialog()
                orderPagedListAdapter.submitData(it)

            }
        }
    }

    override fun onItemClicked(position: Int, item: OrderListResponseItem?) {
        val intent = Intent(activity, OrderDetailsActivity::class.java)
        intent.putExtra(ORDER_ID, item?.id)
        context?.startActivity(intent)
    }

    override fun onChangeStatusClicked(position: Int, item: OrderListResponseItem?) {
        showProgress()
        val neededStatus = (requireActivity() as BaseActivity).returnNeededChangedStatus(statusID)
        orderViewModel.changeOrderStatus(item?.id!!, neededStatus!!)
    }

}