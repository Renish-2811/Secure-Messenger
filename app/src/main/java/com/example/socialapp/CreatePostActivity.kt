package com.example.socialapp

import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.socialapp.daos.PostDao
import kotlinx.android.synthetic.main.activity_create_post2.*
import javax.crypto.KeyGenerator

class CreatePostActivity : AppCompatActivity() {
    private lateinit var postDao: PostDao

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post2)
        postDao = PostDao()
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val keyGenParameterSpec = KeyGenParameterSpec.Builder("MyKeyAlias",
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()

        postButton.setOnClickListener {
               val input = postInput.text.toString().trim()
               val final = cc().encrypt(input)

            if(input.isNotEmpty()) {
                postDao.addPost(final.toString())
                finish()
            }
        }

    }




}
