package com.spravochnic.scbguide.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.databinding.ViewDetailsLectureBinding
import com.spravochnic.scbguide.databinding.ViewLectureBinding
import com.spravochnic.scbguide.db.entity.LectureCategoriesEntity
import com.spravochnic.scbguide.db.entity.LectureEntity
import com.spravochnic.scbguide.utils.ContextUtils
import com.spravochnic.scbguide.utils.gone
import com.spravochnic.scbguide.utils.visible

class LectureCarouselAdapter(
    val baseUrl: String,
    val contextUtils: ContextUtils,
    private val onClickWatchFull: (String, String) -> Unit = { type, name -> },
    private val onClickImgDevice: (String, ImageView, String) -> Unit = { image, imageView: ImageView, name -> }
) :
    ListAdapter<LectureEntity, LectureCarouselAdapter.LectureCarouselViewHolder>(
        LectureCarouselDiffUtilCallback()
    ) {

    override fun onBindViewHolder(holder: LectureCarouselViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureCarouselViewHolder {
        return LectureCarouselViewHolder(
            ViewDetailsLectureBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class LectureCarouselViewHolder(
        private val binding: ViewDetailsLectureBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LectureEntity) {
            with(binding) {
                val image = baseUrl + "instruments/imageLectory/" + item.image
                title.text = item.name
                description.text = item.description
                imgDevice.transitionName = "transition_quick${item.id}"
                imgDevice.load(image) {
                    placeholder(R.drawable.img_placeholder)
                    listener(
                        onStart = {
                            imgDevice.animate().alpha(0f)
                            imgProgress.visible()
                        },
                        onSuccess = { _, _ ->
                            imgDevice.animate().alpha(1f)
                            imgProgress.gone()
                            imgDevice.setOnClickListener {
                                onClickImgDevice(image, imgDevice, item.name.toString())
                            }
                        }
                    )
                }
                watchFull.setOnClickListener {
                    onClickWatchFull(item.type.toString(), item.name.toString())
                }
            }
        }
    }

    class LectureCarouselDiffUtilCallback : DiffUtil.ItemCallback<LectureEntity>() {
        override fun areItemsTheSame(
            oldItem: LectureEntity,
            newItem: LectureEntity
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: LectureEntity,
            newItem: LectureEntity
        ): Boolean = oldItem == newItem
    }
}