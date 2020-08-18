package com.example.dpmaker.utils

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.dpmaker.NewUpload

class UtilsString {
    companion object{
        var pos1: String? = null
        var img: RelativeLayout? = null
        var frameImg: ImageView? = null
        var data: Intent? = null
        var uri : Uri?  = null
        var pos : String? = null
        val PERCENTSYMBOL: Any? = "%"
        var slecetedImg : ImageView? = null

        fun save(v: View): Bitmap? {
            val b = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
            val c = Canvas(b)
            v.draw(c)
            return b
        }

    }
}