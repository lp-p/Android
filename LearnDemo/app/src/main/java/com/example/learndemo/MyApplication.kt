package com.example.learndemo

import android.app.Application
import cn.leancloud.LCObject
import cn.leancloud.LeanCloud


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 提供 this、App ID、绑定的自定义 API 域名作为参数
        LeanCloud.initialize(
            this,
            "sh23zqi272Bho3LvKNwyE5mN-gzGzoHsz",
            "GUCnT07xrHFftHgdyVxST43l",
            "https://sh23zqi2.lc-cn-n1-shared.com"
        )
//        val testObject = LCObject("TestObject")
//        testObject.put("words", "Hello world!")
//        testObject.saveInBackground().blockingSubscribe()
    }
}