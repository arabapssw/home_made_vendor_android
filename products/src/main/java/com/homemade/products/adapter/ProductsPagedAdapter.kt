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
import com.test.utils.Extensions.getScreenWidth


class ProductsPagedAdapter(
    private val interaction: OnItemClickOfProduct? = null,
    private var isFeatured: Boolean? = false
) :
    PagingDataAdapter<ProviderProductsResponseItem, ProductsPagedAdapter.ViewHolder>(DataDifferntiator) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        if (isFeatured == true) holder.itemView.layoutParams.width =
//            (holder.itemView.context.getScreenWidth(holder.itemView.context) / 3) /// THIS LINE WILL DIVIDE OUR VIEW INTO NUMBERS OF PARTS

        with(holder.itemView) {
            val data = getItem(position)
            findViewById<TextView>(R.id.tv_vendor_name).text = data?.nameAr
            findViewById<TextView>(R.id.tv_price_product).text = data?.price.toString() +  " " +resources.getString(R.string.egp)
            findViewById<TextView>(R.id.tv_weight).text = data?.weight.toString()

//            tv_add_cart_title.typeface = typeface
//            tv_name.text = "${getItem(position)?.name}"
//            tv_price.text = "${getItem(position)?.price} ${resources.getString(R.string.egp)}"
//            tv_vendor_name.text = getItem(position)?.provider?.name
//            getItem(position)?.image?.let { iv_product.loadImage(it) }
//            getItem(position)?.provider?.logo?.let { iv_vendor.loadImage(it) }
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
//            if (getItem(position)?.inCart == true) {
//                closeAddToCartButton()
//            } else {
//                openAddToCartButton()
//            }
//
//            if (getItem(position)?.isFavorited == true) {
//                colorizeFAvouriteBtn()
//            } else {
//                disColorizeFAvouriteBtn()
//            }

        }


    }

//    private fun View.closeAddToCartButton() {
//        btn_add_cart_details.isClickable = false
//        btn_add_cart_details.backgroundTintList =
//            ColorStateList.valueOf(resources.getColor(R.color.gray))
//        setBackgroundColor(resources.getColor(R.color.blue_color))
//    }
//
//    private fun View.colorizeFAvouriteBtn() {
//        iv_favourite.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.teaBlue))
//    }
//
//    private fun View.disColorizeFAvouriteBtn() {
//        iv_favourite.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.colorWhite))
//    }
//
//
//    private fun View.openAddToCartButton() {
//        btn_add_cart_details.isClickable = true
//        btn_add_cart_details.backgroundTintList =
//            ColorStateList.valueOf(resources.getColor(R.color.teaBlue))
//        setBackgroundColor(resources.getColor(R.color.colorWhite))
//    }


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
        fun onItemClicked(position: Int, item: ProviderProductsResponseItem)
        fun addToCartClicked(position: Int, item: ProviderProductsResponseItem)
        fun favoriteFunction(position: Int, item: ProviderProductsResponseItem, typeOfFavourite: Int)

    }


}

