package kg.smartpost.aksy.ui.items.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.smartpost.aksy.R
import kg.smartpost.aksy.data.network.category.model.ModelCategoryItem
import kg.smartpost.aksy.databinding.ItemSearchBinding
import kg.smartpost.aksy.databinding.RowCategoriesBinding

class SearchAdapter :
    ListAdapter<ModelCategoryItem, SearchAdapter.ViewHolderCat>(DIFF) {

    fun getItemAtPos(position: Int): ModelCategoryItem {
        return getItem(position)
    }

    private var _binding: ItemSearchBinding? = null

    inner class ViewHolderCat(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val current = getItemAtPos(position)

            binding.txtItem.text = current.title

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCat {
        _binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderCat(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderCat, position: Int) {
        holder.onBind(position)
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<ModelCategoryItem>() {
            override fun areItemsTheSame(
                oldItem: ModelCategoryItem,
                newItem: ModelCategoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ModelCategoryItem,
                newItem: ModelCategoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}