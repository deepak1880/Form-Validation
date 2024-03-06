package com.example.neostartask.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.neostartask.R
import com.example.neostartask.databinding.FragmentImagePickerBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

const val IMAGE_PICKER_FRAGMENT_REQUEST = "image_picker_fragment_request"
const val IMAGE_PICKER_FRAGMENT_REQUEST_KEY = "image_picker_fragment_request_key"
const val IMAGE_PICKER_CAMERA = 1
const val IMAGE_PICKER_GALLERY = 2

@AndroidEntryPoint
class ImagePickerBottomSheetFragment :
    BottomSheetDialogFragment(R.layout.fragment_image_picker_bottom_sheet) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentImagePickerBottomSheetBinding.bind(view)

        binding.cameraButton.setOnClickListener {
            setFragmentResult(
                IMAGE_PICKER_FRAGMENT_REQUEST, bundleOf(
                    IMAGE_PICKER_FRAGMENT_REQUEST_KEY to IMAGE_PICKER_CAMERA
                )
            )
            findNavController().popBackStack()
        }

        binding.galleryButton.setOnClickListener {
            setFragmentResult(
                IMAGE_PICKER_FRAGMENT_REQUEST, bundleOf(
                    IMAGE_PICKER_FRAGMENT_REQUEST_KEY to IMAGE_PICKER_GALLERY
                )
            )
            findNavController().popBackStack()
        }
    }

}