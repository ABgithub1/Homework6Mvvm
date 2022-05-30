package com.example.homework6.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework6.R
import com.example.homework6.adapters.PersonListAdapter
import com.example.homework6.databinding.FragmentListFromRetrofitBinding
import com.example.homework6.extentions.recyclerViewExt.addSpaceDecoration
import com.example.homework6.model.LceState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListFromRetrofitFragment : Fragment() {

    private var _binding: FragmentListFromRetrofitBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<ListFromRetrofitViewModel>()

    private val adapter by lazy {
        PersonListAdapter(itemClick = {
            findNavController().navigate(
                ListFromRetrofitFragmentDirections.actionListFromRetrofitFragmentToDetailsFragment(
                    it.name, it.nickname, it.birthday, it.status, it.img
                )
            )
        }, longItemClick = {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                viewModel.addPersonToDb(it.id, it.name, it.nickname, it.birthday, it.status, it.img)
            }
            Toast.makeText(context, "Added to database", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentListFromRetrofitBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            viewModel.lceFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .onEach { lce ->
                    binding.progressCircular.isVisible = lce == LceState.Loading
                    when (lce) {
                        is LceState.Content -> {
                            adapter.submitList(lce.persons)
                        }
                        is LceState.Error -> {
                            Snackbar.make(
                                view,
                                lce.throwable.message ?: "State_Error",
                                Snackbar.LENGTH_LONG
                            )
                                .show()
                        }
                        LceState.Loading -> {
                        }
                    }
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            toolbar.menu.findItem(R.id.action_search)
                .let { it.actionView as androidx.appcompat.widget.SearchView }
                .setOnQueryTextListener(object :
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        viewModel._searchQueryFlow.tryEmit(newText)
                        return true
                    }
                })

            viewModel.searchQueryFlow
                .onEach {
                    val list = viewModel.filterPersonList(it)
                    adapter.submitList(list)
                }.launchIn(viewLifecycleOwner.lifecycleScope)

            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.addSpaceDecoration(8)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}