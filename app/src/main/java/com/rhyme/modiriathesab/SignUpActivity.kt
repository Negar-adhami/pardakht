package com.rhyme.modiriathesab

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    val context = this
    val db = myDbAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        directToLogin.setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }

        signUpBtn.setOnClickListener {
            submitSignUp()
        }
    }

    private fun submitSignUp() {
        if(!usernameTv.text.toString().isBlank() && !emailTv.text.toString().isBlank() && !pwTv.text.toString().isBlank() && pwTv.text.toString().length>3)
        {
            db.insertUser(usernameTv.text.toString(), emailTv.text.toString(), pwTv.text.toString());
            val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString(getString(R.string.preference_username), usernameTv.text.toString())
            editor.apply()
            val intent = Intent(this@SignUpActivity, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }

    }
}