package com.homemade.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.homemade.home.R


class MainAdapter(private val mContext: Context?) : ListAdapter<MainItem, MainAdapter.MainViewHolder>(DIFF_CALLBACK) {

    private var mainItemListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_main_list, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        if (mContext == null) {
            return
        }
        holder.title.text = getItem(position).title
        holder.tvStat.text = getItem(position).total.toString()
        holder.image.setImageResource(getItem(position).icon)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mainItemListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(mainItem: MainItem)
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title: TextView = view.findViewById(R.id.tv_title)
        val tvStat: TextView = view.findViewById(R.id.tv_stat)
        val image: ImageView = view.findViewById(R.id.iv_icon)
        private var root: MaterialCardView = itemView.findViewById(R.id.card)

        init {
            root.setOnClickListener {
                val position = adapterPosition
                if (mainItemListener != null && position != RecyclerView.NO_POSITION) {
                    mainItemListener!!.onItemClick(getItem(position))
                }
            }
        }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MainItem>() {
            override fun areItemsTheSame(oldItem: MainItem, newItem: MainItem): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: MainItem, newItem: MainItem): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }


}