package ir.zinutech.android.theoplayersample.features

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ir.zinutech.android.theoplayersample.R.layout
import ir.zinutech.android.theoplayersample.core.utils.Constants
import ir.zinutech.android.theoplayersample.core.utils.XmlParser
import kotlinx.android.synthetic.main.activity_main.main_activity_view_pager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SampleListActivity : AppCompatActivity() {

  private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Toast.makeText(this@SampleListActivity, throwable.message, Toast.LENGTH_LONG).show()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)

    GlobalScope.launch(Dispatchers.Main + exceptionHandler) {


      val samplesInputStream = assets.open(Constants.SAMPLES_FILE_NAME)
      val samples = async(Dispatchers.IO) {
        XmlParser.getSamples(samplesInputStream)
      }.await()

      main_activity_view_pager.adapter = SamplesPagerAdapter(samples)

    }

  }
}
