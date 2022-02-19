package com.astar.downloadfiles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.astar.downloadfiles.databinding.FragmentDownloadFileBinding

class DownloadFileFragment : Fragment() {

    private var _binding: FragmentDownloadFileBinding? = null
    private val binding: FragmentDownloadFileBinding get() = _binding!!

    private val viewModel by viewModels<DownloadFileViewModel> {
        val app = requireActivity().application as MainApp
        DownloadFileViewModelFactory(app.loader, app.storage)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDownloadFileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.download.setOnClickListener {
            val url = binding.link.text.toString().trim()

            if (url.isNotEmpty()) {
                viewModel.downloadFile(url)
            } else {
                Toast.makeText(requireContext(), "Введите ссылку", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.observeDownloadState(viewLifecycleOwner, ::handleDownloadFileState)
    }

    private fun handleDownloadFileState(state: DownloadState) {
        when (state) {
            is DownloadState.Download -> binding.progress.isVisible = true
            is DownloadState.Error -> {
                binding.progress.isVisible = false
                toast(state.message)
            }
            is DownloadState.Finish -> {
                binding.progress.isVisible = false
                toast(state.filename)
            }
        }
    }

    private fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}