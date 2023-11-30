package com.example.praktekujikom

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.praktekujikom.databinding.ActivityEditDataBinding

class EditDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditDataBinding

    companion object {
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_NIP = "extra_nip"
        const val EXTRA_GENDER = "extra_gender"
        const val EXTRA_TANGGAL = "extra_tanggal"
        const val EXTRA_TEMPAT_LAHIR = "extra_tempat_lahir"
        const val EXTRA_FOTO = "extra_foto"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nama = intent.getStringExtra(EXTRA_NAMA)
        val nip = intent.getStringExtra(EXTRA_NIP)
        val gender = intent.getStringExtra(EXTRA_GENDER)
        val tanggal = intent.getStringExtra(EXTRA_TANGGAL)
        val tempatLahir = intent.getStringExtra(EXTRA_TEMPAT_LAHIR)
        val foto = Uri.parse(intent.getStringExtra(EXTRA_FOTO))

        binding.imageView2.setImageURI(foto)
        binding.inputnama.setText(nama)
        binding.inputnip.setText(nip)
        binding.inputgender.setText(gender)
        binding.inputdate.setText(tanggal)
        binding.alamatt.setText(tempatLahir)

        binding.imageButton.setOnClickListener {
            startGallery()
        }

        binding.switch1.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                binding.inputnama.isEnabled = true
                binding.inputnip.isEnabled = true
                binding.inputdate.isEnabled = true
                binding.inputgender.isEnabled = true
                binding.alamatt.isEnabled = true
            } else {
                binding.inputnama.isEnabled = false
                binding.inputnip.isEnabled = false
                binding.inputdate.isEnabled = false
                binding.inputgender.isEnabled = false
                binding.alamatt.isEnabled = false
            }
        }


    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                binding.imageView2.setImageURI(uri)
            }
        }
    }
}