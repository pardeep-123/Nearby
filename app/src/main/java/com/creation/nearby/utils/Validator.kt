package com.creation.nearby.utils

import android.text.TextUtils
import android.util.Patterns
import com.creation.nearby.R
import com.creation.nearby.base.AppController
import java.util.regex.Pattern

class Validator() {
    //*email id pattern
    private fun isValidEmailId(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

    fun validateLogin(email: String?, password: String?): Boolean {
        return if (TextUtils.isEmpty(email)) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_email_address)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_valid_email_address)
            false
        } else if (TextUtils.isEmpty(password)) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_enter_password)
            false
        } else true
    }


    fun validateLogin(phone: String?): Boolean {
        return if (TextUtils.isEmpty(phone)) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_phone_address)
            false
        }  else true
    }

    // validation for conatct us
    fun validateContact(message: String?): Boolean {
        return if (TextUtils.isEmpty(message)) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.enter_message)
            false
        } else true
    }

    // validation for conatct us
    fun validateFeedBack(message: String?,image:String): Boolean {
        return when {
            TextUtils.isEmpty(message) -> {
                ErrorMessage =
                    AppController.mInstance.getString(R.string.enter_message)
                false
            }
            TextUtils.isEmpty(image) -> {
                ErrorMessage =
                    AppController.mInstance.getString(R.string.chooseimage)
                false
            }
            else -> true
        }
    }

    fun validateRegister(
        mfirstName: String,
        mlastName: String,
        mEmail: String,
        mPassword: String,
        mConfirmPassword: String,
        location: String
    ): Boolean {
        return  if (TextUtils.isEmpty(mfirstName)) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_name)
            false
        } else if (TextUtils.isEmpty(mlastName)) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.last_msg_name)
            false
        }
        else if (TextUtils.isEmpty(mEmail)) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_email_address)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_valid_email_address)
            false
        } else if (TextUtils.isEmpty(mPassword)) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_enter_password)
            false
        } else if (mPassword.length < 6) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_password_6)
            false
        } else if (TextUtils.isEmpty(mConfirmPassword)) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_please_enter_confirm_password)
            false
        } else if (mPassword != mConfirmPassword) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_password_not_match)
            false
        }else if (TextUtils.isEmpty(location)) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_enter_location)
            false
        }
        else true
    }

    fun validateCompleteSignup(
        image: String,
        mName: String,
        location: String,
        bio: String,
        experience: String,
        generes: Int,
        haveAudio: Boolean,
        haveVideo: Boolean,
        webLink: String,
        haveTrack: Boolean
    ): Boolean {
        return if (TextUtils.isEmpty(image)) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.txt_validate_upload_your_image)
            false
        } else if (TextUtils.isEmpty(mName)) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_name)
            false
        } else if (TextUtils.isEmpty(location)) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_select_address)
            false
        } else if (TextUtils.isEmpty(bio)) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_enter_bio)
            false
        } else if (TextUtils.isEmpty(experience)) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_enter_experience)
            false
        } else if (generes == 0) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_select_generes_type)
            false
        } else if (!haveAudio) {
            ErrorMessage =
                "Please add atleast one audio file"
            false
        } else if (!haveVideo) {
            ErrorMessage =
                "Please add atleast one video file"
            false
        } else if (TextUtils.isEmpty(webLink)) {
            ErrorMessage =
                "Please add your weblink"
            false
        } else if (!haveTrack) {
            ErrorMessage =
                "Please add atleast your one track link"
            false
        } else true
    }

    fun validateEmail(email: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_email_address)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_valid_email_address)
            false
        } else true
    }

    // add event validation

    fun validateAddEvent(
        title: String,
        details: String,
        time: String,
        location: String,
        image: String): Boolean {
        return when {
            TextUtils.isEmpty(title) -> {
                ErrorMessage =
                    AppController.mInstance.getString(R.string.title_empty)
                false
            }
            TextUtils.isEmpty(details) -> {
                ErrorMessage =
                    AppController.mInstance.getString(R.string.details_empty)
                false
            }

            TextUtils.isEmpty(time) -> {
                ErrorMessage =
                    AppController.mInstance.getString(R.string.time_empty)
                false
            }
            TextUtils.isEmpty(location) -> {
                ErrorMessage =
                    AppController.mInstance.getString(R.string.location)
                false
            }
            TextUtils.isEmpty(image) -> {
                ErrorMessage =
                    AppController.mInstance.getString(R.string.chooseimage)
                false
            }
            else -> true
        }
    }

    fun validateChangePassword(
        oldPassword: String?,
        newPassword: String?,
        confirmPassword: String?
    ): Boolean {
        return if (TextUtils.isEmpty(oldPassword)) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_enter_old_password)
            false
        } else if (TextUtils.isEmpty(newPassword)) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_enter_new_password)
            false
        } else if (newPassword!!.length < 6) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_password_6)
            false
        } else if (TextUtils.isEmpty(confirmPassword)) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_please_enter_confirm_password)
            false
        } else if (newPassword != confirmPassword) {
            ErrorMessage =
                AppController.mInstance.getString(R.string.msg_password_not_match)
            false
        } else true
    }

