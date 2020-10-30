package com.example.zayve_test.authorization

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.zayve_test.R
import com.example.zayve_test.ZayveActivity
import com.example.zayve_test.databinding.FragmentSignupBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest


//sign up fragment
class SignupFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSignupBinding

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        auth = FirebaseAuth.getInstance()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        binding.loginButton.setOnClickListener(Navigation.createNavigateOnClickListener((R.id.action_signupFragment2_to_loginFragment2)))
        binding.signupButton.setOnClickListener {
            if (container != null) {
                signUpUser(container.context.applicationContext)
            }
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun signUpUser(context: Context): Unit {
        val displayName=binding.userName
        if (binding.userName.toString().isEmpty()) {
            binding.userName.error = "Please enter a valid user name"
            binding.userName.requestFocus()
            return
        }
        if (binding.userEmail.toString().isEmpty()) {
            binding.userEmail.error = "Please enter a valid email address"
            binding.userEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(binding.userEmail.text.toString()).matches()) {
            binding.userEmail.error = "Please enter a valid email address"
            binding.userEmail.requestFocus()
            return
        }
        if (binding.userPassword.toString().isEmpty() || binding.userPassword.toString().length < 6) {
            binding.userPassword.error = "Please enter a valid password"
            binding.userPassword.requestFocus()
            return
        }


        activity?.mainExecutor?.let {
            auth.createUserWithEmailAndPassword(binding.userEmail.text.toString(), binding.userPassword.text.toString())
                    .addOnCompleteListener(it, OnCompleteListener<AuthResult?> { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail: success")
                            // THE USER ID
                            val userId = task.result?.user?.uid;
                            val user = FirebaseAuth.getInstance().currentUser

                            val profileUpdates = UserProfileChangeRequest.Builder()
                                    .setDisplayName(binding.userName.text.toString())
                                    .build()

                            user!!.updateProfile(profileUpdates)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Log.d(TAG, "User profile updated.")
                                        }
                                    }
//                            saveUserName(userId)
//                            findNavController().navigate(R.id.action_signupFragment2_to_profileSetupFragment)
                            val intent = Intent(activity, ZayveActivity::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail: failure", task.exception)
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                        }

                    })
        }

    }
}

