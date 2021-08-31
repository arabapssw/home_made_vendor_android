package com.test.utils.adapter

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.floriaapp.core.domain.model.checkout.order.order_details.BankTransfer
import com.test.utils.Common.CustomDialog
import com.test.utils.R

class ReceiptAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CustomDialog.NeededReceipt>() {

        override fun areItemsTheSame(oldItem: CustomDialog.NeededReceipt, newItem: CustomDialog.NeededReceipt) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: CustomDialog.NeededReceipt, newItem: CustomDialog.NeededReceipt) =
            oldItem == newItem

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SubjectViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.reciept_layout,
                parent,
                false
            ))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SubjectViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: MutableList<CustomDialog.NeededReceipt>) {
        differ.submitList(list)
    }

    class SubjectViewHolder(
        itemView: View) : RecyclerView.ViewHolder(itemView){

        @SuppressLint("SetTextI18n")
        fun bind(data: CustomDialog.NeededReceipt) = with(itemView) {
            val textTile= findViewById<TextView>(R.id.tv_product_name)
            val textPrice= findViewById<TextView>(R.id.tv_qunatity_)

            when(data.title){
                resources.getString(R.string.discount_title) ->{
                    textTile.setTextColor(resources.getColor(R.color.teaBlue))
                    textPrice.setTextColor(resources.getColor(R.color.teaBlue))

                }
                resources.getString(R.string.total_receipt) ->{
                    textTile.setTypeface(textTile.typeface,Typeface.BOLD)
                    textPrice.setTypeface(textPrice.typeface,Typeface.BOLD)
                }
                resources.getString(R.string.price_title) ->{
                    textTile.setTypeface(textTile.typeface,Typeface.BOLD)
                    textPrice.setTypeface(textPrice.typeface,Typeface.BOLD)
                }
            }
            textTile.text =   data.title
            textPrice.text =   data.Price.toString() + "  " +  resources.getString(R.string.egp)




        }



    }


    interface OnItemClickOfProduct {
        fun onAddNoteClicked(position: Int, item: BankTransfer)
        fun onImageClicked(position: Int, item: BankTransfer)
    }
}