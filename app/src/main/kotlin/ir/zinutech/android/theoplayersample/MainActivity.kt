package ir.zinutech.android.theoplayersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.theoplayer.android.api.source.SourceType.HLS
import ir.zinutech.android.theoplayersample.features.PlaybackSource
import ir.zinutech.android.theoplayersample.features.PlayerActivity

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    startActivity(PlayerActivity.starter(
        this@MainActivity,
        PlaybackSource(
            title = "BasicPlayback",
            playUrl = "https://cdn.theoplayer.com/video/sintel/index.m3u8",
            sourceType = HLS
        )
    ))

  }
}
