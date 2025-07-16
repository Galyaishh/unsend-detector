package com.example.unsenddetector.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unsenddetector.MyApp
import com.example.unsenddetector.R
import com.example.unsenddetector.databinding.FragmentChatBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val adapter = ChatAdapter()

    companion object {
        private const val ARG_USERNAME = "username"

        fun newInstance(userName: String): ChatFragment {
            val fragment = ChatFragment()
            fragment.arguments = Bundle().apply {
                putString(ARG_USERNAME, userName)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chatRV.layoutManager = LinearLayoutManager(requireContext()).apply {
            reverseLayout = false      // new messages at the bottom
            stackFromEnd = false
        }
        binding.chatRV.adapter = adapter

        val userName = arguments?.getString(ARG_USERNAME) ?: return

        CoroutineScope(Dispatchers.IO).launch {
            val allDeletedMessages = MyApp.deletedMessageDb
                .deletedMessageDao()
                .getAll()
                .filter { it.title == userName }
                .sortedBy { it.timestamp }

            launch(Dispatchers.Main) {
                adapter.submitList(allDeletedMessages)
                binding.chatRV.scrollToPosition(allDeletedMessages.size - 1)
            }
        }

        binding.chatTXTTitle.text = getString(R.string.deleted_messages_by, userName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
