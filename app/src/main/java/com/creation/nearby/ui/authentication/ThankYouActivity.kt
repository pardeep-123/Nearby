package com.creation.nearby.ui.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.creation.nearby.R
import com.creation.nearby.animateSlide
import com.creation.nearby.ui.MainActivity
import kotlinx.android.synthetic.main.activity_thank_you.*

class ThankYouActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thank_you)

        confirmCodeBtn?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            animateSlide(this)
            finishAffinity()
        }
    }
}