package com.example.praktekujikom

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.praktekujikom.databinding.ActivityMainBinding
import java.io.File
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var getFile: Uri? = null
    private var tanggal: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPickDate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.btnPickPhoto.setOnClickListener {
            startGallery()
        }

        binding.btnAdd.setOnClickListener {
            var nama = binding.editTextTextPersonName2.text.toString()
            var nip = binding.editTextTextPersonName3.text.toString()

            val laki = binding.radioButton
            val perempuan = binding.radioButton2
            var selectedID = binding.radio.checkedRadioButtonId
            var jenisKelamin = if (selectedID == laki.id) {
                "Laki - laki"
            } else {
                "Perempuan"
            }

            var tempatLahir = binding.pob.text.toString()

            startActivity(
                Intent(this, EditDataActivity::class.java)
                    .putExtra(EditDataActivity.EXTRA_NAMA, nama)
                    .putExtra(EditDataActivity.EXTRA_NIP, nip)
                    .putExtra(EditDataActivity.EXTRA_GENDER, jenisKelamin)
                    .putExtra(EditDataActivity.EXTRA_TANGGAL, tanggal)
                    .putExtra(EditDataActivity.EXTRA_TEMPAT_LAHIR, tempatLahir)
                    .putExtra(EditDataActivity.EXTRA_FOTO, getFile.toString())
            )
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
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
//                val myFile = uriToFile(uri, this)
                getFile = uri
                binding.ivPreviewImage.setImageURI(uri)
            }
        }
    }

    fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                // Lakukan sesuatu dengan tanggal yang dipilih
                val selectedDate = "$selectedDay-${selectedMonth + 1}-$selectedYear"
                // Tampilkan atau gunakan tanggal yang dipilih
                // Misalnya, set pada TextView atau lakukan sesuatu yang lain
                binding.dateview.text = selectedDate
                tanggal = selectedDate
            },
            year,
            month,
            day
        )

        datePickerDialog.show()

    }
}