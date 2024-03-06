package com.example.neostartask.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.neostartask.R
import com.example.neostartask.databinding.FragmentUserPersonalInfoBinding
import com.example.neostartask.model.BasicInfo
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


private const val TAG = "UserBasicInfoFragment"

@AndroidEntryPoint
class UserBasicInfoFragment : Fragment(R.layout.fragment_user_personal_info) {

    private var _binding: FragmentUserPersonalInfoBinding? = null
    private val binding: FragmentUserPersonalInfoBinding get() = _binding!!
    private val viewModel: UserBasicInfoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUserPersonalInfoBinding.bind(view)
        initViews()
    }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                Glide.with(requireContext()).load(uri).centerCrop()
                    .placeholder(R.drawable.ic_launcher_background).into(binding.profileImageView)
                viewModel.profileImageUri = uri.toString()
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    private fun takeImageUsingCamera() {
        val file = File(requireContext().filesDir, "userProfile_${System.currentTimeMillis()}")
        val uri = FileProvider.getUriForFile(
            requireContext(),
             requireContext().applicationContext.packageName+".provider",
            file
        )
        launchCameraIntent.launch(uri)
        viewModel.profileImageUri = uri.toString()
    }

    private val launchCameraIntent =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                Glide.with(requireContext()).load(viewModel.profileImageUri?.toUri()).centerCrop()
                    .placeholder(R.drawable.ic_launcher_background).into(binding.profileImageView)
            }
        }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                takeImageUsingCamera()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Camera permission is required",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private fun launchCamera() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            takeImageUsingCamera()
        } else {
            requestPermissionLauncher.launch(
                Manifest.permission.CAMERA
            )
        }
    }

    private fun launchGallery() {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun initViews() {
        binding.profileImagePickerButton.setOnClickListener {

            setFragmentResultListener(IMAGE_PICKER_FRAGMENT_REQUEST) { key, bundle ->
                val requestType = bundle.getInt(IMAGE_PICKER_FRAGMENT_REQUEST_KEY)
                if (requestType == IMAGE_PICKER_CAMERA) {
                    launchCamera()
                }
                if (requestType == IMAGE_PICKER_GALLERY) {
                    launchGallery()
                }

            }
            val action = UserBasicInfoFragmentDirections.toImagePickerBottomSheetFragment()
            findNavController().navigate(action)


        }
        binding.nextButton.setOnClickListener {
            validateUserInputs()
        }

        resetValidationErrors()
    }

    private fun resetValidationErrors() {
        with(binding) {

            firstNameEditText.doOnTextChanged { _, _, _, _ ->
                firstNameInputLayout.error = null
            }

            lastNameEditText.doOnTextChanged { _, _, _, _ ->
                lastNameInputLayout.error = null
            }


            phoneNumberEditText.doOnTextChanged { _, _, _, _ ->
                phoneNumberInputLayout.error = null
            }


            emailEditText.doOnTextChanged { _, _, _, _ ->
                emailInputLayout.error = null
            }


            passwordEditText.doOnTextChanged { _, _, _, _ ->
                passwordInputLayout.error = null
            }


            confirmPasswordEditText.doOnTextChanged { _, _, _, _ ->
                confirmPasswordInputLayout.error = null
            }

        }
    }

    private fun validateUserInputs() {
        with(binding) {
            var isInputsValid = true
            if (viewModel.profileImageUri == null) {
                Toast.makeText(
                    requireContext(), "Please select a profile image", Toast.LENGTH_SHORT
                ).show()


                isInputsValid = false
            }

            val firstNameValidation = viewModel.validateFirstName(firstNameEditText.text.toString())

            if (firstNameValidation.first) {
                firstNameInputLayout.error = null
            } else {
                firstNameInputLayout.error = firstNameValidation.second

                isInputsValid = false
            }

            val lastNameValidation = viewModel.validateLastName(lastNameEditText.text.toString())

            if (lastNameValidation.first) {
                lastNameInputLayout.error = null
            } else {
                lastNameInputLayout.error = lastNameValidation.second

                isInputsValid = false
            }


            val phoneNumberValidation =
                viewModel.validatePhoneNumber(phoneNumberEditText.text.toString())

            if (phoneNumberValidation.first) {
                phoneNumberInputLayout.error = null
            } else {
                phoneNumberInputLayout.error = phoneNumberValidation.second

                isInputsValid = false
            }


            val emailValidation = viewModel.validateEmail(emailEditText.text.toString())

            if (emailValidation.first) {
                emailInputLayout.error = null
            } else {
                emailInputLayout.error = emailValidation.second

                isInputsValid = false
            }


            val passwordValidation = viewModel.validatePassword(passwordEditText.text.toString())

            if (passwordValidation.first) {
                passwordInputLayout.error = null
            } else {
                passwordInputLayout.error = passwordValidation.second

                isInputsValid = false
            }


            val confirmPasswordValidation = viewModel.validateConfirmPassword(
                passwordEditText.text.toString(), confirmPasswordEditText.text.toString()
            )

            if (confirmPasswordValidation.first) {
                confirmPasswordInputLayout.error = null
            } else {
                confirmPasswordInputLayout.error = confirmPasswordValidation.second

                isInputsValid = false
            }
            if (isInputsValid) {
                saveUserBasicDetails()
            }
        }
    }

    private fun saveUserBasicDetails() {
        with(binding) {
            val isMale = genderRadioGroup.checkedRadioButtonId == maleRadioButton.id
            val info = BasicInfo(
                profileImage = viewModel.profileImageUri!!,
                firstName = firstNameEditText.text.toString(),
                lastName = lastNameEditText.text.toString(),
                phoneNumber = phoneNumberEditText.text.toString(),
                email = emailEditText.text.toString(),
                isMale = isMale,
                password = passwordEditText.text.toString(),
                confirmPassword = confirmPasswordEditText.text.toString()
            )

            val action = UserBasicInfoFragmentDirections.toUserProfessionalInfoFragment(info)
            findNavController().navigate(action)
        }

    }

}