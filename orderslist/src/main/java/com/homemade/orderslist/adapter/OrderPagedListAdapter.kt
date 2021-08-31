package com.homemade.orderslist.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.floriaapp.core.domain.model.provider_.orders.OrderListResponseItem
import com.homemade.orderslist.R
import com.test.utils.*
import com.test.utils.Ext.loadImage
import kotlinx.android.synthetic.main.order_item.view.*

class OrderPagedListAdapter(private val interaction: OnItemClickOfProduct? = null) :
    PagingDataAdapter<OrderListResponseItem, OrderPagedListAdapter.ViewHolder>(DataDifferntiator) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {

            val data = getItem(position)
            setOnClickListener {
                interaction?.onItemClicked(position, data)
            }
            tv_ordeR_number.text = "#${data?.id.toString()}"
            tv_order_date.text = data?.createdAt?.time + " " + data?.createdAt?.date
            tv_user_name.text = data?.user?.firstName + " " + data?.user?.lastName
            total_cost.text = data?.total.toString() + " " + resources.getString(R.string.egp)
            iv_user.loadImage(data?.user?.avatar)
            tv_type_payment.text = ""
            data?.paymentOptions?.forEachIndexed { index, paymentOptionX ->
                tv_type_payment.append(paymentOptionX.name +" ")

            }
            when(data?.paymentOption?.key){
                "not_paid" ->{
                    tv_order_status.setTextColor(resources.getColor((R.color.orangeBold)))
                    tv_order_status.backgroundTintList = ColorStateList.valueOf(resources.getColor((R.color.orange)))

                }
            }

            when (data?.orderStatus?.id) {
                PENDING -> {
                    btn_accept.text = resources.getString(R.string.accepting)
                }
                ACCEPTED -> btn_accept.text = resources.getString(R.string.prepareing_word)
                PREPARING -> {
                    btn_accept.text = resources.getString(R.string.ship)
                    btn_cancel_order.visibility = View.GONE

                }
                SHIPPING -> {
                    btn_accept.text = resources.getString(R.string.delivering)
                    btn_cancel_order.visibility = View.GONE
                }
                DELIVERING -> {
                    btn_accept.text = resources.getString(R.string.done_deliverd)
                    btn_cancel_order.visibility = View.GONE

                }
                DELIVERED -> {
                    btn_accept.visibility = View.GONE
                    btn_cancel_order.visibility = View.GONE
                }
            }
            btn_accept.setOnClickListener {
                interaction?.onChangeStatusClicked(position, data)
            }
            tv_order_status.text = data?.paymentOption?.status


        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(com.homemade.orderslist.R.layout.order_item, parent, false)
        )
    }

    object DataDifferntiator : DiffUtil.ItemCallback<OrderListResponseItem>() {

        override fun areItemsTheSame(
            oldItem: OrderListResponseItem,
            newItem: OrderListResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: OrderListResponseItem,
            newItem: OrderListResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickOfProduct {
        fun onItemClicked(position: Int, item: OrderListResponseItem?)
        fun onChangeStatusClicked(position: Int, item: OrderListResponseItem?)

    }

}

