package com.example.notesapp.ui.options

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notesapp.databinding.FragmentOptionsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OptionsFragment : Fragment() {

    private var _binding: FragmentOptionsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivInstagram.setOnClickListener {
            openRed(Constants.IG)
        }

        binding.ivGitHub.setOnClickListener {
            openRed(Constants.GITHUB)
        }

        binding.ivX.setOnClickListener {
            openRed(Constants.X)
        }

        binding.ivLinkedin.setOnClickListener {
            openRed(Constants.LINKEDIN)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOptionsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun openRed(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        startActivity(intent)
    }

}