package com.spravochnic.scbguide.di.modules

import com.spravochnic.scbguide.ui.lecture.LectureFragment
import com.spravochnic.scbguide.ui.main.MainFragment
import com.spravochnic.scbguide.ui.result.ResultDialogFragment
import com.spravochnic.scbguide.ui.test.TestFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class CommonMainFragmentsBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeLectureFragment(): LectureFragment

    @ContributesAndroidInjector
    abstract fun contributeTestFragment(): TestFragment

    @ContributesAndroidInjector
    abstract fun contributeResultFragment(): ResultDialogFragment
}