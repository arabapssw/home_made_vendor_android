package com.homemade.onboarding.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.homemade.onboarding.R

class IntroAdapter(private var ctx: Context) : PagerAdapter() {
    fun imagesArray(): MutableList<OnBoarding> {
        val imagesArray = mutableListOf<OnBoarding>()
        imagesArray.add(OnBoarding(image = R.drawable.first_onboarding,title = ctx.resources.getString(R.string.first_onboardingtitle)))
        imagesArray.add(OnBoarding(image = R.drawable.second_onboarding,title = ctx.resources.getString(R.string.second_onboardingtitle)))
        imagesArray.add(OnBoarding(image = R.drawable.third_onboarding,title = ctx.resources.getString(R.string.third_onboardingtitle)))
        return imagesArray

    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view == o
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(ctx)
        val view = layoutInflater.inflate(R.layout.slide_item, container, false)
        val slideImage = view.findViewById<ImageView>(R.id.iv)
        slideImage.setImageResource(imagesArray()[position].image)
        view.findViewById<TextView>(R.id.tv_title).text = imagesArray()[position].title
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, o: Any) {
        container.removeView(o as View?)
    }

    override fun getCount() = imagesArray().size

}
data class OnBoarding(var image:Int,var title:String)