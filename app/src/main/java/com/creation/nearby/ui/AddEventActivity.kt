package com.creation.nearby.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivityAddEventBinding
import com.creation.nearby.databinding.ImagePickerBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class AddEventActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var binding: ActivityAddEventBinding
    lateinit var dialogBinding: ImagePickerBottomSheetBinding
    lateinit var imagePickerDialog: BottomSheetDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imagePickerDialog = BottomSheetDialog(this,R.style.CustomBottomSheetDialogTheme)
        dialogBinding = ImagePickerBottomSheetBinding.inflate(layoutInflater,null,false)
        imagePickerDialog.setContentView(dialogBinding.root)
        imagePickerDialog.setCancelable(true)


        binding.selectImageRv.setOnClickListener(this)
        dialogBinding.selectCamera.setOnClickListener(this)
        dialogBinding.selectPhotoLibrary.setOnClickListener(this)
        dialogBinding.cancel.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when(v){

            binding.selectImageRv->{

                imagePickerDialog.show()

            }
            dialogBinding.selectCamera->{


            }
            dialogBinding.selectPhotoLibrary->{

            }
            dialogBinding.cancel->{
                imagePickerDialog.dismiss()
            }


        }

    }
}