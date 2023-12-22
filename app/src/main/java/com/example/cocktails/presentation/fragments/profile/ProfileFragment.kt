package com.example.cocktails.presentation.fragments.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.cocktails.Constants
import com.example.cocktails.R
import com.example.cocktails.databinding.FragmentProfileBinding
import com.example.cocktails.presentation.activities.AuthActivity
import com.example.cocktails.presentation.fragments.profile.viewmodel.ProfileViewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val passwordPatter: Regex = Regex("^(?=.*[A-Za-z])(?=.*\\d).{6,}\$")

    private val profileViewModel: ProfileViewModel by hiltNavGraphViewModels(R.id.profileFragment)

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var email: String
    private val permissionRequestCode = 123


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = requireActivity().intent
        email = intent.getStringExtra(Constants.emailKey).toString()
        initUI()
        initListeners()
    }

    private fun initUI() {
        if (profileViewModel.loadImageUriFromSharedPref() == null) {
            binding.profileImage.setImageResource(R.drawable.baseline_person_24)
        } else {
            displayImage()
        }
        binding.email.setText(email)
        binding.name.setText(profileViewModel.getValueFromSharedPref(Constants.nameKey, email))
        binding.password.setText(
            profileViewModel.getValueFromSharedPref(
                Constants.passwordKey,
                email
            )
        )
    }

    private fun initListeners() {
        binding.logout.setOnClickListener {
            profileViewModel.removeFromSharedPref()
            val intent = Intent(activity, AuthActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        binding.profileImage.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            changeImage.launch(pickImg)
        }

        binding.editName.setOnCheckedChangeListener { _, isChecked ->
            updateName(isChecked)
        }
        binding.editPassword.setOnCheckedChangeListener { _, isChecked ->
            updatePassword(isChecked)
            if (binding.editPassword.error?.toString().isNullOrBlank()) {
                binding.editPassword.visibility = View.VISIBLE
            }
        }
    }

    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                binding.profileImage.setImageURI(imgUri)
                imgUri?.let { uri ->
                    profileViewModel.saveImageUriToSharedPref(uri)
                }
            }
        }

    private fun displayImage() {
        if (activity?.let {
                ContextCompat.checkSelfPermission(
                    activity?.applicationContext!!,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            } == PackageManager.PERMISSION_GRANTED) {
            val savedUri = profileViewModel.loadImageUriFromSharedPref()
            savedUri?.let {
                binding.profileImage.setImageURI(it)
            }
        } else {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), permissionRequestCode
                )
            }
        }

    }

    private fun updateName(isChecked: Boolean) {
        if (isChecked) {
            binding.editName.setBackgroundResource(R.drawable.baseline_done)
            binding.name.isEnabled = true
            binding.name.requestFocus()
        } else {

            binding.editName.setBackgroundResource(R.drawable.baseline_edit)
            profileViewModel.updateValueInSharedPref(
                Constants.nameKey,
                email,
                binding.name.text.toString()
            )
            binding.name.isEnabled = false
            binding.name.clearFocus()
        }
    }

    private fun updatePassword(isChecked: Boolean) {
        if (isChecked) {
            binding.editPassword.setBackgroundResource(R.drawable.baseline_done)
            binding.password.isEnabled = true
            binding.password.requestFocus()
        } else {
            val enteredPassword = binding.password.text.toString().trim()

            if (!checkPassword(enteredPassword)) {
                binding.editPassword.visibility = View.GONE
                binding.password.error = resources.getString(R.string.invalid_password)
                binding.editPassword.isChecked = false
            } else {
                profileViewModel.updateValueInSharedPref(
                    Constants.passwordKey,
                    email,
                    enteredPassword
                )
                binding.password.isEnabled = false
                binding.password.clearFocus()
            }
        }
    }

    private fun checkPassword(password: String): Boolean {
        return password.matches(passwordPatter) && password.isNotEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}