package com.spravochnic.scbguide.ui.auth.register

import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import coil.load
import coil.size.Scale
import com.google.android.material.internal.CheckableImageButton
import com.skydoves.balloon.balloon
import com.spravochnic.scbguide.base.ui.fragments.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentRegisterBinding
import com.spravochnic.scbguide.ui.auth.recovery_service.RecoverServiceFragment
import com.spravochnic.scbguide.utils.StoragePickerUtils
import com.spravochnic.scbguide.utils.balloon_factories.RegisterBalloonFactory
import com.spravochnic.scbguide.utils.observe
import com.spravochnic.scbguide.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import java.io.InputStream
import javax.inject.Inject


@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel by viewModels<RegisterViewModel>()
    private val registerBalloonFactor by balloon<RegisterBalloonFactory>()

    @Inject
    lateinit var storagePickerUtils: StoragePickerUtils

    override fun setListeners() = with(binding) {
        txtNameRegister.doAfterTextChanged {
            viewModel.onChangeName(it.toString())
        }

        tilEmailRegister.setEndIconOnClickListener {
            tilEmailRegister.findViewById<CheckableImageButton>(com.google.android.material.R.id.text_input_end_icon)
                ?.let {
                    registerBalloonFactor.showAlignBottom(it)
                }
        }

        txtEmailRegister.doAfterTextChanged {
            viewModel.onChangeEmail(it.toString())
        }

        txtPasswordRegister.doAfterTextChanged {
            viewModel.onChangePassword(it.toString())
        }

        txtRepeatPasswordRegister.doAfterTextChanged {
            viewModel.onChangeRepeatPassword(it.toString())
        }

        txtAboutMeRegister.doAfterTextChanged {
            viewModel.onChangeAboutMe(it.toString())
        }

        sivAvatarRegister.setOnClickListener {
            storagePickerUtils.galleryImagePicker { viewModel.onPickAvatar(it) }
        }

        tbRegister.setNavigationOnClickListener {
            onBackPressed()
        }

        btnRegister.setOnThrottleClickListener {
            viewModel.onClickRegister()
        }

        btnLoginRegister.setOnClickListener {
            onBackPressed()
        }
    }

    override fun setObservable() = with(viewModel) {

        avatarFlow.observe(viewLifecycleOwner) { avatar ->
            if (avatar != Uri.EMPTY) {
                binding.sivAvatarRegister.run {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    load(avatar)
                }
            }
        }

        validatorFlow.observe(viewLifecycleOwner) {
            binding.btnRegister.isEnabled = it
        }
    }

    companion object {
        const val MESSAGE = "MESSAGE"
    }
}