//    fun validateAddBank(
//        iban: String?,
//        code: String?,
//        name: String?,
//        accNumber: String?,
//        confirmAccNumber: String?,
//        isTermsChecked: Boolean
//    ): Boolean {
//        return if (TextUtils.isEmpty(iban)) {
//            ErrorMessage =
//                AppController.mInstance.getString(R.string.msg_enter_iban)
//            false
//        } else if (TextUtils.isEmpty(code)) {
//            ErrorMessage =
//                AppController.mInstance.getString(R.string.msg_enter_code)
//            false
//        } else if (TextUtils.isEmpty(name)) {
//            ErrorMessage =
//                AppController.mInstance.getString(R.string.msg_enter_holder_name)
//            false
//        } else if (TextUtils.isEmpty(accNumber)) {
//            ErrorMessage =
//                AppController.mInstance.getString(R.string.msg_enter_acc_number)
//            false
//        } else if (accNumber!!.length < 12) {
//            ErrorMessage =
//                AppController.mInstance.getString(R.string.msg_account_number_size)
//            false
//        } else if (TextUtils.isEmpty(confirmAccNumber)) {
//            ErrorMessage =
//                AppController.mInstance.getString(R.string.msg_enter_confirm_acc_number)
//            false
//        } else if (accNumber != confirmAccNumber) {
//            ErrorMessage =
//                AppController.mInstance.getString(R.string.msg_acc_number_not_match)
//            false
//        } else if (!isTermsChecked) {
//            ErrorMessage =
//                "Please accept Terms & Conditions"
//            false
//        } else true
//    }


