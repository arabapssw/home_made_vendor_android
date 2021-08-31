package com.test.utils.custom

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.floriaapp.core.domain.model.provider_.order_details.OrderDetailsVendorItem
import com.test.utils.*


class CustomOrderNeeded(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    var applyText: TextView

    init {
        inflate(context, R.layout.custom_order_needed, this)
        applyText = findViewById(R.id.tv_apply)

    }

    @SuppressLint("SetTextI18n", "CutPasteId")
    fun setData(data: OrderDetailsVendorItem) {
        findViewById<TextView>(R.id.order_number).text =
            "${data.user.firstName} ${data.user.lastName} "
        findViewById<TextView>(R.id.order_date).text =
            "${data.requiredAt.date} ${data.requiredAt.time} "
        findViewById<TextView>(R.id.order_location).text = "${data.address.country.name}  "
        findViewById<TextView>(R.id.order_telephone).text = "${data.user.phone}  "
        findViewById<TextView>(R.id.order_location2).text = "${data.address.formattedAddress}  "
        findViewById<TextView>(R.id.order_payment).text = "${data.orderStatus.name}  "
//        data.paymentOptions.forEachIndexed { index, paymentOptions ->
//            findViewById<TextView>(R.id.order_payment).append(Html.fromHtml("<font color='#682300'>${paymentOptions} </font>"))
//        }
        findViewById<TextView>(R.id.order_shipment).text = "${data.shipping.name}  "
        findViewById<TextView>(R.id.order_notes).text =
            if (data.notes == "") resources.getString(R.string.no_notes) else data.notes

    }

    @SuppressLint("CutPasteId")

    fun setSpinnerData(
        data: OrderDetailsVendorItem, orderStatus: Int, function: ((String) -> Unit?)? = null,
    ) {
        val OrderStatuses = HashMap<String, Int>()
        val OrderStatusList = mutableListOf<String>()
        OrderStatuses[resources.getString(R.string.new_word)] = PENDING
        OrderStatuses[resources.getString(R.string.accept_word)] = ACCEPTED
        OrderStatuses[resources.getString(R.string.prepare_word)] = PREPARING
        OrderStatuses[resources.getString(R.string.ready_deliver)] = DELIVERING
        OrderStatuses[resources.getString(R.string.ready_ship_word)] = SHIPPING
        OrderStatuses[resources.getString(R.string.ready_done)] = DELIVERED
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            OrderStatuses.forEach { t, u ->
                if (orderStatus <= u) {
                  //  if (u == SHIPPING ) OrderStatusList.add(resources.getString(R.string.ready_deliver))
                    OrderStatusList.add(t)
                }
            }
        }
        when(data.orderStatus.id){
            DELIVERING -> OrderStatusList.remove(resources.getString(R.string.ready_ship_word))
            SHIPPING -> OrderStatusList.add(resources.getString(R.string.ready_deliver))
        }

        val spinnerAdapter: ArrayAdapter<String?> =
            object : ArrayAdapter<String?>(
                context, android.R.layout.simple_spinner_item,
                OrderStatusList as List<String?>
            ) {
                override fun getCount(): Int {
                    return OrderStatusList.size // Truncate the list
                }
            }


        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.ed_text).adapter = spinnerAdapter
        findViewById<Spinner>(R.id.ed_text)?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    //if (position != 0) {
                    val selectedItem = adapterView?.getItemAtPosition(position).toString();
                    val selectedId = OrderStatuses.get(selectedItem)
                    if (selectedId != null) {
                        function?.invoke(selectedId.toString())
                    }
                    //  }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
    }


}