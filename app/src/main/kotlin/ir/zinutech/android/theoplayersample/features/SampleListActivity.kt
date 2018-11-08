package ir.zinutech.android.theoplayersample.features

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ir.zinutech.android.theoplayersample.R.layout
import ir.zinutech.android.theoplayersample.core.utils.Constants
import ir.zinutech.android.theoplayersample.core.utils.XmlParser
import kotlinx.android.synthetic.main.activity_samples_list.main_activity_progressbar
import kotlinx.android.synthetic.main.activity_samples_list.main_activity_view_pager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SampleListActivity : AppCompatActivity() {

  private var samplesLoadJob: Job? = null

  private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Toast.makeText(this@SampleListActivity, throwable.message, Toast.LENGTH_LONG).show()
    main_activity_progressbar.visibility = View.GONE
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_samples_list)

    samplesLoadJob = GlobalScope.launch(Dispatchers.Main + exceptionHandler) {


      val samplesInputStream = assets.open(Constants.SAMPLES_FILE_NAME)
      val samples = async(Dispatchers.IO) {

        /* added to see how progressbar behaves on slower devices*/
        delay(2_500)

        XmlParser.getSamples(samplesInputStream)
      }.await()

      main_activity_progressbar.visibility = View.GONE
      main_activity_view_pager.adapter = SamplesPagerAdapter(samples)

    }

  }

  override fun onDestroy() {
    super.onDestroy()
    samplesLoadJob?.cancel()
  }
}
