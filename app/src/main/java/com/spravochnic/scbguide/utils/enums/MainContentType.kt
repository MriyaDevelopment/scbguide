package com.spravochnic.scbguide.utils.enums

import com.spravochnic.scbguide.R

enum class MainContentType(val title: Int, val icon: Int) {
    LECTURE(R.string.main_item_lecture, R.drawable.ic_lecture),
    MY_LECTURE_HALL(R.string.main_item_my_lecture_hall, R.drawable.ic_zipper),
    COMMUNITY(R.string.main_item_community, R.drawable.ic_community)
}