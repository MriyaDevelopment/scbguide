package com.spravochnic.scbguide.utils.enums

import com.google.gson.annotations.SerializedName
import com.spravochnic.scbguide.R

enum class MainContentType(val title: Int, val icon: Int) {
    @SerializedName("lecture") LECTURE(R.string.main_item_lecture, R.drawable.ic_lecture),
    @SerializedName("my_lecture_hall") MY_LECTURE_HALL(R.string.main_item_my_lecture_hall, R.drawable.ic_zipper),
    @SerializedName("community") COMMUNITY(R.string.main_item_community, R.drawable.ic_community)
}