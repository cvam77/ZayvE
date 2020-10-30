package com.example.zayve_test.authorization

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.zayve_test.ZayveActivity
import com.example.zayve_test.R
import com.example.zayve_test.databinding.FragmentLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentLoginBinding

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.signupButton.setOnClickListener((Navigation.createNavigateOnClickListener(R.id.action_loginFragment2_to_signupFragment2)))

        binding.buttonLogin.setOnClickListener {
            if (container != null) {
                loginUser(container.context)
            }
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun loginUser(context: Context): Unit {
        activity?.mainExecutor?.let {
            auth.signInWithEmailAndPassword(binding.userEmail.text.toString(), binding.userPassword.text.toString())
                    .addOnCompleteListener(it, OnCompleteListener<AuthResult?> { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            //        TODo: remember to change the navigation here to login_to_homepage
//                            findNavController().navigate(R.id.action_loginFragment2_to_profile2)
                            val intent = Intent(activity, ZayveActivity::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    })
        }
    }
}
