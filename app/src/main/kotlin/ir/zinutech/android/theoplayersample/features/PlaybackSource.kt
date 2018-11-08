package ir.zinutech.android.theoplayersample.features

import android.os.Parcel
import android.os.Parcelable
import com.theoplayer.android.api.source.SourceType

data class PlaybackSource(
    val title: String,
    val playUrl: String,
    val sourceType: SourceType,
    val poster: String? = null,
    val adUrl: String? = null,
    val adSkipOffset: String? = null,
    val adTimeOffset: String? = null
) : Parcelable {
  constructor(source: Parcel) : this(
      source.readString(),
      source.readString(),
      SourceType.values()[source.readInt()],
      source.readString(),
      source.readString(),
      source.readString(),
      source.readString()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeString(title)
    writeString(playUrl)
    writeInt(sourceType.ordinal)
    writeString(poster)
    writeString(adUrl)
    writeString(adSkipOffset)
    writeString(adTimeOffset)
  }

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<PlaybackSource> = object : Parcelable.Creator<PlaybackSource> {
      override fun createFromParcel(source: Parcel): PlaybackSource = PlaybackSource(source)
      override fun newArray(size: Int): Array<PlaybackSource?> = arrayOfNulls(size)
    }
  }
}