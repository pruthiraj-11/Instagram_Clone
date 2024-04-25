package com.app.instagramclone.utils

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

fun uploadImage(uri: Uri,folderName: String, callback:(String?)->Unit) {
    var imageURL: String? =null
    FirebaseStorage.getInstance().getReference().child(folderName).child(UUID.randomUUID().toString())
        .putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {it1->
                imageURL=it1.toString()
                callback(imageURL)
            }
        }
}