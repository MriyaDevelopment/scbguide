package com.spravochnic.scbguide.di.modules

import com.spravochnic.scbguide.ui.common.DialogErrorFragment
import com.spravochnic.scbguide.ui.lecture.LectureFragment
import com.spravochnic.scbguide.ui.lecture.details.DetailsLectureFragment
import com.spravochnic.scbguide.ui.lecture.details.detail.DetailLectureFragment
import com.spravochnic.scbguide.ui.lecture.details.photo_lecture.PhotoLectureFragment
import com.spravochnic.scbguide.ui.main.MainFragment
import com.spravochnic.scbguide.ui.result.ResultDialogFragment
import com.spravochnic.scbguide.ui.test.TestFragment
import com.spravochnic.scbguide.ui.test.details.DetailsTestFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class CommonMainFragmentsBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector()
    abstract fun contributeLectureFragment(): LectureFragment

    @ContributesAndroidInjector
    abstract fun contributeTestFragment(): TestFragment

    @ContributesAndroidInjector
    abstract fun contributeResultFragment(): ResultDialogFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailsLectureFragment(): DetailsLectureFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailsTestFragment(): DetailsTestFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailLectureFragment(): DetailLectureFragment

    @ContributesAndroidInjector
    abstract fun contributeErrorDialogFragment(): DialogErrorFragment

    @ContributesAndroidInjector
    abstract fun contributePhotoLectureFragment(): PhotoLectureFragment
}