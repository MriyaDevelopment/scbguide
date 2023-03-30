package com.spravochnic.scbguide.ui.lecture.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.spravochnic.scbguide.ui.lecture.LectureViewModel

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