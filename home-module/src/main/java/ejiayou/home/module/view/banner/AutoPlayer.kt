package ejiayou.home.module.view.banner

import android.os.Handler
import android.os.Looper

/**
 * @author:
 * @created on: 2022/7/19 16:05
 * @description:
 */
class AutoPlayer(playable: Playable) {
    /**
     * Define how an object can be auto-playable.
     */
    interface Playable {
        fun playTo(to: Int)
        fun playNext()
        fun playPrevious()
        val total: Int
        val current: Int
    }

    enum class PlayDirection {
        to_left, to_right
    }

    enum class PlayRecycleMode {
        repeat_from_start, play_back
    }

    private var mDirection = PlayDirection.to_right
    private var mPlayRecycleMode = PlayRecycleMode.repeat_from_start
    private var mTimeInterval = 5000
    private val mPlayable: Playable
    private var mTimerTask: Runnable? = null
    private var mSkipNext = false
    private var mTotal = 0
    private var mPlaying = false
    private var mPaused = false

    fun skipNext() {
        mSkipNext = true
    }

    @JvmOverloads
    fun play(start: Int = 0, direction: PlayDirection? = PlayDirection.to_right) {
        if (mPlaying) return
        mTotal = mPlayable.total
        if (mTotal <= 1) {
            return
        }
        mPlaying = true
        playTo(start)
        val handler = Handler(Looper.myLooper()!!)
        mTimerTask = Runnable {
            if (!mPaused) {
                playNextFrame()
            }
            if (mPlaying) {
                handler.postDelayed(mTimerTask!!, mTimeInterval.toLong())
            }
        }
        handler.postDelayed(mTimerTask!!, mTimeInterval.toLong())
    }

    fun stop() {
        if (!mPlaying) {
            return
        }
        mPlaying = false
    }

    fun setTimeInterval(timeInterval: Int): AutoPlayer {
        mTimeInterval = timeInterval
        return this
    }

    fun setPlayRecycleMode(playRecycleMode: PlayRecycleMode): AutoPlayer {
        mPlayRecycleMode = playRecycleMode
        return this
    }

    private fun playNextFrame() {
        if (mSkipNext) {
            mSkipNext = false
            return
        }
        val current = mPlayable.current
        if (mDirection == PlayDirection.to_right) {
            if (current == mTotal - 1) {
                if (mPlayRecycleMode == PlayRecycleMode.play_back) {
                    mDirection = PlayDirection.to_left
                    playNextFrame()
                } else {
                    playTo(0)
                }
            } else {
                playNext()
            }
        } else {
            if (current == 0) {
                if (mPlayRecycleMode == PlayRecycleMode.play_back) {
                    mDirection = PlayDirection.to_right
                    playNextFrame()
                } else {
                    playTo(mTotal - 1)
                }
            } else {
                playPrevious()
            }
        }
    }

    fun pause() {
        mPaused = true
    }

    fun resume() {
        mPaused = false
    }

    private fun playTo(to: Int) {
        mPlayable.playTo(to)
    }

    private fun playNext() {
        mPlayable.playNext()
    }

    private fun playPrevious() {
        mPlayable.playPrevious()
    }

    init {
        mPlayable = playable
    }
}
