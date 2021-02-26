package com.zalocoders.animalfeedcalculator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView

typealias checkClick = (feed: Feed, check: Boolean) -> Unit

class FeedAdapter(
    private val feedList: List<Feed>,
    private val context: Context,
    private val checkClick: checkClick
) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.check_layout, parent, false)
        return FeedViewHolder(view, checkClick)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val feed = feedList.get(position)
        holder.bind(feed)
    }

    override fun getItemCount(): Int = feedList.size

    inner class FeedViewHolder(
        private val view: View,
        val checkClick: checkClick
    ) : RecyclerView.ViewHolder(view) {

        fun bind(feed: Feed) = with(view) {
            var checked = false
            val check = view.findViewById<CheckBox>(R.id.checkbox)
            check.text = feed.name

            check.setOnClickListener {
                if (check.isChecked) {
                    checkClick(feed, true)
                } else {
                    checkClick(feed, false)
                }

            }
        }

    }
}