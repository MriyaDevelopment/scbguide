package com.spravochnic.scbguide.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.spravochnic.scbguide.api.models.TestCategories
import com.spravochnic.scbguide.databinding.ViewDetailsLectureBinding
import com.spravochnic.scbguide.databinding.ViewTestBinding
import com.spravochnic.scbguide.db.entity.LectureCategoriesEntity

class TestAdapter(private val onClickTestItem: (String) -> Unit = {}) :
    ListAdapter<TestCategories, TestAdapter.TestViewHolder>(TestCategoriesDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        return TestViewHolder(
            ViewTestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TestViewHolder(
        private val binding: ViewTestBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TestCategories) {
            with(binding) {
                imgTest.load(item.image)
                textLevel.text = item.name
                textQuestions.text = item.questions
                root.setOnClickListener {
                    onClickTestItem(item.type)
                }
            }
        }
    }

    class TestCategoriesDiffUtilCallback : DiffUtil.ItemCallback<TestCategories>() {
        override fun areItemsTheSame(
            oldItem: TestCategories,
            newItem: TestCategories
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: TestCategories,
            newItem: TestCategories
        ): Boolean = oldItem == newItem
    }
}