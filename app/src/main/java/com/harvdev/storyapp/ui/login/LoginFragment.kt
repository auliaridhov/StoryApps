package com.harvdev.storyapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.harvdev.storyapp.R
import com.harvdev.storyapp.databinding.FragmentLoginBinding
import com.harvdev.storyapp.ui.utils.CustomEditText
import com.harvdev.storyapp.ui.utils.safeNavigate

class LoginFragment : Fragment() {

    companion object {
        private const val FRAGMENT_ID = R.id.navigation_login
    }

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var edemail: CustomEditText
    private lateinit var edPassword: CustomEditText

    private lateinit var btnLogin: Button

    private lateinit var textSignUp: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentLoginBinding.inflate(inflater, container, false).root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)

        initBinding(binding)
        initViewModel()
        initObserver()
        initOnClickListener()
        initCheckAlreadyLogin()
    }

    private fun initBinding(binding: FragmentLoginBinding){
        btnLogin = binding.btnLogin
        edemail = binding.edLoginEmail
        edPassword = binding.edLoginPassword
        edPassword.setInputTypePassword()
        textSignUp = binding.textSignUp
    }

    private fun initViewModel(){
        loginViewModel =
            ViewModelProvider(this)[LoginViewModel::class.java]
    }

    private fun initObserver(){
        loginViewModel.isLoading.observe(viewLifecycleOwner, Observer {

        })
    }

    private fun initOnClickListener(){
        btnLogin.setOnClickListener {
            if (edemail.isNotEmpty() && edPassword.isNotEmptyAndError())
                loginViewModel.login(edemail.text.toString(), edPassword.text.toString()){ isError, message ->
                    if (isError == true){
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    } else {
                        safeNavigate(FRAGMENT_ID, R.id.action_navigation_login_to_navigation_home)
                    }

                }
        }
        textSignUp.setOnClickListener {
            safeNavigate(FRAGMENT_ID, R.id.action_navigation_login_to_navigation_register)
        }
    }

    private fun initCheckAlreadyLogin(){
        if (loginViewModel.isLogin()){
            safeNavigate(FRAGMENT_ID, R.id.action_navigation_login_to_navigation_home)
        }
    }

}