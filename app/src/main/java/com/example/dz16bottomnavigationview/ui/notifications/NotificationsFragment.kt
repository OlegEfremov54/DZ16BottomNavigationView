package com.example.dz16bottomnavigationview.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dz16bottomnavigationview.R
import com.example.dz16bottomnavigationview.databinding.FragmentNotificationsBinding
import com.example.dz16bottomnavigationview.domain.notes.Note

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var viewModel: NotificationsViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[NotificationsViewModel::class.java]

        val adapter = NotesAdapter(requireContext(), object : NotesAdapter.OnItemClickListener {
            override fun onClick(item: Note) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Внимание")
                    .setMessage("Вы собираетесь удалить пункт \"${item.title}\"")
                    .setPositiveButton("Удалить") { _, _ ->
                        viewModel?.deleteNote(item)
                    }
                    .setNeutralButton("Отмена", null)
                    .create().show()
            }
        })

        viewModel?.notes?.observe(viewLifecycleOwner) { list ->
            list?.let {
                adapter.updateList(it)
            }
        }
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        binding.addNoteFab.setOnClickListener {
            val dialogAddItem = layoutInflater.inflate(R.layout.dialog_add_item, null)

            AlertDialog.Builder(requireContext())
                .setTitle("Внимание")
                .setView(dialogAddItem)
                .setPositiveButton("Добавить заметку") { dialog, _ ->
                    val title = dialogAddItem.findViewById<EditText>(R.id.title_et).text.toString().trim()
                    val content = dialogAddItem.findViewById<EditText>(R.id.content_et).text.toString().trim()
                    val note = Note(title, content)
                    viewModel?.insertNote(note)
                    adapter.notifyDataSetChanged()
                }
                .setNeutralButton("Отмена", null)
                .create().show()
        }
        binding.rvNotes.adapter = adapter
        val root: View = binding.root

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}