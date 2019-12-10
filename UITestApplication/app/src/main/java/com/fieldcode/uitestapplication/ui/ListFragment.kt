package com.fieldcode.uitestapplication.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

import com.fieldcode.uitestapplication.R
import com.fieldcode.uitestapplication.databinding.ListFragmentBinding
import kotlinx.android.synthetic.main.item.view.*

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var binding: ListFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ListFragment.viewModel
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            listBackArrow.setOnClickListener { findNavController().navigate(R.id.action_listFragment_to_switchesFragment) }
            recyclerView.adapter = MyAdapter(list)
        }
    }

    companion object {
        private val list: List<Pair<String, String>> =
            listOf(
                Pair("Maciej", "Beczek"),
                Pair("Michał", "Gauza"),
                Pair("Łukasz", "Szczepański"),
                Pair("Bartosz", "Bogucki"),
                Pair("Michał", "Simbiga"),
                Pair("Marcin", "Bartoszewski"),
                Pair("Tomasz", "Łukaszewki"),
                Pair("Sebastian", "Małecki"),
                Pair("Przemysław", "Niemiec"),
                Pair("Maciej", "Kosecki")
            )
    }
}

class MyAdapter(private val list: List<Pair<String, String>>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.card.first_text.text = list[position].first
        holder.itemView.card.second_text.text = list[position].second
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
