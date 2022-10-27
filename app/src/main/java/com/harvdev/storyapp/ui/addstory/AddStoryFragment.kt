package com.harvdev.storyapp.ui.addstory

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.harvdev.storyapp.R
import com.harvdev.storyapp.databinding.AddStoryFragmentBinding
import com.harvdev.storyapp.ui.home.HomeViewModel
import com.harvdev.storyapp.ui.utils.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import java.io.File
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AddStoryFragment : Fragment() {

    companion object {
        fun newInstance() = AddStoryFragment()
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    private val viewModel: AddStoryViewModel by viewModels {
        ViewModelFactory(requireContext())
    }
    private lateinit var previewImageView: ImageView
    private lateinit var btnUpload: Button
    private lateinit var btnOpenCamera: Button
    private lateinit var btnOpenGallery: Button
    private lateinit var edDescription: EditText

    private lateinit var currentPhotoPath: String

    private var getFile: File? = null

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    requireContext(),
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_story_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = AddStoryFragmentBinding.bind(view)

        initBinding(binding)
        initPermission()
//        initViewModel()

    }

    private fun initPermission(){
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

//    private fun initViewModel(){
//        viewModel = ViewModelProvider(this)[AddStoryViewModel::class.java]
//    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        activity?.let { intent.resolveActivity(it.packageManager) }

        createCustomTempFile(requireContext()).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                AUTHORITY_APPS,
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }
    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }


    private fun initBinding(binding: AddStoryFragmentBinding){
        previewImageView = binding.previewImageView
        btnUpload = binding.buttonAdd
        btnOpenCamera = binding.btnOpenCamera
        btnOpenGallery = binding.btnOpenGallery
        edDescription = binding.edAddDescription

        btnOpenCamera.setOnClickListener {
            startTakePhoto()
        }

        btnUpload.setOnClickListener {
            uploadImage()
        }

        btnOpenGallery.setOnClickListener {
            startGallery()
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile

            val result = BitmapFactory.decodeFile(getFile?.path)

            previewImageView.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireActivity())
            getFile = myFile
            previewImageView.setImageURI(selectedImg)
        }
    }

    private fun uploadImage() {
        if (getFile != null) {
            if (edDescription.text.toString() == ""){
                Toast.makeText(requireContext(), "Silakan isi deskripsi terlebih dahulu.", Toast.LENGTH_SHORT).show()
            } else {
                val file = reduceFileImage(getFile as File)

                val description = edDescription.text.toString().toRequestBody("text/plain".toMediaType())

                val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "photo",
                    file.name,
                    requestImageFile
                )

                viewModel.uploadImage(imageMultipart, description){ error, message ->
                    if (error == true){
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        safePopBackstack()
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), "Silakan masukkan berkas gambar terlebih dahulu.", Toast.LENGTH_SHORT).show()
        }
    }



}