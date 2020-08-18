package com.example.dpmaker


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dpmaker.adapter.ImageAdapter
import com.example.dpmaker.utils.UtilsString
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_images.*
import java.util.*

class ImagesActivity : AppCompatActivity() {
    private lateinit var mRecyclerView: RecyclerView
    private var mAdapter: ImageAdapter? = null
    private lateinit var mProgressCircle: ProgressBar
    private var mDatabaseRef: DatabaseReference? = null
    var mUploads: MutableList<String?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)
        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        mProgressCircle = findViewById(R.id.progress_circle)
        mUploads = ArrayList()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("images/")
        mDatabaseRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.i("DataSnapTest",""+dataSnapshot.value)
                for (postSnapshot in dataSnapshot.children) {
                    Log.i("postSnapshot",""+postSnapshot.value)
                    mUploads?.add(postSnapshot.value as String?)
                }
                mAdapter = mUploads?.let { ImageAdapter(this@ImagesActivity, it) }
                mRecyclerView.adapter = mAdapter
                mProgressCircle.visibility = View.INVISIBLE
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@ImagesActivity,databaseError.message, Toast.LENGTH_SHORT)
                    .show()
                mProgressCircle.visibility = View.INVISIBLE
            }
        })

        mRecyclerView.addOnItemTouchListener(RecyclerItemClickListenr
            (this, mRecyclerView, object :
            RecyclerItemClickListenr.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
//            UtilsString.pos = imgUrl[position]
                UtilsString.pos1 = mUploads?.get(position)
                Glide.with(this@ImagesActivity)
                    .load(UtilsString.pos1)
                    .centerCrop()
                    .into(main_image)
            }

            override fun onItemLongClick(view: View?, position: Int) {

            }
        }))
        val btnCreateDp = findViewById<Button>(R.id.btn_create_dp)
        btnCreateDp.setOnClickListener{
            if (main_image.drawable == null){
                Toast.makeText(this@ImagesActivity,
                    "Please select the frame" ,Toast.LENGTH_SHORT)
                    .show()
            } else{
                val intent = Intent(this, CreationPage::class.java)
                startActivity(intent)
            }
        }
    }
}