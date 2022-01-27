package com.evonative.vodworks.test.presentation.adapters

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evonative.vodworks.test.data.model.ItemData
import com.evonative.vodworks.test.others.Utils
import evonative.hilt.coroutines.retrofit.example.R
import evonative.hilt.coroutines.retrofit.example.databinding.RowLayoutBinding
import java.lang.ref.WeakReference


class HomeActivityListAdapter(val items : List<ItemData>, val context: Context, val listener: HomeActivityListItemClickListener) : RecyclerView.Adapter<ViewHolder>() {

    // Gets the number of countries in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false), listener)
    }

    // Binds each country in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bind(items[position], context, position)

        holder.itemView.setOnClickListener(View.OnClickListener {
            listener.onPositionClicked(position);
        })

    }
}

class ViewHolder (view: View, val listener: HomeActivityListItemClickListener) : RecyclerView.ViewHolder(view) {
    private val binding = RowLayoutBinding.bind(view)

    fun bind(data: ItemData, context: Context, position: Int) {
        with(binding) {
            title.text = data.headline
            tvDescription.text = data.synopsis

            for (image in data.keyArtImages){
                val iv = ImageView(context)
                iv.scaleType = ImageView.ScaleType.CENTER_CROP

                Glide.with(context.applicationContext)
                    .load(image.url)
                    .dontAnimate()
                    .into(iv)
                System.gc()
                var lp: LinearLayout.LayoutParams? = null

                lp = LinearLayout.LayoutParams(
                    Utils.convertDpToPixel(100),
                    Utils.convertDpToPixel(100)
                )
                lp.setMargins(30, 0, 0, 0)
                lp.gravity = Gravity.CENTER
                iv.layoutParams = lp
                iv.setOnClickListener(View.OnClickListener {
                    listener.onPositionClicked(position);
                })
                title.setOnClickListener(View.OnClickListener {
                    listener.onPositionClicked(position);
                })
                tvDescription.setOnClickListener(View.OnClickListener {
                    listener.onPositionClicked(position);
                })

                imagesContainer.addView(iv)
            }

        }
    }
}