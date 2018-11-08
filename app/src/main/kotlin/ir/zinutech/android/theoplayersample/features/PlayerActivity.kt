package ir.zinutech.android.theoplayersample.features

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.theoplayer.android.api.event.EventListener
import com.theoplayer.android.api.event.player.PauseEvent
import com.theoplayer.android.api.event.player.PlayEvent
import com.theoplayer.android.api.event.player.PlayerEventTypes
import com.theoplayer.android.api.event.player.TimeUpdateEvent
import com.theoplayer.android.api.source.SourceDescription
import com.theoplayer.android.api.source.TypedSource
import com.theoplayer.android.api.source.addescription.THEOplayerAdDescription
import ir.zinutech.android.theoplayersample.R
import ir.zinutech.android.theoplayersample.R.color
import ir.zinutech.android.theoplayersample.core.platform.BaseActivity
import kotlinx.android.synthetic.main.activity_player.player_activity_theoplayer
import timber.log.Timber

class PlayerActivity : BaseActivity() {

  companion object {
    fun starter(context: Context, playbackSrc: PlaybackSource): Intent {
      return Intent(context, PlayerActivity::class.java).apply {
        putExtra(EXTRA_PLAYBACK_SRC, playbackSrc)
      }
    }

    private const val EXTRA_PLAYBACK_SRC = "extra_playback_src"
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

  private val playbackSrc: PlaybackSource by lazy {
    intent.getParcelableExtra<PlaybackSource>(EXTRA_PLAYBACK_SRC)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_player)

    Timber.d("onCreate()")

    initUi()
    initPlayer()
  }

  private fun initUi() {
    supportActionBar?.apply {
      title = playbackSrc.title
      setDisplayHomeAsUpEnabled(true)
      setHomeButtonEnabled(true)
    }


    player_activity_theoplayer.settings.isFullScreenOrientationCoupled = true
  }


  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    return when (item?.itemId) {
      android.R.id.home -> {
        onBackPressed()
        true
      }
      else -> {
        super.onOptionsItemSelected(item)
      }
    }
  }

  override fun onConfigurationChanged(newConfig: Configuration?) {
    super.onConfigurationChanged(newConfig)
    Timber.d("onConfigurationChanged()")
  }

  private fun initPlayer() {
    Timber.d("initPlayer(), playbackSource:[%s]", playbackSrc)

    val typedSource: TypedSource = TypedSource.Builder
        .typedSource()
        .src(playbackSrc.playUrl)
        .type(playbackSrc.sourceType)
        .build()

    val sourceDescription = SourceDescription.Builder()
        .sources(typedSource)
        .apply {
          playbackSrc.poster?.let {
            poster(it)
          }
          playbackSrc.adUrl?.let {
            ads(THEOplayerAdDescription.Builder.adDescription(it)
                .apply {
                  playbackSrc.adSkipOffset?.let {
                    skipOffset(it)
                  }

                  playbackSrc.adTimeOffset?.let {
                    timeOffset(it)
                  }
                }
                .build())
          }
        }
        .build()

    player_activity_theoplayer.player.apply {
      source = sourceDescription
      isAutoplay = playbackSrc.poster?.isNotEmpty() != true
      addEventListener(PlayerEventTypes.PLAY, playEventListener)
      addEventListener(PlayerEventTypes.PAUSE, pauseEventListener)
      addEventListener(PlayerEventTypes.TIMEUPDATE, timeUpdateEventListener)
    }

  }

  override fun onPause() {
    Timber.d("onPause()")

    super.onPause()
    player_activity_theoplayer.onPause()
  }

  override fun onResume() {
    Timber.d("onResume()")

    super.onResume()
    player_activity_theoplayer.onResume()
  }

  override fun onDestroy() {
    Timber.d("onDestroy()")

    player_activity_theoplayer.onDestroy()

    player_activity_theoplayer.player?.apply {
      removeEventListener(PlayerEventTypes.PLAY, playEventListener)
      removeEventListener(PlayerEventTypes.PAUSE, pauseEventListener)
      removeEventListener(PlayerEventTypes.TIMEUPDATE, timeUpdateEventListener)

    }
    super.onDestroy()

  }
}