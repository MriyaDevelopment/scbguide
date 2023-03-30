package com.spravochnic.scbguide.ui.lecture.adapter.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.databinding.ItemLectureBinding
import com.spravochnic.scbguide.ui.lecture.LectureViewModel

class LectureViewHolder(
    private val binding: ItemLectureBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: LectureViewModel.Lecture, lectureOnClickActionListener: ((String) -> Unit)?) {
        binding.nameLecture.text = item.name
        binding.imageLecture.load(item.image) {
            placeholder(R.drawable.ic_launcher_background)
        }
        binding.root.setOnClickListener {
            lectureOnClickActionListener?.invoke(item.name)
        }
    }

    companion object {
        fun getViewHolder(parent: ViewGroup): LectureViewHolder {
            val binding = ItemLectureBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return LectureViewHolder(binding)
        }
    }
}