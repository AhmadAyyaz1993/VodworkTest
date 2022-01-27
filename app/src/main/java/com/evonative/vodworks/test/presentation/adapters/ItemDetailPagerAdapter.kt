package com.evonative.vodworks.test.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.bumptech.glide.Glide
import com.evonative.vodworks.test.VodWorksApplication
import com.evonative.vodworks.test.data.model.CardImages
import com.evonative.vodworks.test.data.model.ItemData
import evonative.hilt.coroutines.retrofit.example.R

class ItemDetailPagerAdapter(
    itemList: List<CardImages>,
    isInfinite: Boolean
) : LoopingPagerAdapter<CardImages>(itemList, isInfinite) {

    override fun getCount(): Int {
        return itemList!!.size
    }

    override fun inflateView(
        viewType: Int,
        container: ViewGroup,
        listPosition: Int
    ): View {
        return LayoutInflater.from(
            container.context
        ).inflate(R.layout.item_view_pager, container, false)
    }

    override fun bindView(
        convertView: View,
        listPosition: Int,
        viewType: Int
    ) {
        val image = convertView.findViewById<ImageView>(R.id.imgImage)
        VodWorksApplication.getContext()?.let {
            Glide.with(it)
                .load(itemList?.get(listPosition)!!.url)
                .dontAnimate()
                .into(image)
        }
    }



}