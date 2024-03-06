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
import com.example.neostartask.databinding.FragmentUserProfessionalInfoBinding
import com.example.neostartask.model.EducationInfo
import com.example.neostartask.model.ProfessionalInfo
import com.example.neostartask.utils.getQualificationDropDownData
import com.example.neostartask.utils.getStateDropDownData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfessionalInfoFragment : Fragment(R.layout.fragment_user_professional_info) {
    private var _binding: FragmentUserProfessionalInfoBinding? = null
    private val binding: FragmentUserProfessionalInfoBinding get() = _binding!!
    private val viewModel: UserProfessionalInfoViewModel by viewModels()

    val args: UserProfessionalInfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUserProfessionalInfoBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            previousButton.setOnClickListener {
                findNavController().popBackStack()
            }

            nextButton.setOnClickListener {
                validateUserInput()
            }

            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                getQualificationDropDownData()
            )
            educationEditText.setAdapter(adapter)

            resetValidationErrors()
        }
    }

    private fun resetValidationErrors() {
        with(binding) {
            educationEditText.doOnTextChanged { _, _, _, _ ->
                educationInputLayout.error = null
            }


            yearOfPassingEditText.doOnTextChanged { _, _, _, _ ->
                yearPassingInputLayout.error = null
            }
        }
    }

    private fun validateUserInput() {
        with(binding) {

            /* Qualification validation */
            val qualificationValidation = viewModel.validateQualification(
                educationEditText.text.toString()
            )

            if (qualificationValidation.first) {
                educationInputLayout.error = null
            } else {
                educationInputLayout.error = qualificationValidation.second
                return
            }

            /* Year of Passing validation */
            val yearOfPassingValidation = viewModel.validateYearOfPassing(
                yearOfPassingEditText.text.toString()
            )

            if (yearOfPassingValidation.first) {
                yearPassingInputLayout.error = null
            } else {
                yearPassingInputLayout.error = yearOfPassingValidation.second
                return
            }

            /*val universityValidation = viewModel.validateUniversity(
                universityEditText.text.toString()
            )

            if (universityValidation.first) {
                universityInputLayout.error = null
            } else {
                universityInputLayout.error = universityValidation.second
                return
            }

            val gradeValidation = viewModel.validateGrade(
                gradeEditText.text.toString()
            )

            if (gradeValidation.first) {
                gradeInputLayout.error = null
            } else {
                gradeInputLayout.error = gradeValidation.second
                return
            }*/

            /*val experienceValidation = viewModel.validateExperience(
                experienceEditText.text.toString()
            )

            if (experienceValidation.first) {
                experienceInputLayout.error = null
            } else {
                experienceInputLayout.error = experienceValidation.second
                return
            }

            val designationValidation = viewModel.validateDesignation(
                designationEditText.text.toString()
            )

            if (designationValidation.first) {
                designationInputLayout.error = null
            } else {
                designationInputLayout.error = designationValidation.second
                return
            }

            val domainValidation = viewModel.validateDomain(
                domainEditText.text.toString()
            )

            if (domainValidation.first) {
                domainInputLayout.error = null
            } else {
                domainInputLayout.error = domainValidation.second
                return
            }*/

            saveUserInfo()
        }
    }

    private fun saveUserInfo() {

        with(binding) {

            val educationInfo = EducationInfo(
                education = educationEditText.text.toString(),
                yearOfPassing = yearOfPassingEditText.text.toString().toInt(),
                university = universityEditText.text.toString(),
                grade = gradeEditText.text.toString()
            )

            val professionalInfo = ProfessionalInfo(
                experience = experienceEditText.text.toString().toInt(),
                designation = designationEditText.text.toString(),
                domain = domainEditText.text.toString()
            )

            val action = UserProfessionalInfoFragmentDirections.toUserAddressFragment(
                args.basicInfo,
                educationInfo,
                professionalInfo
            )
            findNavController().navigate(action)
        }

    }
}