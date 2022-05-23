package com.example.learndemo

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.leancloud.LCUser
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        editTextSignUpUsername.addTextChangedListener(watcher)
        editTextSignUpUserpassword.addTextChangedListener(watcher)
        progressBarSignUp.visibility = View.INVISIBLE  //开始停止转动
        buttonSignUpConfirm.setOnClickListener {
            val name = editTextSignUpUsername.text?.trim().toString()
            val pwd = editTextSignUpUserpassword.text?.trim().toString()
            LCUser().apply {
                progressBarSignUp.visibility = View.VISIBLE  //注册开始转动
                username = name
                password = pwd
                signUpInBackground().subscribe(object : Observer<LCUser> {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onNext(t: LCUser) {
                        Toast.makeText(this@SignUpActivity, "注册成功", Toast.LENGTH_SHORT).show()
                        LCUser.logIn(name, pwd).subscribe(object : Observer<LCUser> {
                            override fun onSubscribe(d: Disposable) {}

                            override fun onNext(t: LCUser) {
                                startActivity(
                                    Intent(
                                        this@SignUpActivity,
                                        MainActivity::class.java
                                    ).also {
                                        it.flags =
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    })
                                //finish()
                            }

                            override fun onError(e: Throwable) {
                                progressBarSignUp.visibility = View.INVISIBLE  //出错停止转动
                                Toast.makeText(
                                    this@SignUpActivity,
                                    "${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            override fun onComplete() {}
                        })
                    }

                    override fun onError(e: Throwable) {
                        progressBarSignUp.visibility = View.INVISIBLE  //出错停止转动
                        Toast.makeText(this@SignUpActivity, "${e.message}", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onComplete() {}
                })
            }
        }
    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val t1 = editTextSignUpUsername.text.toString().isNotEmpty()
            val t2 = editTextSignUpUserpassword.text.toString().isNotEmpty()
            buttonSignUpConfirm.isEnabled = t1 and t2
        }

        override fun afterTextChanged(p0: Editable?) {}
    }
}