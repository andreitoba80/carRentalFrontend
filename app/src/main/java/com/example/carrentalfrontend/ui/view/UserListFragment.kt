package com.example.carrentalfrontend.ui.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carrentalfrontend.R
import com.example.carrentalfrontend.databinding.FragmentUserListBinding
import com.example.carrentalfrontend.domain.model.entity.User
import com.example.carrentalfrontend.ui.adapter.UserListAdapter
import com.example.carrentalfrontend.ui.viewmodel.UserListViewModel
import com.example.carrentalfrontend.util.logDebugError
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListFragment : Fragment() {
    private val viewModel by viewModel<UserListViewModel>()

    private lateinit var binding: FragmentUserListBinding
    private lateinit var userListAdapter: UserListAdapter
    private lateinit var userList: ArrayList<User>
    private var filteredUserList: ArrayList<User> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentUserListBinding.inflate(layoutInflater).let {
        binding = it
        binding.root
    }.also {
        initUserListRecyclerView()
        initObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.usersRefreshLayout.setOnRefreshListener {
            binding.usersRefreshLayout.isRefreshing = false
            viewModel.fetchUsers()
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.searchFieldEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.filterButton.visibility = View.GONE
            } else {
                binding.filterButton.visibility = View.VISIBLE
            }
        }

        binding.searchFieldEditText.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val searchString = s.toString()
                    filteredUserList = userList.filter {
                        it.fullName.contains(searchString, ignoreCase = true) ||
                                it.username.contains(searchString, ignoreCase = true)
                    } as ArrayList<User>
                    addDataToList(filteredUserList)
                    logDebugError("Filtered list: $filteredUserList")
                }
            }
        )
    }

    private fun initUserListRecyclerView() {
        binding.usersRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        userList = ArrayList()
        viewModel.fetchUsers()
    }

    private fun initObserver() {
        viewModel.userList.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                userList = it
                addDataToList(it)
            }
        }

        viewModel.deleteSuccess.observe(viewLifecycleOwner) {
            if (it == true) {
                viewModel.fetchUsers()
            }
        }
    }

    private fun addDataToList(users: ArrayList<User>) {
        userListAdapter = UserListAdapter(users, {
            // TODO: Navigate to edit screen
            val bundle = Bundle().apply {
                putSerializable("SELECTED_USER_DATA_KEY", it)
            }
            findNavController().navigate(R.id.action_userListFragment_to_editUserFragment, bundle)
            logDebugError("OnEditClick: $it")
        }, {
            logDebugError("OnDeleteClick: $it")
            viewModel.deleteUser(it.id)
        })
        binding.usersRecyclerView.adapter = userListAdapter
    }

}