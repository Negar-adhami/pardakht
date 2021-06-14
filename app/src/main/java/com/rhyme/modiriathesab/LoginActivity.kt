package com.rhyme.modiriathesab

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    val context = this
    val db = myDbAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        directToSignup.setOnClickListener {
            startActivity(Intent(context,SignUpActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        loginBtn.setOnClickListener {
            submitSignUp()
        }
    }


    private fun submitSignUp() {
        if(!nameTvLogin.text.toString().isBlank() && !pwTvLogin.text.toString().isBlank())
        {
            if(db.isValidUser(nameTvLogin.text.toString(), pwTvLogin.text.toString()))
            {
                val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString(getString(R.string.preference_username), nameTvLogin.text.toString())
                editor.apply()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }
        }
    }

}