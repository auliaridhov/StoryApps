package com.harvdev.storyapp.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.harvdev.storyapp.R
import com.harvdev.storyapp.databinding.FragmentRegisterBinding
import com.harvdev.storyapp.ui.login.LoginViewModel
import com.harvdev.storyapp.ui.utils.CustomEditText
import com.harvdev.storyapp.ui.utils.ViewModelFactory
import com.harvdev.storyapp.ui.utils.safeNavigate
import com.harvdev.storyapp.ui.utils.safePopBackstack

class RegisterFragment : Fragment() {

    companion object {
        private const val FRAGMENT_ID = R.id.navigation_register
    }

    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    private lateinit var edNama: CustomEditText
    private lateinit var edEmail: CustomEditText
    private lateinit var edPassword: CustomEditText

    private lateinit var btnLogin: Button

    private lateinit var signInText: TextView
    private lateinit var loadingBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentRegisterBinding.inflate(inflater, container, false).root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentRegisterBinding.bind(view)

        initBinding(binding)
        initObserver()
        initOnClickListener()
    }

    private fun initBinding(binding: FragmentRegisterBinding){
        btnLogin = binding.btnRegister
        edNama = binding.edRegisterName
        edEmail = binding.edRegisterEmail
        edEmail.setEmailType()
        edPassword = binding.edRegisterPassword
        edPassword.setInputTypePassword()
        signInText = binding.textSignIn
        loadingBar = binding.loadingBar
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
            if (edNama.isNotEmpty() && edEmail.isNotEmptyAndError() && edPassword.isNotEmptyAndError())
                loginViewModel.register(
                    edNama.text.toString(),
                    edEmail.text.toString(), edPassword.text.toString()
                ){ error, message ->
                    if (error == true){
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        safeNavigate(FRAGMENT_ID, R.id.action_navigation_register_to_navigation_login)
                    }
                }
        }
        signInText.setOnClickListener {
            safePopBackstack()
        }
    }

}