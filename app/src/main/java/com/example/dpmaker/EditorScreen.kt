package com.example.dpmaker

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.dpmaker.utils.UtilsString
import kotlinx.android.synthetic.main.activity_editor_screen.*


class EditorScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor_screen)

        val bitmap = UtilsString.img?.let { it1 -> UtilsString.save(it1) }!!
        edit_image.setImageBitmap(bitmap)

        val btnConfirm = findViewById<Button>(R.id.btn_confirm)
        btnConfirm.setOnClickListener{
            val intent = Intent(this, DesignFormat::class.java)
            startActivity(intent)
        }
        val btnChange = findViewById<Button>(R.id.btn_change)
        btnChange.setOnClickListener {
            finish()
        }
        val btnClose = findViewById<ImageButton>(R.id.btn_close2)
        btnClose.setOnClickListener {
            finish()
        }
    }

}