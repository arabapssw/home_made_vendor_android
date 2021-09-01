package com.homemade.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.homemade.adapters.MainAdapter
import com.homemade.adapters.MainItem
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MainAdapter(activity)
        gv_actions.layoutManager = GridLayoutManager(activity, 3)
        gv_actions.adapter = adapter
        adapter.submitList(getItems())
        adapter.setOnItemClickListener(object :MainAdapter.OnItemClickListener{
            override fun onItemClick(mainItem: MainItem) {

            }

        })
    }

    private fun getItems(): ArrayList<MainItem> {
     val items =    ArrayList<MainItem>()
        items.add(MainItem(R.drawable.user_perspective,1282 ,getString(R.string.client)))
        items.add(MainItem(R.drawable.chart_perspective,5750,getString(R.string.sales)))
        items.add(MainItem(R.drawable.check_perspective,1105,getString(R.string.complete_orders)))
        items.add(MainItem(R.drawable.credit_card_perspective,1282 ,getString(R.string.credit_card)))
        items.add(MainItem(R.drawable.browser_perspective,1742 ,getString(R.string.bank_transfer)))
        items.add(MainItem(R.drawable.coin_perspective,3465 ,getString(R.string.cash)))
        return items
    }
}