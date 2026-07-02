package com.example.eecs4443_team7_project.util

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.example.eecs4443_team7_project.R

/**
 * SoundManager, responsible for playing sound effects.
 */
object SoundManager {
    private var soundPool: SoundPool? = null
    private var bonkSoundId: Int = 0
    private var kachingSoundId: Int = 0

    fun init(context: Context) {
        if (soundPool != null) return

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(audioAttributes)
            .build()

        // Load the sound files from res/raw
        bonkSoundId = soundPool?.load(context, R.raw.bonk_btn_sound, 1) ?: 0
        kachingSoundId = soundPool?.load(context, R.raw.kaching_btn_sound, 1) ?: 0
    }

    fun playBonkSound() {
        if (bonkSoundId != 0) {
            soundPool?.play(bonkSoundId, 1f, 1f, 0, 0, 1f)
        }
    }

    fun playKachingSound() {
        if (kachingSoundId != 0) {
            soundPool?.play(kachingSoundId, 1f, 1f, 0, 0, 1f)
        }
    }

    fun release() {
        soundPool?.release()
        soundPool = null
        bonkSoundId = 0
        kachingSoundId = 0
    }
}
