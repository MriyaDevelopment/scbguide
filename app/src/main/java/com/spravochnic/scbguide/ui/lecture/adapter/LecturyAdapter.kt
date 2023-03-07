package com.spravochnic.scbguide.ui.lecture.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.databinding.ItemLectureBinding
import com.spravochnic.scbguide.ui.lecture.LectureViewModel

class LecturyAdapter :
    ListAdapter<LectureViewModel.Lecture, LecturyAdapter.LectureViewHolder>(LectureDiffUtilCallback()) {

    private var lectureOnClickActionListener: ((String) -> Unit)? = null

    fun setClickLectureActionListener(action: (String) -> Unit) {
        lectureOnClickActionListener = action
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureViewHolder {
        return LectureViewHolder.getViewHolder(parent)
    }

    override fun onBindViewHolder(holder: LectureViewHolder, position: Int) {
        holder.bind(getItem(position), lectureOnClickActionListener)
    }

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

    class LectureDiffUtilCallback : DiffUtil.ItemCallback<LectureViewModel.Lecture>() {

        override fun areItemsTheSame(
            oldItem: LectureViewModel.Lecture,
            newItem: LectureViewModel.Lecture
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: LectureViewModel.Lecture,
            newItem: LectureViewModel.Lecture
        ): Boolean =
            oldItem == newItem
    }


}