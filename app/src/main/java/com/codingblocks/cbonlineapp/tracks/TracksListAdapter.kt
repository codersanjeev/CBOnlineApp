package com.codingblocks.cbonlineapp.tracks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codingblocks.cbonlineapp.R
import com.codingblocks.cbonlineapp.course.ItemClickListener
import com.codingblocks.cbonlineapp.util.extensions.loadImage
import com.codingblocks.cbonlineapp.util.extensions.sameAndEqual
import com.codingblocks.onlineapi.models.CareerTracks
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.item_course_card.view.*
import kotlinx.android.synthetic.main.item_track.view.*
import kotlinx.android.synthetic.main.item_track.view.trackCourseNumTv
import kotlinx.android.synthetic.main.item_track.view.trackLogo
import kotlinx.android.synthetic.main.item_track.view.trackTitleTv
import kotlinx.android.synthetic.main.item_track_card.view.*

class TracksListAdapter(val type: String = "") : ListAdapter<CareerTracks, TracksListAdapter.ItemViewHolder>(DiffCallback()) {

    var onItemClick: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            when (type) {
                "LIST" -> LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_track_card, parent, false)
                else -> LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_track, parent, false)
            }
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemClickListener = onItemClick
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemClickListener: ItemClickListener? = null

        fun bind(item: CareerTracks) = with(itemView) {
            trackTitleTv.text = item.name
            trackLogo.loadImage(item.logo)
            ViewCompat.setTransitionName(trackLogo, item.name)

            trackCourseNumTv.text = "${item.courses?.size} Courses"
            if (type == "LIST") {
                trackCover.loadImage(item.background)
                item.professions?.forEach {
                    val chip = LayoutInflater.from(context).inflate(R.layout.single_chip_layout, trackChips, false) as Chip
                    chip.text = it.title
                    trackChips.addView(chip)
                }
            }
            setOnClickListener {
                itemClickListener?.onClick(
                    item.id, item.logo, trackLogo
                )
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CareerTracks>() {
        override fun areItemsTheSame(oldItem: CareerTracks, newItem: CareerTracks): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CareerTracks, newItem: CareerTracks): Boolean {
            return oldItem.sameAndEqual(newItem)
        }
    }
}
