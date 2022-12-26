package com.spravochnic.scbguide.ui.profile.adapter

import android.view.View.TEXT_ALIGNMENT_CENTER
import android.widget.BaseAdapter
import androidx.core.view.isVisible
import com.google.android.material.shape.CornerFamily
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.base.ui.adapter.BaseListAdapter
import com.spravochnic.scbguide.databinding.ItemProfileBinding
import com.spravochnic.scbguide.utils.dp
import com.spravochnic.scbguide.utils.enums.ProfileType
import com.spravochnic.scbguide.utils.getColor
import com.spravochnic.scbguide.utils.setCornerRadius
import com.spravochnic.scbguide.utils.updateMargin

class ProfileAdapter : BaseListAdapter<ProfileAdapter.ItemProfile>() {

    private var onClickProfileItemListener: ((ProfileType) -> Unit)? = null

    fun setOnClickProfileItemListener(action: (ProfileType) -> Unit) {
        onClickProfileItemListener = action
    }

    override fun build() {
        baseViewHolder(ItemProfile::class, ItemProfileBinding::inflate) { item ->
            binding.run {
                if (item.type.icon != null) {
                    ivIconProfile.setImageResource(item.type.icon)
                } else {
                    txtTextProfile.textAlignment = TEXT_ALIGNMENT_CENTER
                }
                txtTextProfile.text = item.type.text
                if (item.type == ProfileType.DELETE) txtTextProfile.setTextColor(getColor(R.color.red))

                viewDividerProfile.isVisible = !item.bottom
                val top = if (item.top) 10.dp else 0f
                val bottom = if (item.bottom) 10.dp else 0f
                root.setCornerRadius(top, top, bottom, bottom)
                root.updateMargin(
                    14.dp.toInt(),
                    if (item.top) 16.dp.toInt() else 0,
                    14.dp.toInt(),
                    0
                )
                root.setOnClickListener {
                    onClickProfileItemListener?.invoke(item.type)
                }
            }
        }
    }

    data class ItemProfile(
        val type: ProfileType,
        val bottom: Boolean,
        val top: Boolean,
    )
}