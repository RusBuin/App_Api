package com.example.kotlin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserListActivity : AppCompatActivity() {

    private lateinit var recyclerViewUsers: RecyclerView
    private val userList = mutableListOf<User>()
    private lateinit var userAdapter: UserAdapter
    private val TAG = "UserListActivity"
    private var currentPage = 1
    private var isLoading = false
    private var isLastPage = false
    private lateinit var textViewError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_list)

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers)
        textViewError = findViewById(R.id.textViewError)
        userAdapter = UserAdapter(this, userList)
        recyclerViewUsers.layoutManager = LinearLayoutManager(this)
        recyclerViewUsers.adapter = userAdapter

        recyclerViewUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (!isLoading && !isLastPage && layoutManager.findLastVisibleItemPosition() == userList.size - 1) {
                    loadUsers(currentPage)
                }
            }
        })

        loadUsers(currentPage)
    }

    private fun loadUsers(page: Int) {
        isLoading = true
        val apiService = ApiClient.api
        Log.d(TAG, "Loading users from page: $page")

        apiService.getUsers(page).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.d(TAG, "Response received: ${response.code()}")

                if (response.isSuccessful) {
                    val users = response.body()?.data ?: emptyList()
                    Log.d(TAG, "Users loaded: $users")

                    if (users.isNotEmpty()) {
                        textViewError.visibility = View.GONE  // Скрыть сообщение об ошибке, если данные успешно загружены
                        userList.addAll(users)
                        userAdapter.notifyDataSetChanged()
                        currentPage++
                    } else {
                        isLastPage = true
                    }
                } else {
                    Log.e(TAG, "Error: ${response.code()} - ${response.message()}")
                    textViewError.text = "Error: ${response.code()} - ${response.message()}"
                    textViewError.visibility = View.VISIBLE
                    Toast.makeText(this@UserListActivity, "Error: ${response.code()} - ${response.message()}", Toast.LENGTH_SHORT).show()
                }

                isLoading = false
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(TAG, "Network error: ${t.message}", t)
                textViewError.text = "Network error: ${t.message}"
                textViewError.visibility = View.VISIBLE
                Toast.makeText(this@UserListActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                isLoading = false
            }
        })
    }
}
