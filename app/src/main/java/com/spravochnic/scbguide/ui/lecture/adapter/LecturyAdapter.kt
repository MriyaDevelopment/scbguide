package com.spravochnic.scbguide.ui.lecture.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.spravochnic.scbguide.ui.lecture.LectureViewModel
import com.spravochnic.scbguide.ui.lecture.adapter.diff.LectureDiffUtilCallback
import com.spravochnic.scbguide.ui.lecture.adapter.holder.LectureViewHolder

class LecturyAdapter :
    ListAdapter<LectureViewModel.Lecture, LectureViewHolder>(LectureDiffUtilCallback()) {

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
}