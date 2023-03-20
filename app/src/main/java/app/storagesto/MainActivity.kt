package app.storagesto

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import app.storagesto.databinding.ActivityMainBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MainActivity : AppCompatActivity(){
    var path: Uri? = null
    var bytesP : ByteArray? = null
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val storage = Firebase.storage
        val ref  = storage.reference
        binding.get.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            startActivityForResult(intent, 100)
        }
        binding.upload.setOnClickListener {
            val inputStream = contentResolver.openInputStream(path!!)
            bytesP = inputStream!!.readBytes()
            uploadPDF(bytesP!!)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100){
            path = data!!.data
        }
    }
    private fun uploadPDF(bytes: ByteArray) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val pdfRef = storageRef.child("pdfs/myfile.pdf")

        pdfRef.putBytes(bytes).addOnSuccessListener {
        }
    }
}