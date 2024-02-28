package com.example.notesapp.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentSearchBinding
import com.example.notesapp.databinding.ToastLayoutBinding
import com.example.notesapp.ui.notes.NotesEvent
import com.example.notesapp.ui.notes.adapter.NotesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesAdapter: NotesAdapter
    private val searchViewModel: SearchViewModel by viewModels()

    private lateinit var popupToast: ToastLayoutBinding
    private lateinit var dialogToast: AlertDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        binding.etSearch.addTextChangedListener(validationSearchQuery)

    }

    private val validationSearchQuery = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val query = s.toString()
            searchViewModel.getNotesSearch(query)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun initUI() {
        initUIState()
        initList()
        configurationToast()
    }

    private fun configurationToast() {
        popupToast = ToastLayoutBinding.inflate(layoutInflater)
        dialogToast = initConfigPopUp(requireContext(), popupToast)


        popupToast.buttonAction.setOnClickListener {
            searchViewModel.onEvent(NotesEvent.RestoreNote)
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

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.state.collect {
                    if (it.notes.isEmpty()) {
                        binding.rvNotesSearch.isVisible = false
                    } else {
                        notesAdapter.updateList(it.notes)
                        binding.rvNotesSearch.isVisible = true
                    }
                }
            }
        }
    }

    private fun initList() {
        notesAdapter = NotesAdapter(
            onItemSelected = {
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToNoteDetailActivity(it.id!!)
                )
            },
            onItemDelete = {
                searchViewModel.onEvent(NotesEvent.DeleteNote(it))
                dialogToast.show()

            }
        )

        binding.rvNotesSearch.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notesAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onPause() {
        super.onPause()
        binding.etSearch.text = Editable.Factory.getInstance().newEditable("")
    }

}