package com.kominfo.kalenderevent.ui.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.kominfo.kalenderevent.R
import com.kominfo.kalenderevent.presentation.detail.DetailActivity
import com.kominfo.kalenderevent.response.ResponseItem


class EventAdapter(private val responseList: MutableList<ResponseItem>) :
    RecyclerView.Adapter<EventAdapter.ResponseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return ResponseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ResponseViewHolder, position: Int) {
        val currentItem = responseList[position]

        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_RESPONSE_ITEM, currentItem)
            intent.putExtra(DetailActivity.EXTRA_DESCRIPTION, holder.deskripsi)
            intent.putStringArrayListExtra(DetailActivity.EXTRA_MAPS, ArrayList(holder.maps.orEmpty()))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return responseList.size
    }

    inner class ResponseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recImage: ShapeableImageView = itemView.findViewById(R.id.recImage)
        val recTitle: MaterialTextView = itemView.findViewById(R.id.recTitle)
        val recDate: MaterialTextView = itemView.findViewById(R.id.recDate)
        val recTime: MaterialTextView = itemView.findViewById(R.id.recTime)
        val recMaps: MaterialTextView = itemView.findViewById(R.id.recMaps)
        var maps: List<String?>? = null
        var deskripsi: String? = null

        fun bind(responseItem: ResponseItem) {
            recTitle.text = responseItem.nama
            recDate.text = responseItem.getDateAsString()
            recTime.text = responseItem.getTimeAsString()
            val firstLocation = responseItem.lokasi?.get(0)
            recMaps.text = firstLocation?.toString() ?: "No Location"

            Glide.with(itemView.context)
                .load(responseItem.url)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(recImage)

            deskripsi = responseItem.deskripsi
            maps = responseItem.maps
        }
    }

}
