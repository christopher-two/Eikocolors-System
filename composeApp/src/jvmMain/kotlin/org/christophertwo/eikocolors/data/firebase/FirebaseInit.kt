package org.christophertwo.eikocolors.data.firebase

import android.app.Application
import com.google.firebase.FirebasePlatform
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.initialize

fun firebaseInit(): FirebaseApp {
    FirebasePlatform.initializeFirebasePlatform(object : FirebasePlatform() {
        val storage = mutableMapOf<String, String>()

        override fun store(key: String, value: String) = storage.set(key, value)
        override fun retrieve(key: String) = storage[key]
        override fun clear(key: String) {
            storage.remove(key)
        }

        override fun log(msg: String) = println("FIREBASE LOG: $msg")
    })

    val options = FirebaseOptions(
        apiKey = "AIzaSyArfVH8NXxGU5yjLkiRt3rB0dDvYAYTQA8",
        applicationId = "1:37282779527:android:e3b5d0c121ee39beb64322",
        projectId = "eiko-colors",
        storageBucket = "eiko-colors.firebasestorage.app",
        databaseUrl = "https://eiko-colors-default-rtdb.firebaseio.com",
    )

    val mockContext = Application()

    return Firebase.initialize(context = mockContext, options = options)
}