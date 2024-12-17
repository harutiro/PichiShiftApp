package net.harutiro.pichishiftapp

import android.content.Context
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackParameters
import androidx.media3.exoplayer.ExoPlayer

class PlayerViewModel : ViewModel() {

    private var exoPlayer: ExoPlayer? = null

    // 再生状態
    val isPlaying = mutableStateOf(false)

    // 再生速度
    val playbackSpeed = mutableFloatStateOf(1.0f)

    // ピッチ
    val pitch = mutableFloatStateOf(1.0f)

    fun init(context: Context) {
        exoPlayer = ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri("android.resource://${context.packageName}/${R.raw.maou_14_shining_star}")
            setMediaItem(mediaItem)
            prepare()
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC) // 音楽用コンテンツタイプ
                    .setUsage(C.USAGE_MEDIA) // 通常のメディア再生
                    .build(),
                true // AudioFocusを有効にする
            )
        }
    }

    // 再生
    fun playMusic() {
        exoPlayer?.let {
            it.playWhenReady = true
            it.playbackParameters = PlaybackParameters(playbackSpeed.floatValue, pitch.floatValue)
            isPlaying.value = true
        }
    }

    // 停止
    fun stopMusic() {
        exoPlayer?.pause()
        isPlaying.value = false
    }

    // 再生速度を設定
    fun setPlaybackSpeed(speed: Float) {
        playbackSpeed.floatValue = speed.coerceIn(0.5f, 2.0f) // ここは安全の範囲を指定する
        updatePlaybackParameters()
    }

    // ピッチを設定
    fun setPitch(newPitch: Float) {
        pitch.floatValue = newPitch.coerceIn(0.5f, 2.0f) // ここは安全の範囲を指定する
        updatePlaybackParameters()
    }

    // PlaybackParametersを更新
    private fun updatePlaybackParameters() {
        exoPlayer?.playbackParameters = PlaybackParameters(playbackSpeed.floatValue, pitch.floatValue)
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer?.release()
        exoPlayer = null
    }
}
