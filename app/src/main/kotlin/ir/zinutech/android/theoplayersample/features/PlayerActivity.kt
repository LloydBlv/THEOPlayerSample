package ir.zinutech.android.theoplayersample.features

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.theoplayer.android.api.event.EventListener
import com.theoplayer.android.api.event.player.PauseEvent
import com.theoplayer.android.api.event.player.PlayEvent
import com.theoplayer.android.api.event.player.PlayerEventTypes
import com.theoplayer.android.api.event.player.TimeUpdateEvent
import com.theoplayer.android.api.source.SourceDescription
import com.theoplayer.android.api.source.SourceType
import com.theoplayer.android.api.source.TypedSource
import ir.zinutech.android.theoplayersample.R
import ir.zinutech.android.theoplayersample.core.platform.BaseActivity
import kotlinx.android.synthetic.main.activity_player.*
import timber.log.Timber

class PlayerActivity : BaseActivity() {

    companion object {
        fun starter(context: Context, sourceUrl: String, sourceType: SourceType): Intent {
            return Intent(context, PlayerActivity::class.java).apply {
                putExtra(EXTRA_SOURCE_URL, sourceUrl)
                putExtra(EXTRA_SOURCE_TYPE, sourceType.ordinal)
            }
        }

        private const val EXTRA_SOURCE_URL = "extra_source_url"
        private const val EXTRA_SOURCE_TYPE = "extra_source_type"
    }

    private val playEventListener by lazy {
        EventListener<PlayEvent> { Timber.d("playEvent:[%s]", it) }
    }

    private val pauseEventListener by lazy {
        EventListener<PauseEvent> { Timber.d("pauseEvent:[%s]", it) }
    }

    private val timeUpdateEventListener by lazy {
        EventListener<TimeUpdateEvent> { Timber.d("timeUpdateEvent:[%s]", it.currentTime) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        initPlayer()
    }

    private fun initPlayer() {
        val playSrc = intent.getStringExtra(EXTRA_SOURCE_URL)
        val sourceType = SourceType.values()[intent.getIntExtra(EXTRA_SOURCE_TYPE, -1)]

        val typedSource: TypedSource = TypedSource.Builder
            .typedSource()
            .src(playSrc)
            .type(sourceType)
            .build()

        val sourceDescription = SourceDescription.Builder()
            .sources(typedSource)
            .build()

        player_activity_theoplayer.player.apply {
            source = sourceDescription
            isAutoplay = true
            addEventListener(PlayerEventTypes.PLAY, playEventListener)
            addEventListener(PlayerEventTypes.PAUSE, pauseEventListener)
            addEventListener(PlayerEventTypes.TIMEUPDATE, timeUpdateEventListener)
        }

    }

    override fun onPause() {
        super.onPause()
        player_activity_theoplayer.onPause()
    }

    override fun onResume() {
        super.onResume()
        player_activity_theoplayer.onResume()
    }

    override fun onDestroy() {
        player_activity_theoplayer.onDestroy()

        player_activity_theoplayer.player?.apply {
            removeEventListener(PlayerEventTypes.PLAY, playEventListener)

        }
        super.onDestroy()

    }
}