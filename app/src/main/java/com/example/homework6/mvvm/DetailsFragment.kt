package com.example.homework6.mvvm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.homework6.databinding.FragmentDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = requireNotNull(_binding) { "Destroyed" }

    private val args by navArgs<DetailsFragmentArgs>()

    private val viewModel by viewModel<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailsBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            nameDetails.text = args.name
            nicknameDetails.text = args.nickname
            dateOfBirthDetails.text = args.birthday
            statusDetails.text = args.status
            imageDetails.load(args.img)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}