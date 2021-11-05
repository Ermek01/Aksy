package kg.smartpost.aksy.ui.main.utils

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.smartpost.aksy.R
import kg.smartpost.aksy.data.network.category.model.ModelCategoryItem
import kg.smartpost.aksy.databinding.RowCategoriesBinding


class CategoryAdapter(
    private val categoryClickListener: CategoryClickListener,
    private val icons: MutableList<Int>
) :
    ListAdapter<ModelCategoryItem, CategoryAdapter.ViewHolderCat>(DIFF) {

    fun getItemAtPos(position: Int): ModelCategoryItem {
        return getItem(position)
    }

    private var _binding: RowCategoriesBinding? = null

    inner class ViewHolderCat(private val binding: RowCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val current = getItemAtPos(position)

            Glide.with(binding.root).load(icons[position])
                .into(binding.imgCategory)

            binding.txtNameCategory.text = current.title

            binding.root.setOnClickListener {
                categoryClickListener.onCategoryClick(position)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCat {
        _binding = RowCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    interface CategoryClickListener {
        fun onCategoryClick(position: Int)
    }

}