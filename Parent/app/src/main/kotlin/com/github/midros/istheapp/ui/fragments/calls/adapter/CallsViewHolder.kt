package com.github.midros.istheapp.ui.fragments.calls.adapter

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import at.grabner.circleprogress.CircleProgressView
import com.github.midros.istheapp.R
import com.github.midros.istheapp.data.model.Calls
import com.github.midros.istheapp.utils.ConstFun.runThread
import com.github.midros.istheapp.utils.ConstFun.convertCurrentDuration
import com.pawegio.kandroid.longToast
import kotterknife.bindView
import java.io.File
import java.io.FileNotFoundException
import android.media.AudioManager


/**
 * Created by luis rafael on 28/03/18.
 */
class CallsViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view) {

    private val contact: TextView by bindView(R.id.contact_item_calls)
    private val phoneNumber: TextView by bindView(R.id.phone_number_item_calls)
    private val dateTime: TextView by bindView(R.id.date_item_calls)
    val imageItem: ImageView by bindView(R.id.img_item_calls)
    val progressCall: CircleProgressView by bindView(R.id.progress_item_calls)
    val itemClick: LinearLayout by bindView(R.id.item_click_calls)
    private val duration: TextView by bindView(R.id.duration_item_calls)
    private val currentDuration: TextView by bindView(R.id.current_duration_item_calls)

    private val audio = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    private var mediaPlayer: MediaPlayer? = null
    private var currentProgress = false
    private var runThreads: Thread? = null
    private var playing: Boolean = false
    private var stopPlayer: Boolean = true

    fun bind(item: Calls) {
        contact.text = item.contact
        phoneNumber.text = item.phoneNumber
        dateTime.text = item.dateTime
        duration.text = item.duration
    }

    fun getPlaying(): Boolean = playing
    fun getStopPlayer(): Boolean = stopPlayer

    fun initializeMediaPlayer(fileName: String) {
        try {
            mediaPlayer = MediaPlayer.create(context, Uri.fromFile(File(fileName)))
            mediaPlayer!!.setOnCompletionListener {
                onStopAudioCall()
            }
        } catch (e: FileNotFoundException) {
            context.longToast(e.message.toString())
        }
    }

    fun onPlayAudioCall() {

        val currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC)
        if (currentVolume == 0) context.longToast(R.string.volume_up)

        if (mediaPlayer != null) {
            setDrawableItemClick(R.drawable.ic_pause_black_24dp)
            setMaxProgress(mediaPlayer!!.duration.toFloat())
            mediaPlayer!!.start()
            playing = true
            stopPlayer = false
            currentProgress = true
            setCurrentProgress()

        }
    }

    fun onPauseAudioCall() {
        if (mediaPlayer != null) {
            mediaPlayer!!.pause()
            setDrawableItemClick(R.drawable.ic_play_arrow_24dp)
            playing = false
            stopPlayer = false
        }
    }

    fun onStopAudioCall() {
        if (mediaPlayer != null) {
            currentProgress = false
            stopCurrentProgress()
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer = null
            setDrawableItemClick(R.drawable.ic_play_arrow_24dp)
            setProgressValue(0f)
            playing = false
            stopPlayer = true
        }
    }

    fun setDrawableItemClick(id: Int) {
        imageItem.background = ContextCompat.getDrawable(context, id)
    }

    private fun setMaxProgress(value: Float) {
        progressCall.setValue(0f)
        progressCall.maxValue = value
    }

    private fun setProgressValue(value: Float) {
        currentDuration.text = convertCurrentDuration(value.toInt())
        progressCall.setValueAnimated(value, 300)
    }

    private fun setCurrentProgress() {
        runThreads = runThread(300) {
            if (currentProgress) if (mediaPlayer != null) setProgressValue(mediaPlayer!!.currentPosition.toFloat())
        }
        runThreads!!.start()
    }

    private fun stopCurrentProgress() {
        if (runThreads != null) runThreads!!.interrupt()
    }

}