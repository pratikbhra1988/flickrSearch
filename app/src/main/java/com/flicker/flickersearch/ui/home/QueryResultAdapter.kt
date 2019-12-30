package com.flicker.flickersearch.ui.home

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flicker.domain.api.SearchResponse
import com.flicker.domain.api.photo

/**
 * Created by Pratik Behera on 2019-12-30.
 */


class QueryResultAdapter(
    private val context: Context
) : RecyclerView.Adapter<QueryResultAdapter.Holder>() {
    private val items = mutableListOf<photo>()
    var onRowclicked: (photo) -> (Unit) = {}

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.apply {
            view.render(items[position])
            view.setOnClickListener {
                onRowclicked(items[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(QueryResultitemView(context))

    fun updateItems(list: List<photo>) {
        items.clear()
        items += list - items
        notifyDataSetChanged()
    }

    class Holder(val view: QueryResultitemView) : RecyclerView.ViewHolder(view)
}
