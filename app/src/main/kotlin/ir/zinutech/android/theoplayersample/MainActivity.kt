package ir.zinutech.android.theoplayersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.theoplayer.android.api.source.SourceType
import ir.zinutech.android.theoplayersample.features.PlayerActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(PlayerActivity.starter(
            context = this@MainActivity,
            sourceUrl = "https://cdn.theoplayer.com/video/sintel/index.m3u8",
            sourceType = SourceType.HLS
        ))
    }
}
