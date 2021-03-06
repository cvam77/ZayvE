package com.example.zayve_test.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.zayve_test.R
import com.example.zayve_test.ZayveActivity
import com.example.zayve_test.databinding.FragmentProfileSetupBinding
import com.google.android.gms.tasks.Task
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class ProfileSetupFragment : Fragment() {
    private lateinit var binding: FragmentProfileSetupBinding
    private var SELECT_PHOTO: Int = 1
    private lateinit var mStorageRef: StorageReference;
    private lateinit var myDBref: DatabaseReference
    private lateinit var imageURI: Uri
    private var imageDownloadUrl = ""
    private lateinit var user: FirebaseUser


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_setup, container, false)
//        receivers user name from firebase
        user = FirebaseAuth.getInstance().currentUser!!

//        picks an image from user
        binding.pickImage.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, SELECT_PHOTO)
        }
        binding.saveProfile.setOnClickListener {
            submitInterests()
        }

        return binding.root
    }

    // Write a message to the real time database database
    private fun submitInterests() {
//        realtime database reference
        val database = FirebaseDatabase.getInstance()
        myDBref = database.reference
        uploadUserProfile()
    }

    private fun checkinterest(string: String, number: Int) : Boolean
    {
        if (string.isEmpty())
        {
            Toast.makeText(context, "Fill in interest " + number,
                    Toast.LENGTH_SHORT).show()
            return false;
        }
        return true;
    }

    //    handles image picker result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PHOTO) {
            // handle chosen image
            if (data != null) {
                imageURI = data.data!!
                binding.profileAvatar.setImageURI(imageURI)
            }
        }
    }

    //   uploads avatar image and saves downloadurl into imageUrl
    private fun uploadUserProfile() {
        val interest1 = binding.interest1.text.toString()
        val interest2 = binding.interest2.text.toString()
        val interest3 = binding.interest3.text.toString()
        val interest4 = binding.interest4.text.toString()
        val interest5 = binding.interest5.text.toString()

        if (checkinterest(interest1, 1) && checkinterest(interest2, 2) && checkinterest(interest3, 3) && checkinterest(interest4, 4) && checkinterest(interest5, 5))
        {
            mStorageRef = FirebaseStorage.getInstance().reference;
            val imageStorageBucketRef: StorageReference = mStorageRef.child("images/${user.uid}")
    //        puts image into the firebase storage, fetches image download url and uploads the user profile to the backend
            imageStorageBucketRef.putFile(imageURI)
                    .addOnSuccessListener { taskSnapshot -> // Get a URL to the uploaded content
                        // Get a URL to the uploaded content
                        val downloadTask: Task<Uri> = taskSnapshot.storage.downloadUrl
                        downloadTask.addOnSuccessListener {
                            imageDownloadUrl = it.toString()
                            Log.d("response", "downloadUrl:$imageDownloadUrl")
                            val interests = arrayListOf<String>(interest1, interest2, interest3, interest4, interest5);
                            val userDb = myDBref.child("users").child(user.uid)
                            userDb.child("interests").setValue(interests)
                            userDb.child("user_name").setValue(binding.fullName.text.toString())
                            userDb.child("profile_image").setValue(imageDownloadUrl)
                            userDb.child("about").setValue(binding.userIntro.text.toString())
    //                        userDb.child("user_name").setValue(binding.userName.text.toString())
    //                        after saving data to the realtime database, navigates to ZayveActivity
    //                        todo: if possible, manage the navigation to the profile frag
                            val intent = Intent(activity, ZayveActivity::class.java)
                            startActivity(intent)

                        }
                        Log.d("response", "Successfully uploaded the data to firebase")
                    }
                    .addOnFailureListener {
                        Log.d("response", "Unsuccessful to upload the image to firebase")
                    }
        }
    }

}



