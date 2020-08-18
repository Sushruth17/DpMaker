package com.example.dpmaker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.dpmaker.utils.UtilsString
import kotlinx.android.synthetic.main.activity_creation_page.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CreationPage : AppCompatActivity() {

    private var bitmap : Bitmap? = null
    var image_uri: Uri? = null
    var bmOptions = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creation_page)

        UtilsString.img = rl_img_creation_page

//        val imgUrl_pos = intent.getStringExtra("URL") ?: "Null"
        val imgView = findViewById<ImageView>(R.id.frame_image)

        Glide.with(this)
            .load(UtilsString.pos1)
            .into(imgView)

        UtilsString.frameImg = imgView
        val btnUpload = findViewById<Button>(R.id.btn_upload)
        btnUpload.setOnClickListener {
/*            val intent = Intent(this, EditorScreen::class.java)
            startActivity(intent)*/

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
        val btnCamera = findViewById<Button>(R.id.btn_click)
        btnCamera.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                // Ensure that there's a camera activity to handle the intent
                takePictureIntent.resolveActivity(packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }
                    // Continue only if the File was successfully created
                    //com.example.android.fileprovider
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "com.example.dpmaker",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, 2)
                    }
                }
            }
        }
        val btnEdit = findViewById<Button>(R.id.btn_edit)
        btnEdit.setOnClickListener {
            val intent = Intent(this, EditorScreen::class.java)
            startActivity(intent)
        }
        val btnSave = findViewById<Button>(R.id.btn_save)
        btnSave.setOnClickListener {
            val intent = Intent(this, DesignFormat::class.java)
            startActivity(intent)
        }

        val btnClose = findViewById<ImageButton>(R.id.btn_close)
        btnClose.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UtilsString.data = data
        val selectedImg = findViewById<ImageView>(R.id.selected_image)
        if (resultCode == Activity.RESULT_OK ) {
            if (requestCode == 1) {
                UtilsString.uri = data?.data
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, UtilsString.uri)
                selectedImg.setImageBitmap(bitmap)
                photo_here_txt.visibility = View.GONE
            }
            else if (requestCode == 2  && data!=null){
            val imageBitmap = data.extras?.get("data") as Bitmap
                selectedImg.setImageBitmap(imageBitmap)
                photo_here_txt.visibility = View.GONE
            }
            UtilsString.slecetedImg = selectedImg
        }
    }
    lateinit var currentPhotoPath: String

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }
}

