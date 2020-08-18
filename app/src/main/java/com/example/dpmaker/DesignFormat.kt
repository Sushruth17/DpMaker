package com.example.dpmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dpmaker.utils.UtilsString
import kotlinx.android.synthetic.main.activity_design_format.*
import java.io.File
import java.io.FileOutputStream


class DesignFormat : AppCompatActivity(),AdapterView.OnItemSelectedListener {
    var progressVal: Int = 100
    var selectedFormat : String = "jpg"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_design_format)
        val seek = findViewById<SeekBar>(R.id.seekBar1)
        val seekBarValue = findViewById<TextView>(R.id.seek_txt)
        seek?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                seekBarValue.text = seek.progress.toString() + UtilsString.PERCENTSYMBOL
                progressVal = seek.progress
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seek: SeekBar) {
                progressVal = seek.progress
                Log.i("progress","seekBar---------->"+progressVal)
            }
        })

        val spinnerImg: Spinner? = findViewById(R.id.spinner)
        if (spinnerImg != null) {
            spinnerImg.onItemSelectedListener = this
        }

        applicationContext.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.image_format_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                if (spinnerImg != null) {
                    spinnerImg.adapter = adapter
                }

            }
        }

        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            finish()
        }

        val btnSave = findViewById<Button>(R.id.btn_save_fin)
        btnSave.setOnClickListener {
            val file: Bitmap = UtilsString.img?.let { it1 -> UtilsString.save(it1) }!!
            var outStream: FileOutputStream? = null
            val sdCard: File = Environment.getExternalStorageDirectory()
            val dir = File(sdCard.absolutePath.toString() + "/Pictures")
            dir.mkdirs()
            val fileName =
                String.format("%d."+selectedFormat, System.currentTimeMillis())
            val outFile = File(dir, fileName)
            outStream = FileOutputStream(outFile)
            file.compress(Bitmap.CompressFormat.JPEG,
                progressVal, outStream)
            outStream.flush()
            outStream.close()
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            intent.data = Uri.fromFile(outFile)
            sendBroadcast(intent)
            Toast.makeText(this@DesignFormat, "Successfully saved"
                    ,Toast.LENGTH_SHORT).show()
            finish()
        }


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedFormat = spinner.selectedItem.toString().toLowerCase()
    }

  }