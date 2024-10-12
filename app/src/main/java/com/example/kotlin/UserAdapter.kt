package com.example.kotlin

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter(
    private val context: Context,
    private val users: List<User>
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewUserName: TextView = view.findViewById(R.id.userName)
        val textViewUserEmail: TextView = view.findViewById(R.id.userEmail)
        val imageViewAvatar: ImageView = view.findViewById(R.id.userAvatar)
        val buttonFollow: Button = view.findViewById(R.id.buttonFollow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]

        holder.textViewUserName.text = "${user.first_name} ${user.last_name}"
        holder.textViewUserEmail.text = user.email

        Glide.with(context)
            .load(user.avatar)
            .into(holder.imageViewAvatar)

        val isFollowed = sharedPreferences.getBoolean("followed_${user.id}", false)
        holder.buttonFollow.text = if (isFollowed) "Unfollow" else "Follow"

        holder.buttonFollow.setOnClickListener {
            toggleFollow(user)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    private fun toggleFollow(user: User) {
        val editor = sharedPreferences.edit()
        val isFollowed = sharedPreferences.getBoolean("followed_${user.id}", false)

        if (isFollowed) {
            editor.putBoolean("followed_${user.id}", false)
        } else {
            editor.putBoolean("followed_${user.id}", true)
        }
        editor.apply()
    }
}
