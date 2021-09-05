package com.homemade.orderslist.details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Observer
import com.floriaapp.core.domain.model.provider_.order_details.Product
import com.floriaapp.core.ui.OrderViewModel
import com.homemade.orderslist.adapter.OrderDetailsAdapter
import com.homemade.orderslist.databinding.ActivityOrderDetailsBinding
import com.test.utils.Bases.BaseActivity
import com.test.utils.Ext.showToast
import com.test.utils.ORDER_ID
import com.test.utils.R
import org.koin.android.viewmodel.ext.android.viewModel

class OrderDetailsActivity : BaseActivity() {
    lateinit var binding: ActivityOrderDetailsBinding
    lateinit var orderDetailsAdapter: OrderDetailsAdapter

    private val orderViewModel: OrderViewModel by viewModel()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orderDetailsAdapter = OrderDetailsAdapter()
        binding.rvOrderProducts.adapter = orderDetailsAdapter


        val ordrId = intent.extras?.getInt(ORDER_ID, 0)
        showProgress()
        orderViewModel.getOrderDetails(ordrId!!)
        orderViewModel.OrdersList.observe(this, Observer { dataOrder ->
            binding.toolbar.setTitleText("#${dataOrder.data.id}")
            val list = mutableListOf<Product>()
            list.add(Product(1, "", "", 1, 1, 1))
            list.addAll(dataOrder.data.products)
            orderDetailsAdapter.submitList(list)
            binding.customOrder.setData(dataOrder.data)
            binding.customOrder.setSpinnerData(
                dataOrder.data,
                dataOrder.data.orderStatus.id
            ) { selectedStatus ->
                binding.customOrder.applyText.setOnClickListener {
                    if (dataOrder.data.orderStatus.id != selectedStatus.toInt()) {
                        orderViewModel.changeOrderStatus(dataOrder.data.id, selectedStatus.toInt())
                        showProgress()
                    }

                }

            }
            binding.tvTotalCostValue.text =
                dataOrder.data.total.toString() + " " + resources.getString(R.string.egp)



            dismissProgressDialog()

            binding.btnShowReceipt.setOnClickListener {
                showReceiptDialog(dataOrder.data)

            }
        })



        orderViewModel.SuccessOrderDone.observe(this, Observer {
            dismissProgressDialog()
            showSuccessFavouriteMessage(it.message)
            //  finish()
        })

        orderViewModel.Error.observe(this, Observer {
            dismissProgressDialog()
            //  finish()
            showToast(it)
        })


    }
}