package com.harvdev.storyapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.harvdev.storyapp.R
import com.harvdev.storyapp.databinding.FragmentLoginBinding
import com.harvdev.storyapp.ui.utils.CustomEditText
import com.harvdev.storyapp.ui.utils.safeNavigate
import com.harvdev.storyapp.ui.utils.ViewModelFactory

class LoginFragment : Fragment() {

    companion object {
        private const val FRAGMENT_ID = R.id.navigation_login
    }

    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    private lateinit var edemail: CustomEditText
    private lateinit var edPassword: CustomEditText

    private lateinit var btnLogin: Button

    private lateinit var textSignUp: TextView
    private lateinit var loadingBar: ProgressBar

    private lateinit var logoImage: ImageView

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
        initObserver()
        initOnClickListener()
        initCheckAlreadyLogin()
        playAnimation()
    }

    private fun initBinding(binding: FragmentLoginBinding){
        btnLogin = binding.btnLogin
        edemail = binding.edLoginEmail
        edemail.setEmailType()
        edPassword = binding.edLoginPassword
        edPassword.setInputTypePassword()
        textSignUp = binding.textSignUp
        loadingBar = binding.loadingBar
        logoImage = binding.logoImage
    }

    private fun initObserver(){
        loginViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it){
                btnLogin.visibility = View.GONE
                loadingBar.visibility = View.VISIBLE
            } else {
                btnLogin.visibility = View.VISIBLE
                loadingBar.visibility = View.GONE
            }
        })
    }

    private fun initOnClickListener(){
        btnLogin.setOnClickListener {
            if (edemail.isNotEmptyAndError() && edPassword.isNotEmptyAndError())
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

    private fun playAnimation() {
        ObjectAnimator.ofFloat(logoImage, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(btnLogin, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(textSignUp, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(edemail, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(edPassword, View.ALPHA, 1f).setDuration(500)


        val together = AnimatorSet().apply {
            playTogether(login, signup)
        }


        AnimatorSet().apply {
            playSequentially(email, password, together)
            start()
        }
    }

}