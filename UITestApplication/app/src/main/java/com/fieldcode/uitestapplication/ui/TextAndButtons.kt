package com.fieldcode.uitestapplication.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.fieldcode.uitestapplication.R
import com.fieldcode.uitestapplication.databinding.TextAndButtonsFragmentBinding

class TextAndButtons : Fragment() {

    private lateinit var viewModel: TextAndButtonsViewModel

    private lateinit var binding: TextAndButtonsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(TextAndButtonsViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater,R.layout.text_and_buttons_fragment,container,false)
        with(binding){
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@TextAndButtons.viewModel
            return root
        }
    }
}