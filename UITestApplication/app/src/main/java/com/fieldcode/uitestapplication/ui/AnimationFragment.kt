package com.fieldcode.uitestapplication.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil

import com.fieldcode.uitestapplication.R
import com.fieldcode.uitestapplication.databinding.AnimationFragmentBinding

class AnimationFragment : Fragment() {

    private lateinit var viewModel: AnimationViewModel
    private lateinit var binding: AnimationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(AnimationViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.animation_fragment, container, false)
        with(binding){
            viewModel = this@AnimationFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            animateButton.setOnClickListener {
                motoImage.startAnimation(AnimationUtils.loadAnimation(context,R.anim.to_right_anim))
                carImage.startAnimation(AnimationUtils.loadAnimation(context,R.anim.to_left)) }

        }
    }
}
