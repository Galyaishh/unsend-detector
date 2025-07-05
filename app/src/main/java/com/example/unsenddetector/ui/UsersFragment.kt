package com.example.unsenddetector.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unsenddetector.MyApp
import com.example.unsenddetector.R
import com.example.unsenddetector.databinding.FragmentUsersBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UsersAdapter { selectedUser ->
            // מעבר לצ׳אט עם המשתמש
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, ChatFragment.newInstance(selectedUser))
                .addToBackStack(null)
                .commit()
        }

        binding.usersRVList.layoutManager = LinearLayoutManager(requireContext())
        binding.usersRVList.adapter = adapter
        loadUsers()
    }

    private fun loadUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            val dao = MyApp.deletedMessageDb.deletedMessageDao()
            val allMessages = dao.getAll()

            val distinctUsers = allMessages
                .mapNotNull { it.title }
                .distinct()

            withContext(Dispatchers.Main) {
                adapter.submitList(distinctUsers)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
