package com.creation.nearby.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.creation.nearby.databinding.ActivityAlertBinding

class AlertActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var binding: ActivityAlertBinding

    private var isMessageClicked = false
    private var isFriendClicked = false
    private var isRequestClicked = false
/*    private var isTipsClicked = false
    private var isCameraClicked = false
    private var isLocationClicked = false*/
    private var isNotificationClicked = false
/*    private var isVoiceClicked = false*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.messageLayout.setOnClickListener(this)
        binding.newFriendLayout.setOnClickListener(this)
        binding.requestsLayout.setOnClickListener(this)
        binding.inAppNotificationLayout.setOnClickListener(this)
      /*  binding.tipsLayout.setOnClickListener(this)
        binding.locationLayout.setOnClickListener(this)
        binding.cameraLayout.setOnClickListener(this)
        binding.voiceLayout.setOnClickListener(this)*/
        binding.goback1.setOnClickListener(this)



    }

    override fun onClick(v: View?) {

        when(v){
            binding.messageLayout->{

                if (!isMessageClicked){
                    isMessageClicked = true
                    binding.emptyCheckBox1.visibility = View.GONE
                    binding.checkedCheckBox1.visibility = View.VISIBLE
                }else{
                    isMessageClicked = false
                    binding.emptyCheckBox1.visibility = View.VISIBLE
                    binding.checkedCheckBox1.visibility = View.GONE
                }
            }
            binding.newFriendLayout->{
                if (!isFriendClicked){
                    isFriendClicked = true
                    binding.emptyCheckBox2.visibility = View.GONE
                    binding.checkedCheckBox2.visibility = View.VISIBLE
                }else{
                    isFriendClicked = false
                    binding.emptyCheckBox2.visibility = View.VISIBLE
                    binding.checkedCheckBox2.visibility = View.GONE
                }
            }
            binding.requestsLayout->{
                if (!isRequestClicked){
                    isRequestClicked = true
                    binding.emptyCheckBox3.visibility = View.GONE
                    binding.checkedCheckBox3.visibility = View.VISIBLE
                }else{
                    isRequestClicked = false
                    binding.emptyCheckBox3.visibility = View.VISIBLE
                    binding.checkedCheckBox3.visibility = View.GONE
                }
            }
            binding.inAppNotificationLayout->{
                if (!isNotificationClicked){
                    isNotificationClicked = true
                    binding.emptyCheckBox4.visibility = View.GONE
                    binding.checkedCheckBox4.visibility = View.VISIBLE
                }else{
                    isNotificationClicked = false
                    binding.emptyCheckBox4.visibility = View.VISIBLE
                    binding.checkedCheckBox4.visibility = View.GONE
                }
            }
          /*  binding.tipsLayout->{
                if (!isTipsClicked){
                    isTipsClicked = true
                    binding.emptyCheckBox5.visibility = View.GONE
                    binding.checkedCheckBox5.visibility = View.VISIBLE
                }else{
                    isTipsClicked = false
                    binding.emptyCheckBox5.visibility = View.VISIBLE
                    binding.checkedCheckBox5.visibility = View.GONE
                }
            }
            binding.locationLayout->{
                if (!isLocationClicked){
                    isLocationClicked = true
                    binding.emptyCheckBox6.visibility = View.GONE
                    binding.checkedCheckBox6.visibility = View.VISIBLE
                }else{
                    isLocationClicked = false
                    binding.emptyCheckBox6.visibility = View.VISIBLE
                    binding.checkedCheckBox6.visibility = View.GONE
                }
            }
            binding.cameraLayout->{
                if (!isCameraClicked){
                    isCameraClicked = true
                    binding.emptyCheckBox7.visibility = View.GONE
                    binding.checkedCheckBox7.visibility = View.VISIBLE
                }else{
                    isCameraClicked = false
                    binding.emptyCheckBox7.visibility = View.VISIBLE
                    binding.checkedCheckBox7.visibility = View.GONE
                }
            }
            binding.voiceLayout->{
                if (!isVoiceClicked){
                    isVoiceClicked = true
                    binding.emptyCheckBox8.visibility = View.GONE
                    binding.checkedCheckBox8.visibility = View.VISIBLE
                }else{
                    isVoiceClicked = false
                    binding.emptyCheckBox8.visibility = View.VISIBLE
                    binding.checkedCheckBox8.visibility = View.GONE
                }
            }
*/
            binding.goback1->{

                onBackPressed()
                }
            }
        }

}