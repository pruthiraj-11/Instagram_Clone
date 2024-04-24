package com.app.instagramclone.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.instagramclone.Models.User
import com.app.instagramclone.R
import com.app.instagramclone.Utils.USER_NODE
import com.app.instagramclone.Utils.uploadImage
import com.app.instagramclone.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }
    lateinit var user: User
    private val launcher=registerForActivityResult(ActivityResultContracts.GetContent()){uri->
        uri?.let {
            uploadImage(uri, USER_NODE){
                if (it != null) {
                    if (it.isNotEmpty()) {
                        user.profileimage=it
                        binding.profileImage.setImageURI(uri)
                    }
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        user= User()
        binding.signupbtn.setOnClickListener {
            if ((binding.name.editText?.text.toString() == "") or
                (binding.mail.editText?.text.toString() == "") or
                (binding.password.editText?.text.toString() == "")
            ) {
                Toast.makeText(this@SignupActivity,"All fields are mandatory.",Toast.LENGTH_SHORT).show()
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.mail.editText?.text.toString(),binding.password.editText?.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            user.name= binding.name.editText?.text.toString()
                            user.email= binding.mail.editText?.text.toString()
                            user.password= binding.password.editText?.text.toString()
                            FirebaseDatabase.getInstance().getReference().child("Users").child(Firebase.auth.currentUser!!.uid).setValue(user).addOnSuccessListener {
                                Toast.makeText(this@SignupActivity,"Signup Successfully.",Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@SignupActivity,HomeActivity::class.java))
                                finish()
                            }
                        } else {
                            Toast.makeText(this@SignupActivity,it.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        binding.pickImage.setOnClickListener {
            try {
                launcher.launch("image/*")
            } catch (ex) {

            }

        }

        binding.existingUser.setOnClickListener {
            startActivity(Intent(this@SignupActivity,SignInActivity::class.java))
        }
    }
}