//    fun validateAddEvent(
//        hasImages: Boolean,
//        eventType: Int,
//        artistType: Int,
//        venue: String,
//        location: String,
//        Date: String,
//        time: String,
//        budget: String
//    ): Boolean {
//        return if (eventType == 0) {
//            ErrorMessage =
//                AppController.mInstance.getString(R.string.msg_select_event_type)
//            false
//        } else if (artistType == 0) {
//            ErrorMessage =
//                AppController.mInstance.getString(R.string.msg_select_artist_type)
//            false
//        } else if (TextUtils.isEmpty(venue)) {
//            ErrorMessage =
//                AppController.mInstance.getString(R.string.msg_venue)
//            false
//        } else if (TextUtils.isEmpty(location)) {
//            ErrorMessage =
//                AppController.mInstance.getString(R.string.msg_select_address)
//            false
//        } else if (TextUtils.isEmpty(Date)) {
//            ErrorMessage =
//                AppController.mInstance.getString(R.string.msg_date)
//            false
//        } else if (TextUtils.isEmpty(time)) {
//            ErrorMessage =
//                AppController.mInstance.getString(R.string.msg_time)
//            false
//        } else if (TextUtils.isEmpty(budget)) {
//            ErrorMessage =
//                AppController.mInstance.getString(R.string.msg_budget)
//            false
//        } else if (!hasImages) {
//            ErrorMessage =
//                AppController.mInstance.getString(R.string.txt_upload_your_image)
//            false
//        } else true
//    }

    fun validateAddCard(
        name: String,
        number: String,
        month: String,
        year: String,
        cvv: String
    ): Boolean {
        return if (TextUtils.isEmpty(name)) {
            ErrorMessage =
                "Please enter card holder name"
            false
        } else if (TextUtils.isEmpty(number)) {
            ErrorMessage =
                "Please enter card number"
            false
        } else if (number.length < 16) {
            ErrorMessage =
                "Card number must be of 16 digits"
            false
        } else if (TextUtils.isEmpty(month)) {
            ErrorMessage =
                "Please select expiry month"
            false
        } else if (TextUtils.isEmpty(year)) {
            ErrorMessage =
                "Please select expiry year"
            false
        } else if (TextUtils.isEmpty(cvv)) {
            ErrorMessage =
                "Please enter cvv"
            false
        } else true
    }

    fun validateCardSelected(
        name: String

    ): Boolean {
        return if (TextUtils.isEmpty(name)) {
            ErrorMessage =
                "Please select card"
            false
        } else true
    }

    fun validateAddReview(
        rating: String,
        message: String

    ): Boolean {
        return if (TextUtils.isEmpty(rating)) {
            ErrorMessage =
                "Please give rating"
            false
        } else if (TextUtils.isEmpty(message)) {
            ErrorMessage =
                "Please enter reviews"
            false
        } else true
    }
    /*public boolean validateProfile(@NotNull String email, @NotNull String mName) {
        if (TextUtils.isEmpty(mName)) {
            ErrorMessage = AppController.getInstance().getString(R.string.msg_name);
            return false;
        } else if (TextUtils.isEmpty(email)) {
            ErrorMessage = AppController.getInstance().getString(R.string.msg_email_address);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ErrorMessage = AppController.getInstance().getString(R.string.msg_valid_email_address);
            return false;
        } else return true;
    }

    public boolean validatePassword(@NotNull String mOld, @NotNull String mNew, @NotNull String mConfirm) {
        if (TextUtils.isEmpty(mOld)) {
            ErrorMessage = AppController.getInstance().getString(R.string.msg_enter_old_password);
            return false;
        } else if (TextUtils.isEmpty(mNew)) {
            ErrorMessage = AppController.getInstance().getString(R.string.msg_enter_new_password);
            return false;
        } else if (mNew.length() < 6) {
            ErrorMessage = AppController.getInstance().getString(R.string.msg_password_6);
            return false;
        } else if (TextUtils.isEmpty(mConfirm)) {
            ErrorMessage = AppController.getInstance().getString(R.string.msg_please_enter_confirm_password);
            return false;
        } else if (!mNew.equals(mConfirm)) {
            ErrorMessage = AppController.getInstance().getString(R.string.msg_password_not_match);
            return false;
        } else return true;
    }

    public boolean validateCase(@NotNull String mDate, @NotNull String mTitle, @NotNull String mDescription, @NotNull ArrayList<String> imageList, @NotNull ArrayList<String> documentList) {
        if (TextUtils.isEmpty(mDate)) {
            ErrorMessage = AppController.getInstance().getString(R.string.msg_select_date);
            return false;
        } else if (!Utils.getInstance().validDate(mDate)) {
            ErrorMessage = AppController.getInstance().getString(R.string.msg_valid_date);
            return false;
        } else if (TextUtils.isEmpty(mTitle)) {
            ErrorMessage = AppController.getInstance().getString(R.string.msg_select_injury);
            return false;
        } else if (TextUtils.isEmpty(mDescription)) {
            ErrorMessage = AppController.getInstance().getString(R.string.msg_select_description);
            return false;
        } else if (false) {//Disabled Validation on Client Feedbacl
            ErrorMessage = AppController.getInstance().getString(R.string.msg_upload_photos);
            return false;
        } else if (false) {
            ErrorMessage = AppController.getInstance().getString(R.string.msg_upload_documents);
            return false;
        } else return true;
    }*/

    companion object {
        var ErrorMessage = ""
        var instance: Validator? = null
            get() {
                if (field == null) {
                    field = Validator()
                }
                return field
            }
            private set
    }
}