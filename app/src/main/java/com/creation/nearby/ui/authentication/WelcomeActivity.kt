package com.creation.nearby.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.creation.nearby.R
import com.creation.nearby.animateFade
import com.creation.nearby.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity(),View.OnClickListener{

    lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginWithEmail.setOnClickListener(this)
        binding.signUp.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v){
            binding.loginWithEmail->{
                startActivity(Intent(this,LoginActivity::class.java))
                animateFade(this)
            }
            binding.signUp->{
                startActivity(Intent(this,SignUpActivity::class.java))
                animateFade(this)
            }
        }

    }
 }