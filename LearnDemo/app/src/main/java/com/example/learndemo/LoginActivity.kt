package com.example.learndemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import cn.leancloud.LCUser
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        editTextLoginUsername.addTextChangedListener(watcher)
        editTextLoginUserpassword.addTextChangedListener(watcher)
        buttonLoginConfirm.isEnabled = false
        progressBarLogin.visibility = View.INVISIBLE  //开始停止转动
        buttonToSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        buttonLoginConfirm.setOnClickListener {
            progressBarLogin.visibility = View.VISIBLE   //登录开始转动
            val name = editTextLoginUsername.text?.toString()?.trim()
            val pwd = editTextLoginUserpassword.text?.toString()?.trim()
            LCUser.logIn(name, pwd).subscribe(object : Observer<LCUser> {
                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: LCUser) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }

                override fun onError(e: Throwable) {
                    progressBarLogin.visibility = View.INVISIBLE  //出错停止转动
                    Toast.makeText(this@LoginActivity, "${e.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {}
            })
        }
    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val t1 = editTextLoginUsername.text.toString().isNotEmpty()
            val t2 = editTextLoginUserpassword.text.toString().isNotEmpty()
            buttonLoginConfirm.isEnabled = t1 and t2   // 填入数据后登录按钮启用
        }

        override fun afterTextChanged(p0: Editable?) {}
    }
}