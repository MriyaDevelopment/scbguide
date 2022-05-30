package com.spravochnic.scbguide.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ImageRequest
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.databinding.ViewLectureBinding
import com.spravochnic.scbguide.db.entity.LectureCategoriesEntity
import com.spravochnic.scbguide.utils.ContextUtils
import com.spravochnic.scbguide.utils.gone
import com.spravochnic.scbguide.utils.visible
import javax.inject.Inject
import javax.inject.Singleton

class LectureAdapter(
    val contextUtils: ContextUtils,
    private val onClickItemLecture: (String, String) -> Unit = { type, name -> }
) :
    ListAdapter<LectureCategoriesEntity, LectureAdapter.LectureCategoryViewHolder>(
        LectureCategoriesDiffUtilCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureCategoryViewHolder {
        return LectureCategoryViewHolder(
            ViewLectureBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LectureCategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class LectureCategoryViewHolder(
        private val binding: ViewLectureBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LectureCategoriesEntity) {
            with(binding) {
                imgLecture.load(item.image) {
                    listener(
                        onStart = {
                            imgLecture.animate().alpha(0f)
                            imgProgress.visible()
                        },
                        onSuccess = { _, _ ->
                            imgLecture.animate().alpha(1f)
                            imgProgress.gone()
                        }
                    )
                }
                textLecture.text = item.name
                textDevices.text =
                    item.numbers?.let {
                        contextUtils.getString(
                            R.string.lectureViewDevice,
                            it
                        )
                    }
                root.setOnClickListener {
                    onClickItemLecture(item.type.toString(), item.name.toString())
                }
            }
        }
    }

    class LectureCategoriesDiffUtilCallback : DiffUtil.ItemCallback<LectureCategoriesEntity>() {
        override fun areItemsTheSame(
            oldItem: LectureCategoriesEntity,
            newItem: LectureCategoriesEntity
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: LectureCategoriesEntity,
            newItem: LectureCategoriesEntity
        ): Boolean = oldItem == newItem
    }
}