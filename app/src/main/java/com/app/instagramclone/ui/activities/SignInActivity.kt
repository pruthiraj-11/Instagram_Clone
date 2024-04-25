package com.app.instagramclone.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.instagramclone.R
import com.app.instagramclone.databinding.ActivitySignInBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySignInBinding.inflate(layoutInflater)
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

        binding.signinbtn.setOnClickListener {
            if ((binding.mail.editText?.text.toString() == "") or
                (binding.password.editText?.text.toString() == "")
            ) {
                Toast.makeText(this@SignInActivity,"All fields are mandatory.", Toast.LENGTH_SHORT).show()
            } else {
                Firebase.auth.signInWithEmailAndPassword(binding.mail.editText?.text.toString(),binding.password.editText?.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this@SignInActivity,"Login Successfully.",Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
                            finish()
                    } else {
                            Toast.makeText(this@SignInActivity, it.exception!!.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
            }
            }
        }
    }
}