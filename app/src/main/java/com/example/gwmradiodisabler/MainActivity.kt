package com.example.gwmradiodisabler

import android.app.Activity
import android.media.MediaPlayer
import android.os.Bundle

import android.media.AudioManager

class MainActivity : Activity(), AudioManager.OnAudioFocusChangeListener {
    private lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        val result = audioManager.requestAudioFocus(
            this,
            AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
        )

        val mp: MediaPlayer = MediaPlayer.create(this, R.raw.power_plug)
        mp.setOnCompletionListener { player: MediaPlayer ->
            player.release()
            finish()
        }
        mp.start()
    }

    override fun onAudioFocusChange(focusChange: Int) {
        when (focusChange) {
            AudioManager.AUDIOFOCUS_LOSS -> {
                if (::mp.isInitialized && mp.isPlaying) mp.pause()
            }
            AudioManager.AUDIOFOCUS_GAIN -> {
                if (::mp.isInitialized && !mp.isPlaying) mp.start()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
