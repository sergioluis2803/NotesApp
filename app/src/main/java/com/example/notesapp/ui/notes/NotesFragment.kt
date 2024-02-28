package com.example.notesapp.ui.notes

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentNotesBinding
import com.example.notesapp.databinding.ToastLayoutBinding
import com.example.notesapp.ui.notes.adapter.NotesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesFragment : Fragment() {

    private val notesViewModel by viewModels<NotesViewModel>()
    private lateinit var notesAdapter: NotesAdapter

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var popupToast: ToastLayoutBinding
    private lateinit var dialogToast: AlertDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initUIState()
        initList()
        initListener()
        configurationToast()
    }

    private fun initListener() {
        binding.btnNew.setOnClickListener {
            findNavController().navigate(
                NotesFragmentDirections.actionNotesFragmentToNoteDetailActivity(0)
            )
        }
    }

    private fun initList() {
        notesAdapter = NotesAdapter(
            onItemSelected = {
                findNavController().navigate(
                    NotesFragmentDirections.actionNotesFragmentToNoteDetailActivity(it.id!!)
                )
            },
            onItemDelete = {
                notesViewModel.onEvent(NotesEvent.DeleteNote(it))
                dialogToast.show()
            }
        )

        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notesAdapter
        }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                notesViewModel.state.collect {
                    if (it.notes.isEmpty()) {
                        binding.tvMessage.isVisible = true
                        binding.rvNotes.isVisible = false
                    } else {
                        notesAdapter.updateList(it.notes)
                        binding.tvMessage.isVisible = false
                        binding.rvNotes.isVisible = true
                    }
                }
            }
        }
    }

    private fun configurationToast(){
        popupToast = ToastLayoutBinding.inflate(layoutInflater)
        dialogToast = initConfigPopUp(requireContext(), popupToast)


        popupToast.buttonAction.setOnClickListener {
            notesViewModel.onEvent(NotesEvent.RestoreNote)
            dialogToast.hide()
        }
    }

    private fun initConfigPopUp(context: Context, popupToast: ViewBinding, state: Boolean = true): AlertDialog {
        val dialogBuilder = AlertDialog.Builder(context, R.style.FondoDialog)
        dialogBuilder.setView(popupToast.root)
        dialogBuilder.setCancelable(false)
        val dialog = dialogBuilder.create()
        dialog.setCancelable(state)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogToast.dismiss()
    }
}