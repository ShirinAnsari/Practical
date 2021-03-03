package com.shirinansaripractical.view.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shirinansaripractical.R
import com.shirinansaripractical.enum.AdapterType
import com.shirinansaripractical.model.UserResponse
import java.util.*


class UserAdapter(private val listData: ArrayList<UserResponse.UserItem?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == AdapterType.PROGRESS.type) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val listItem: View = layoutInflater.inflate(R.layout.layout_progress, parent, false)
            ProgressHolder(listItem)
        } else {
            val layoutInflater = LayoutInflater.from(parent.context)
            val listItem: View = layoutInflater.inflate(R.layout.list_item_user, parent, false)
            ViewHolder(listItem)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {

            holder.tvUserName.text = holder.itemView.context.getString(
                R.string.str_user_name,
                listData[position]!!.name!!.first,
                listData[position]!!.name!!.last
            )
            holder.tvUserEmail.text = listData[position]!!.email
            holder.tvUserPhone.text = listData[position]!!.phone

            //Location
            holder.tvUserLocation.text = holder.itemView.context.getString(
                R.string.str_user_location, listData[position]!!.location!!.city,
                listData[position]!!.location!!.state, listData[position]!!.location!!.country
            )

            if (listData[position]!!.location!!.coordinates != null && listData[position]!!.location!!.coordinates!!.latitude != null) {
                holder.tvUserLocation.setOnClickListener {
                    val strUri =
                        "http://maps.google.com/maps?q=loc:" +
                                listData[position]!!.location!!.coordinates!!.latitude!!.toString() + "," +
                                listData[position]!!.location!!.coordinates!!.longitude!!.toString() +
                                " (" + listData[position]!!.location!!.city.toString() + ")"
                    val intent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(strUri))
                    intent.setClassName(
                        "com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity"
                    )
                    holder.itemView.context.startActivity(intent)
                }
            }

            //Image
            if (listData[position]!!.picture != null && listData[position]!!.picture!!.medium != null) {
                Glide.with(holder.itemView.context)
                    .load(listData[position]!!.picture!!.medium)
                    .circleCrop()
                    .into(holder.ivUser)
            }
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (listData.size == 1 && listData[position] == null) {
            AdapterType.PROGRESS.type
        } else {
            AdapterType.FILL_VIEW.type
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivUser: ImageView = itemView.findViewById(R.id.ivUser)
        var tvUserName: TextView = itemView.findViewById(R.id.tvUserName)
        var tvUserEmail: TextView = itemView.findViewById(R.id.tvUserEmail)
        var tvUserPhone: TextView = itemView.findViewById(R.id.tvUserPhone)
        var tvUserLocation: TextView = itemView.findViewById(R.id.tvUserLocation)
    }

    class ProgressHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}