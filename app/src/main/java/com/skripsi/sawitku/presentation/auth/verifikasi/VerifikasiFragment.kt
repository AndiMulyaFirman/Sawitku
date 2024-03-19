package com.skripsi.sawitku.presentation.auth.verifikasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.skripsi.sawitku.R
import com.skripsi.sawitku.databinding.FragmentVerifikasiBinding

class VerifikasiFragment : Fragment() {
    private lateinit var binding: FragmentVerifikasiBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerifikasiBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnImage.setOnClickListener {
            findNavController().navigate(R.id.action_verifikasiFragment_to_loginFragment)
        }
    }
}