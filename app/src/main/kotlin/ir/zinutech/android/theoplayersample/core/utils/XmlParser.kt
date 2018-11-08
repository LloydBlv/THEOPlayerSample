package ir.zinutech.android.theoplayersample.core.utils

import com.google.gson.stream.JsonReader
import com.theoplayer.android.api.source.SourceType
import com.theoplayer.android.api.source.SourceType.DASH
import com.theoplayer.android.api.source.SourceType.HLSX
import com.theoplayer.android.api.source.SourceType.MP4
import ir.zinutech.android.theoplayersample.core.exceptions.ParserException
import ir.zinutech.android.theoplayersample.features.PlaybackSource
import ir.zinutech.android.theoplayersample.features.SampleGroup
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


object XmlParser {


  @Throws(IOException::class)
  fun getSamples(inputStream: InputStream): List<SampleGroup> {
    val jsonReader = JsonReader(InputStreamReader(inputStream, "UTF-8"))
    return readSampleGroups(jsonReader)
  }


  @Throws(IOException::class)
  private fun readEntry(jsonReader: JsonReader): PlaybackSource {

    var sampleName = ""
    var uri = ""
    var adTagUri: String? = null
    var poster: String? = null
    var skipOffset: String? = null
    var timeOffset: String? = null
    var streamType: String? = null

    jsonReader.beginObject()
    while (jsonReader.hasNext()) {
      val name = jsonReader.nextName()
      when (name) {
        "name" -> {
          sampleName = jsonReader.nextString()
        }
        "uri" -> {
          uri = jsonReader.nextString()
        }

        "ad_tag_uri" -> {
          adTagUri = jsonReader.nextString()
        }

        "poster" -> {
          poster = jsonReader.nextString()
        }

        "skip_offset" -> {
          skipOffset = jsonReader.nextString()
        }
        "time_offset" -> {
          timeOffset = jsonReader.nextString()
        }

        "stream_type" -> {
          streamType = jsonReader.nextString()
        }
      }
    }

    jsonReader.endObject()

    return PlaybackSource(
        playUrl = uri,
        title = sampleName,
        adUrl = adTagUri,
        poster = poster,
        adSkipOffset = skipOffset,
        adTimeOffset = timeOffset,
        sourceType = inferType(streamType ?: uri)
    )

  }

  @Throws(IOException::class)
  private fun readSampleGroups(reader: JsonReader): List<SampleGroup> {
    val samplesGroups = arrayListOf<SampleGroup>()
    reader.beginArray()
    while (reader.hasNext()) {
      samplesGroups.add(readSampleGroup(reader))
    }
    reader.endArray()
    return samplesGroups
  }

  @Throws(IOException::class)
  private fun readSampleGroup(reader: JsonReader): SampleGroup {
    var groupName = ""
    val samples = arrayListOf<PlaybackSource>()

    reader.beginObject()
    while (reader.hasNext()) {
      val name = reader.nextName()
      when (name) {
        "name" -> groupName = reader.nextString()
        "samples" -> {
          reader.beginArray()
          while (reader.hasNext()) {
            samples.add(readEntry(reader))
          }
          reader.endArray()
        }
        else -> throw ParserException("Unsupported name: $name")
      }
    }
    reader.endObject()
    return SampleGroup(groupName, samples)
  }

  private fun inferType(url: String): SourceType {
    return when {
      url.endsWith("m3u8") -> SourceType.HLS
      url.endsWith("mpd") -> DASH
      url.endsWith("mkv") -> HLSX
      else -> MP4
    }
  }
}