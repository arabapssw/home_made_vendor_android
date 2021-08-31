package com.homemade.orderslist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.floriaapp.core.domain.model.provider_.order_details.Product
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.homemade.orderslist.R
import com.test.utils.Ext.loadImage

class OrderDetailsAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val HEADER_ITEM = 0
    private val NORMAL_ITEM = 1

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {

        override fun areItemsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean =
            oldItem == newItem

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean =
            oldItem == newItem

    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            NORMAL_ITEM ->
                DaysRecyclerViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.normal_product,
                        parent,
                        false
                    ),
                    interaction
                )
            HEADER_ITEM -> HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.header_product,
                    parent,
                    false
                ),
                interaction
            )
            else ->
                HeaderViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.header_product,
                        parent,
                        false
                    ),
                    interaction
                )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HEADER_ITEM
            else -> NORMAL_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DaysRecyclerViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
            is HeaderViewHolder -> {
                holder.bind()
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: MutableList<com.floriaapp.core.domain.model.provider_.order_details.Product>) {
        differ.submitList(list)
    }

    class DaysRecyclerViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Product) = with(itemView) {
            itemView.setOnClickListener {
                //interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.findViewById<ImageView>(R.id.tv_image).loadImage(item.image)
            itemView.findViewById<ShapeableImageView>(R.id.tv_image).shapeAppearanceModel =
                itemView.findViewById<ShapeableImageView>(R.id.tv_image).shapeAppearanceModel
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED,10f)
                    .build();
            itemView.findViewById<TextView>(R.id.tv_product_name).text = item.name
          //  itemView.findViewById<TextView>(R.id.tv_qunatity_).text = item.quantity.toString()
            itemView.findViewById<TextView>(R.id.tv_price).text =
                item.price.toString() + " " + resources.getString(R.string.egp)


        }
    }

    class HeaderViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind() = with(itemView) {

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Product)
        fun onLocationItemSelected(position: Int, item: Product)
    }
}