package com.voiceeffects.supereffect.convertvideotomp3.file.adapter

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import com.voiceeffects.supereffect.convertvideotomp3.R
import com.voiceeffects.supereffect.convertvideotomp3.base.BaseBindingAdapterDiff
import com.voiceeffects.supereffect.convertvideotomp3.data.database.entity.AudioEntity
import com.voiceeffects.supereffect.convertvideotomp3.databinding.AudioItemViewBinding

class AudioAdapter(val context: Context): BaseBindingAdapterDiff<AudioEntity, AudioItemViewBinding>(
    object : DiffUtil.ItemCallback<AudioEntity>() {
        override fun areItemsTheSame(oldItem: AudioEntity, newItem: AudioEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AudioEntity, newItem: AudioEntity): Boolean {
            return oldItem == newItem
        }

    }
) {
    var onPlayAudio: ((pos: Int, audioEntity: AudioEntity) -> Unit) = { _, _ -> }
    var onSelect: ((pos: Int, audioEntity: AudioEntity) -> Unit) = { _, _ -> }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolderBase(holder: BaseHolder<AudioItemViewBinding>, position: Int) {
        with(getItem(holder.adapterPosition)) {
            holder.binding.audioName.text = this.nameFile
            holder.binding.audioInfo.text = "${this.start} + | + ${this.size}"

            holder.binding.actionAudioBtn.setImageResource(
                if(this.isPlay) {
                    R.drawable.ic_play_v2
                } else R.drawable.ic_pause_v2
            )

            holder.binding.animationAudio.isVisible = !this.isPlay
        }
    }

    override val layoutIdItem: Int
        get() = R.layout.audio_item_view
}