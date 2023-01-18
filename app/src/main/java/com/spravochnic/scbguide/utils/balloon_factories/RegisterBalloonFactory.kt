package com.spravochnic.scbguide.utils.balloon_factories

import android.content.Context
import android.view.Gravity
import androidx.lifecycle.LifecycleOwner
import com.skydoves.balloon.*
import com.spravochnic.scbguide.R

class RegisterBalloonFactory: Balloon.Factory()  {

    override fun create(context: Context, lifecycle: LifecycleOwner?): Balloon {
        return Balloon.Builder(context)
            .setWidthRatio(1.0f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setText(context.getString(R.string.register_email_balloon))
            .setTextColorResource(R.color.white)
            .setTextSize(12f)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setArrowSize(10)
            .setDismissWhenTouchOutside(true)
            .setDismissWhenClicked(true)
            .setTextGravity(Gravity.START)
            .setMarginRight(30)
            .setArrowPosition(0.5f)
            .setPadding(12)
            .setCornerRadius(10f)
            .setArrowOrientation(ArrowOrientation.TOP)
            .setBackgroundColorResource(R.color.system_blue_01)
            .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
            .setLifecycleOwner(lifecycle)
            .build()
    }
}