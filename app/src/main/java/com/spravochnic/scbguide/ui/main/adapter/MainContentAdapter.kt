package com.spravochnic.scbguide.ui.main.adapter

import com.spravochnic.scbguide.api.responses.MainResponse
import com.spravochnic.scbguide.base.ui.adapter.BaseListAdapter
import com.spravochnic.scbguide.databinding.ItemBoardBinding
import com.spravochnic.scbguide.databinding.ItemMainBinding
import com.spravochnic.scbguide.utils.WordDeclension
import com.spravochnic.scbguide.utils.getString

class MainContentAdapter : BaseListAdapter<MainContentAdapter.ItemMainContent>() {

    override fun build() {
        baseViewHolder(ItemMainContent.WrapBoard::class, ItemBoardBinding::inflate) {}

        baseViewHolder(ItemMainContent.WrapCategory::class, ItemMainBinding::inflate) { item ->
            binding.run {
                item.result.mainContentType?.let {
                    imgItemMain.setImageResource(it.icon)
                    txtTitleItemMain.text = getString(it.title)
                }
                txtSubTitleItemMain.text = WordDeclension.THEMES.declension(
                    item.result.numberOfTopics.toInt(),
                    root.context
                )
            }
        }
    }

    sealed class ItemMainContent {
        object WrapBoard : ItemMainContent()
        data class WrapCategory(val result: MainResponse.Result) : ItemMainContent()
    }
}