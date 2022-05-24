package com.spravochnic.scbguide.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spravochnic.scbguide.di.ViewModelKey
import com.spravochnic.scbguide.di.factory.AppViewModelFactory
import com.spravochnic.scbguide.ui.common.DialogErrorViewModel
import com.spravochnic.scbguide.ui.lecture.LectureViewModel
import com.spravochnic.scbguide.ui.lecture.details.DetailsLectureViewModel
import com.spravochnic.scbguide.ui.lecture.details.detail.DetailLectureViewModel
import com.spravochnic.scbguide.ui.main.MainViewModel
import com.spravochnic.scbguide.ui.result.ResultViewModel
import com.spravochnic.scbguide.ui.test.TestViewModel
import com.spravochnic.scbguide.ui.test.details.DetailsTestViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
interface CommonMainViewModule {
    @Binds
    fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TestViewModel::class)
    fun bindTestViewModel(viewModel: TestViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LectureViewModel::class)
    fun bindLectureViewModel(viewModel: LectureViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResultViewModel::class)
    fun bindResultViewModel(viewModel: ResultViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsLectureViewModel::class)
    fun bindDetailsLectureViewModel(viewModel: DetailsLectureViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsTestViewModel::class)
    fun bindDetailsTestViewModel(viewModel: DetailsTestViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailLectureViewModel::class)
    fun bindDetailLectureViewModel(viewModel: DetailLectureViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DialogErrorViewModel::class)
    fun bindDialogErrorViewModel(viewModel: DialogErrorViewModel): ViewModel
}