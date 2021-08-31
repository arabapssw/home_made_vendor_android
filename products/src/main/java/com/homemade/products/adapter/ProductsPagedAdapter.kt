package com.homemade.products.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.floriaapp.core.domain.model.category.categoryProductItem
import com.floriaapp.core.domain.model.provider_.productsVendor.ProviderProductsResponseItem
import com.homemade.products.R
import com.test.utils.ACTIVE
import com.test.utils.Extensions.getScreenWidth
import com.test.utils.INACTIVE
import com.test.utils.PRODUCT_PIN
import com.test.utils.PRODUCT_STATUS


class ProductsPagedAdapter(
    private val interaction: OnItemClickOfProduct? = null,
    private var isFeatured: Boolean? = false
) :
    PagingDataAdapter<ProviderProductsResponseItem, ProductsPagedAdapter.ViewHolder>(
        DataDifferntiator
    ) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder.itemView) {
            val data = getItem(position)
            findViewById<TextView>(R.id.tv_vendor_name).text = data?.nameAr
            findViewById<TextView>(R.id.tv_price_product).text =
                data?.price.toString() + " " + resources.getString(R.string.egp)
            findViewById<TextView>(R.id.tv_weight).text = data?.weight.toString()

            findViewById<TextView>(R.id.tv_disappear).setOnClickListener {
                interaction?.onItemClicked(position,data!!,PRODUCT_STATUS)
            }

            findViewById<TextView>(R.id.tv_tasbet).setOnClickListener {
                interaction?.onItemClicked(position,data!!, PRODUCT_PIN)
            }

            when (data?.active) {
                ACTIVE -> setActiveStatus()
                INACTIVE -> setInActiveStatus()
            }
            when (data?.pinned) {
                ACTIVE -> setActivePinned()
                INACTIVE ->setInActivePinned()
            }

//            if (getItem(position) !=null) {
//                btn_add_cart_details.setOnClickListener {
//                    getItem(position)?.let { it1 -> interaction?.addToCartClicked(position, it1) }
//                    if (context.isUserVerifed()) {
//                        getItem(position)?.inCart = true
//                        notifyItemChanged(position)
//                    }
//                }
//                iv_favourite.setOnClickListener {
//                    if ((context as BaseActivity).showNotAuthorizedUser(function = { (context as BaseActivity).navigateToLogin() })) return@setOnClickListener
//
//                    if (context.isUserVerifed()) {
//                        if (getItem(position)?.isFavorited == true) {
//                            getItem(position)?.isFavorited = false
//                            getItem(position)?.let { it1 ->
//                                interaction?.favoriteFunction(
//                                    position,
//                                    it1, UNFAVOURITES
//                                )
//                            }
//                        } else {
//                            getItem(position)?.isFavorited = true
//                            getItem(position)?.let { it1 ->
//                                interaction?.favoriteFunction(
//                                    position,
//                                    it1, FAVOURITES
//                                )
//                            }
//                        }
//                        notifyItemChanged(position)
//                    }
//                }
//                setOnClickListener {
//                    getItem(position)?.let { it1 -> interaction?.onItemClicked(position, it1) }
//                }
//            }
//


        }


    }

    private fun View.setActivePinned() {
        findViewById<TextView>(R.id.tv_tasbet).text = resources.getString(R.string.stable)
        findViewById<TextView>(R.id.tv_tasbet).setTextColor(resources.getColor(R.color.teaBlue))
        findViewById<TextView>(R.id.tv_tasbet).setCompoundDrawablesRelativeWithIntrinsicBounds(
            R.drawable.ic_sabet,
            0,
            0,
            0
        )
    }
    private fun View.setActiveStatus() {
        findViewById<TextView>(R.id.tv_disappear).text = resources.getString(R.string.disappeard)
        findViewById<TextView>(R.id.tv_disappear).setTextColor(resources.getColor(R.color.teaBlue))
        findViewById<TextView>(R.id.tv_disappear).setCompoundDrawablesRelativeWithIntrinsicBounds(
            R.drawable.ic_icon_eye,
            0,
            0,
            0
        )
    }

    private fun View.setInActiveStatus() {
        findViewById<TextView>(R.id.tv_disappear).text = resources.getString(R.string.disappear)
        findViewById<TextView>(R.id.tv_disappear).setTextColor(resources.getColor(R.color.black41))
        findViewById<TextView>(R.id.tv_disappear).setCompoundDrawablesRelativeWithIntrinsicBounds(
            R.drawable.ic_gray_eye,
            0,
            0,
            0
        )
    }
    private fun View.setInActivePinned() {
        findViewById<TextView>(R.id.tv_tasbet).text = resources.getString(R.string.tasbet)
        findViewById<TextView>(R.id.tv_tasbet).setTextColor(resources.getColor(R.color.black41))
        findViewById<TextView>(R.id.tv_tasbet).setCompoundDrawablesRelativeWithIntrinsicBounds(
            R.drawable.ic_not_sabet,
            0,
            0,
            0
        )
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.category_details_item, parent, false)
        )
    }


    object DataDifferntiator : DiffUtil.ItemCallback<ProviderProductsResponseItem>() {

        override fun areItemsTheSame(
            oldItem: ProviderProductsResponseItem,
            newItem: ProviderProductsResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ProviderProductsResponseItem,
            newItem: ProviderProductsResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickOfProduct {
        fun onItemClicked(position: Int, item: ProviderProductsResponseItem,typeChoosen:Int)

    }


}

