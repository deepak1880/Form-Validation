package com.example.neostartask.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.neostartask.R
import com.example.neostartask.databinding.FragmentUserAddressBinding
import com.example.neostartask.model.Address
import com.example.neostartask.utils.getStateDropDownData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserAddressFragment : Fragment(R.layout.fragment_user_address) {
    private var _binding: FragmentUserAddressBinding? = null
    private val binding: FragmentUserAddressBinding get() = _binding!!

    private val viewModel: UserAddressViewModel by viewModels()

    val args: UserAddressFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUserAddressBinding.bind(view)
        initView()
    }

    private fun initView() {
        binding.submitButton.setOnClickListener {
            validateUserInputs()
        }

        resetValidationErrors()

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            getStateDropDownData()
        )
        binding.stateEditText.setAdapter(adapter)

    }

    private fun resetValidationErrors() {
        with(binding) {
            addressEditText.doOnTextChanged { _, _, _, _ ->
                addressInputLayout.error = null
            }


            landmarkEditText.doOnTextChanged { _, _, _, _ ->
                landmarkInputLayout.error = null
            }


            stateEditText.doOnTextChanged { _, _, _, _ ->
                stateInputLayout.error = null
            }
        }

    }

    private fun validateUserInputs() {

        with(binding) {
            val addressValidation = viewModel.validateAddress(addressEditText.text.toString())

            if (addressValidation.first) {
                addressInputLayout.error = null
            } else {
                addressInputLayout.error = addressValidation.second
                return
            }

            val landmarkValidation = viewModel.validateLandmark(landmarkEditText.text.toString())

            if (landmarkValidation.first) {
                landmarkInputLayout.error = null
            } else {
                landmarkInputLayout.error = landmarkValidation.second
                return
            }

//            val cityValidation = viewModel.validateCity(cityEditText.text.toString())
//
//            if (cityValidation.first) {
//                addressInputLayout.error = null
//            } else {
//                addressInputLayout.error = cityValidation.second
//                return
//            }

            val stateValidation = viewModel.validateState(addressEditText.text.toString())

            if (stateValidation.first) {
                stateInputLayout.error = null
            } else {
                stateInputLayout.error = stateValidation.second
                return
            }

//            val pinCodeValidation = viewModel.validatePinCode(pinCodeEditText.text.toString())
//
//            if (pinCodeValidation.first) {
//                pinCodeInputLayout.error = null
//            } else {
//                pinCodeInputLayout.error = pinCodeValidation.second
//                return
//            }

            saveUserInfo()
        }
    }

    private fun saveUserInfo() {
        with(binding) {
            val address = Address(
                address = addressEditText.text.toString(),
                landmark = landmarkEditText.text.toString(),
                pinCode = pinCodeEditText.text.toString(),
                city = cityEditText.text.toString(),
                state = stateEditText.text.toString()
            )

            //Just save it

            val action = UserAddressFragmentDirections.toRegistrationSuccessFragment(
                args.BasicInfo, args.educationalInfo, args.professionalInfo, address
            )
            findNavController().navigate(action)
        }
    }
}