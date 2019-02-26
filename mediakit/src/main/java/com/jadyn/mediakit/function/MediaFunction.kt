package com.jadyn.mediakit.function

import android.media.MediaExtractor
import android.media.MediaFormat
import android.util.Size

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2019/2/12
 *@ChangeList:
 */
val MediaFormat.width
    get() = getInteger(MediaFormat.KEY_WIDTH)

val MediaFormat.height
    get() = getInteger(MediaFormat.KEY_HEIGHT)

val MediaFormat.size
    get() = Size(width, height)

val MediaFormat.duration
    get() = getLong(MediaFormat.KEY_DURATION)

//时长：秒
val MediaFormat.durationSecond
    get() = (getLong(MediaFormat.KEY_DURATION) / 1000000L).toInt()

val MediaFormat.fps: Int
    get() = try {
        getInteger(MediaFormat.KEY_FRAME_RATE)
    } catch (e: Exception) {
        0
    }

val MediaFormat.mime
    get() = getString(MediaFormat.KEY_MIME)

/*
* 选择视频轨
* */
fun MediaExtractor.selectVideoTrack(): Int {
    val numTracks = trackCount
    for (i in 0 until numTracks) {
        val format = getTrackFormat(i)
        val mime = format.getString(MediaFormat.KEY_MIME)
        if (mime.startsWith("video/")) {
            return i
        }
    }
    return -1
}

/*
* 防止时间越界
* */
fun MediaFormat.getSafeTimeUS(second: Float): Long {
    return when {
        second < 0L -> 0L
        second * 1000000 > duration -> duration
        else -> (second * 1000000).toLong()
    }
}