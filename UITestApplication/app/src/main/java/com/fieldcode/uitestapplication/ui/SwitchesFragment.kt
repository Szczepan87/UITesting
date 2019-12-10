package com.fieldcode.uitestapplication.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController

import com.fieldcode.uitestapplication.R
import com.fieldcode.uitestapplication.databinding.SwitchesFragmentBinding

class SwitchesFragment : Fragment() {

    private lateinit var viewModel: SwitchesViewModel
    private lateinit var binding: SwitchesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(SwitchesViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.switches_fragment, container, false)
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@SwitchesFragment.viewModel
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            switchBackArrow.setOnClickListener { findNavController().navigate(R.id.action_switchesFragment_to_animationFragment) }
            switchForwardArrow.setOnClickListener { findNavController().navigate(R.id.action_switchesFragment_to_listFragment) }
        }
    }
}
