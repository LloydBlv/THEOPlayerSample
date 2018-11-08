package ir.zinutech.android.theoplayersample.features

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.zinutech.android.theoplayersample.R
import ir.zinutech.android.theoplayersample.core.extensions.inflate


class SamplesPagerAdapter(private val samplesGroups: List<SampleGroup>): PagerAdapter() {


  override fun instantiateItem(container: ViewGroup, position: Int): Any {
    val layout = container.inflate(R.layout.view_samples_list).apply {
      this as RecyclerView
      val data = samplesGroups[position].samplesList
      adapter = SamplesAdapter(data){
        getChildAdapterPosition(it).takeIf { it >= 0 }?.let { position ->
          context.startActivity(
              PlayerActivity.starter(
                  context,
                  data[position]
              )
          )

        }
      }
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(context)
      addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
    }
    container.addView(layout)
    return layout
  }

  override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
    container.removeView(view as View)
  }


  override fun getPageTitle(position: Int): CharSequence? = samplesGroups[position].title
  override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`
  override fun getCount(): Int = samplesGroups.size

}