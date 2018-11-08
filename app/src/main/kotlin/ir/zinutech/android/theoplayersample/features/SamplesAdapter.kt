package ir.zinutech.android.theoplayersample.features

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.zinutech.android.theoplayersample.R
import ir.zinutech.android.theoplayersample.core.extensions.inflate
import ir.zinutech.android.theoplayersample.features.SamplesAdapter.ViewHolder
import kotlinx.android.synthetic.main.item_sample_layout.view.sample_item_title_tv
import kotlinx.android.synthetic.main.item_sample_layout.view.sample_item_url_tv

class SamplesAdapter(val data: List<PlaybackSource>, val onItemClickListener: (View) -> Unit) : RecyclerView.Adapter<ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
      parent.inflate(R.layout.item_sample_layout).apply {
        setOnClickListener {
          onItemClickListener(it)
        }
      }
  )

  override fun getItemCount(): Int = data.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(data[position])
  }

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: PlaybackSource) {
      itemView.sample_item_title_tv.text = item.title
      itemView.sample_item_url_tv.text = item.playUrl
    }
  }
}