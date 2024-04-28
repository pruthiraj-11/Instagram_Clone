package com.app.instagramclone.ui.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.instagramclone.models.User
import com.app.instagramclone.R
import com.app.instagramclone.utils.USER_NODE
import com.app.instagramclone.utils.uploadImage
import com.app.instagramclone.databinding.ActivitySignupBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }
    private lateinit var user: User
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
        if (intent.hasExtra("MODE")) {
            if (intent.getIntExtra("MODE",-1)==1) {
                binding.signupbtn.text="Update Profile"
                binding.existingUser.visibility= View.GONE
                FirebaseDatabase.getInstance().getReference().child(USER_NODE).child(Firebase.auth.currentUser!!.uid)
                    .addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val user: User? = snapshot.getValue(User::class.java)
                            if (!user?.profileimage.isNullOrEmpty()) {
                                Glide.with(this@SignupActivity).load(user?.profileimage).error(R.drawable.user).into(binding.profileImage)
                            }
                            binding.name.editText?.setText(user?.name)
                            binding.mail.editText?.setText(user?.email)
                            binding.password.editText?.setText(user?.password)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@SignupActivity,error.message,Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }
        val text="<font color=#000000>Already Have Account?</font> <font color=#1E88E5>Login</font>"
        binding.existingUser.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        binding.signupbtn.setOnClickListener {
            if (intent.hasExtra("MODE")) {
                if (intent.getIntExtra("MODE",-1)==1) {
                    FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(Firebase.auth.currentUser!!.uid).setValue(user).addOnCompleteListener {
                            startActivity(Intent(this@SignupActivity, HomeActivity::class.java))
                            finish()
                        }
                }
            } else {
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
                                    Toast.makeText(this@SignupActivity,"Registered Successfully.",Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@SignupActivity, HomeActivity::class.java))
                                    finish()
                                }
                            } else {
                                Toast.makeText(this@SignupActivity,it.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }

        binding.pickImage.setOnClickListener {
            try {
                launcher.launch("image/*")
            } catch (exception: ActivityNotFoundException) {
                Toast.makeText(this@SignupActivity,exception.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        }

        binding.existingUser.setOnClickListener {
            startActivity(Intent(this@SignupActivity, SignInActivity::class.java))
            finish()
        }
    }
}