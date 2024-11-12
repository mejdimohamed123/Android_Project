package tn.esprit.gestionpost

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tn.esprit.gestionpost.Api.PostApi
import tn.esprit.gestionpost.Data.Post
import tn.esprit.gestionpost.Data.PostState

class PostAdapter(private val context: Context) :
    ListAdapter<Post, PostAdapter.PostViewHolder>(DiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_post, parent, false)

        return PostViewHolder(view)
    }
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)

//        Glide.with(holder.postImage)
//            .load("http://192.168.207.249:5004/" + "images/" + post.image)
//            .placeholder(R.drawable.velo)
//            .override(100, 100)
//            .error(com.google.android.material.R.drawable.mtrl_ic_error)
//            .into(holder.postImage)

        holder.title.text = post.title
        holder.date.text = post.date

        holder.likeButton.setOnClickListener {
            // Handle the like button click
            holder.likeButton.visibility = View.GONE
            holder.likedButton.visibility = View.VISIBLE
        }

        holder.likedButton.setOnClickListener {
            // Handle the liked button click
            holder.likeButton.visibility = View.VISIBLE
            holder.likedButton.visibility = View.GONE
        }
        holder.updateButton.setOnClickListener {
            val intent = Intent(context, AddPostActivity::class.java)
            context.startActivity(intent)
        }

        holder.commentButton.setOnClickListener {
            val intent = Intent(context, CommentActivity::class.java)
            context.startActivity(intent)
        }

        if (post.state == PostState.PRIVATE) {
            holder.stateIcon.setImageResource(R.drawable.onlyme)
        } else if (post.state == PostState.FRIENDS_ONLY) {
            holder.stateIcon.setImageResource(R.drawable.friendsonly)
        } else if (post.state == PostState.PUBLIC) {
            holder.stateIcon.setImageResource(R.drawable.group)
        }


        holder.deleteButton.setOnClickListener {
            showDeleteConfirmationDialog(post._id)
        }



    }
    override fun getItemCount() = currentList.size



    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("ResourceType")
        val postImage: ImageView = itemView.findViewById(R.drawable.velo)
        val title: TextView = itemView.findViewById(R.id.post_title)
        val likeButton: ImageButton = itemView.findViewById(R.id.like)
        val likedButton: ImageButton = itemView.findViewById(R.id.liked)
        val date: TextView = itemView.findViewById(R.id.date)
        val stateIcon: ImageView = itemView.findViewById(R.id.state)  // Assuming you have an ImageView for the state icon
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete) // Add this line
        val updateButton: ImageButton = itemView.findViewById(R.id.update) // Add this line
        val shareButton: ImageButton = itemView.findViewById(R.id.share) // Add this line

        val commentButton: ImageButton = itemView.findViewById(R.id.comment)

        init {
            // ... (your existing initialization code)
            commentButton.setOnClickListener {
                val intent = Intent(context, CommentActivity::class.java)
                context.startActivity(intent)
            }

            shareButton.setOnClickListener {
                val post = getItem(adapterPosition)
                val shareContent = "Check out this post: ${post.title}"
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareContent)
                context.startActivity(Intent.createChooser(shareIntent, "Share via"))
            }
        }
        init {
            updateButton.setOnClickListener {
                val intent = Intent(context, AddPostActivity::class.java)
                context.startActivity(intent)
            }
        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    private fun showDeleteConfirmationDialog(postId: String) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Delete Post")
        alertDialogBuilder.setMessage("Are you sure you want to delete this post?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            deletePost(postId)
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun deletePost(postId: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.207.249:5004/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val postApi = retrofit.create(PostApi::class.java)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = postApi.deletePost(postId)

                if (response == "Deleted") {
                    Toast.makeText(context, "Post deleted successfully", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Error deleting post", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error deleting post", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }




}
