package com.example.zayve_test

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.zayve_test.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth


//sign up fragment
class SignupFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding:FragmentSignupBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        auth = FirebaseAuth.getInstance()
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_signup,container,false)
        binding.loginButton.setOnClickListener(Navigation.createNavigateOnClickListener((R.id.action_signupFragment2_to_loginFragment2)))
        binding.signupButton.setOnClickListener {
            if (container != null) {
                signUpUser(container.context)
            }
        }

        return binding.root
    }

    private fun signUpUser(context: Context): Unit {
        if(binding.userName.toString().isEmpty()){
            binding.userName.error = "Please enter a valid user name"
            binding.userName.requestFocus()
            return
        }
        if(binding.userEmail.toString().isEmpty()){
            binding.userEmail.error = "Please enter a valid email address"
            binding.userEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(binding.userEmail.text.toString()).matches()){
            binding.userEmail.error = "Please enter a valid email address"
            binding.userEmail.requestFocus()
            return
        }
        if(binding.userPassword.toString().isEmpty()){
            binding.userPassword.error = "Please enter a valid password"
            binding.userPassword.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(binding.userEmail.text.toString(), binding.userPassword.text.toString())
                .addOnCompleteListener(context) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        Navigation.createNavigateOnClickListener(R.id.action_signupFragment2_to_loginFragment2)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(context, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()

                    }
                }
    }
}