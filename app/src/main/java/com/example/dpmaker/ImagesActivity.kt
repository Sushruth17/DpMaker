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
    private val imgUrl: ArrayList<String> = ArrayList()
    private lateinit var mRecyclerView: RecyclerView
    private var mAdapter: ImageAdapter? = null
    private lateinit var mProgressCircle: ProgressBar
    private var mDatabaseRef: DatabaseReference? = null
//    var mUploads: MutableList<NewUpload?>? = null
    var mUploads: MutableList<String?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)
      /*  imgUrl.add("https://firebasestorage.googleapis.com/v0/b/dpmaker-21479.appspot.com/o/images%2F1.png?alt=media&token=87d8ebbe-5b30-4588-a3dc-b4fbc424047e")
        imgUrl.add("https://firebasestorage.googleapis.com/v0/b/dpmaker-21479.appspot.com/o/images%2F2.png?alt=media&token=31721349-9ecc-4ef1-b0ff-0768cbf20b24")
        imgUrl.add("https://firebasestorage.googleapis.com/v0/b/dpmaker-21479.appspot.com/o/images%2F3.png?alt=media&token=15033d35-de12-42aa-b438-bc2d1f7e4956")
        imgUrl.add("https://firebasestorage.googleapis.com/v0/b/dpmaker-21479.appspot.com/o/images%2F4.png?alt=media&token=8aadbcbc-abe7-428f-9bd3-57fe78fcc70a")
        imgUrl.add("https://firebasestorage.googleapis.com/v0/b/dpmaker-21479.appspot.com/o/images%2F5.png?alt=media&token=725d0f4a-dd2d-4f4b-b22f-f894951b947e")
*/

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
/*                    val upload = postSnapshot.getValue(NewUpload::class.java)
                    Log.i("upload", "-----------$upload")*/
                    mUploads?.add(postSnapshot.value as String?)

                }
                mAdapter = mUploads?.let { ImageAdapter(this@ImagesActivity, it) }
                mRecyclerView.adapter = mAdapter
                mProgressCircle.visibility = View.INVISIBLE
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@ImagesActivity, "Am here 43 "+databaseError.message, Toast.LENGTH_SHORT)
                    .show()
                mProgressCircle.visibility = View.INVISIBLE
            }
        })
//        mAdapter = MyAdapter(imgUrl,this@ImagesActivity)
//
//        mRecyclerView.adapter = mAdapter
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
            val intent = Intent(this, CreationPage::class.java)
//            intent.putExtra("URL",UtilsString.pos)
            startActivity(intent)
        }
    }
}