package com.example.learndemo

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cn.leancloud.LCException
import cn.leancloud.LCObject
import cn.leancloud.LCQuery
import cn.leancloud.LCUser
import cn.leancloud.livequery.LCLiveQuery
import cn.leancloud.livequery.LCLiveQueryEventHandler
import cn.leancloud.livequery.LCLiveQuerySubscribeCallback
import io.reactivex.disposables.Disposable

class MyViewModel(application: Application) : AndroidViewModel(application) {
    private val _dataListLive = MutableLiveData<List<LCObject>>()
    val dataListLive: LiveData<List<LCObject>> = _dataListLive

    init {
        //
        val query = LCQuery<LCObject>("Word")
        query.whereEqualTo("user", LCUser.getCurrentUser())
        query.findInBackground().subscribe(object : io.reactivex.Observer<List<LCObject>> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(t: List<LCObject>) {
                _dataListLive.value = t
            }

            override fun onError(e: Throwable) {
                Toast.makeText(application, "${e.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onComplete() {}
        })
        //实时显示
        val liveQuery = LCLiveQuery.initWithQuery(query)
        liveQuery.subscribeInBackground(object : LCLiveQuerySubscribeCallback() {
            override fun done(e: LCException?) {}
        })
        liveQuery.setEventHandler(object : LCLiveQueryEventHandler() {
            override fun onObjectCreated(lcObject: LCObject) {
                super.onObjectCreated(lcObject)
                val t = _dataListLive.value?.toMutableList()
                t?.add(lcObject)
                _dataListLive.value = t
            }

            override fun onObjectDeleted(objectId: String?) {//控制台删除，客户端实时删除单词
                super.onObjectDeleted(objectId)
                val t = _dataListLive.value?.toMutableList()
                val ob = t?.find { it.get("objectId") == objectId }
                t?.remove(ob)
                _dataListLive.value = t
            }

            override fun onObjectUpdated(lcObject: LCObject, updateKeyList: MutableList<String>) {
                super.onObjectUpdated(lcObject, updateKeyList)
                val ob =
                    _dataListLive.value?.find { it.get("objectId") == lcObject.get("objectId") }
                updateKeyList.forEach {
                    ob?.put(it, lcObject.get(it))
                }
                _dataListLive.value = _dataListLive.value
            }
        })
    }

    fun addWord(newWord: String) {
        LCObject("Word").apply {
            put("word", newWord)
            put("user", LCUser.getCurrentUser())
            saveInBackground().subscribe(object : io.reactivex.Observer<LCObject> {
                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: LCObject) {
                    Toast.makeText(getApplication(), "添加成功", Toast.LENGTH_SHORT).show()
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(getApplication(), "${e.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {}
            })
        }
    }
}