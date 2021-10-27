package kg.smartpost.aksy.ui.items.utils

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kg.smartpost.aksy.R
import kg.smartpost.aksy.data.network.category.model.ModelCategoryItem
import kg.smartpost.aksy.data.network.items.model.ModelItems
import kg.smartpost.aksy.databinding.RowCategoriesBinding
import kg.smartpost.aksy.databinding.RowItemsBinding

class AnnouncementAdapter(private val announcementClickListener: AnnouncementClickListener) :
    ListAdapter<ModelItems.Item, AnnouncementAdapter.ViewHolderCat>(DIFF) {

    fun getItemAtPos(position: Int): ModelItems.Item {
        return getItem(position)
    }

    private var _binding: RowItemsBinding? = null

    inner class ViewHolderCat(private val binding: RowItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val current = getItemAtPos(position)

            if (!current.photos.isNullOrEmpty())
                Glide.with(binding.root).load(current.photos[0])
                    .override(Resources.getSystem().displayMetrics.widthPixels)
                    .error(ContextCompat.getDrawable(binding.root.context, R.drawable.def_image))
                    .into(binding.img)

            binding.name.text = current.text
            binding.date.text = current.created

            binding.btnChosen.setOnClickListener {
                if (binding.btnChosen.drawable.constantState == ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_chosen_disable
                    )?.constantState
                ) {
                    binding.btnChosen
                        .setImageResource(R.drawable.ic_chosen_enable)

                } else {
                    binding.btnChosen
                        .setImageResource(R.drawable.ic_chosen_disable)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCat {
        _binding = RowItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderCat(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderCat, position: Int) {
        holder.onBind(position)
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<ModelItems.Item>() {
            override fun areItemsTheSame(oldItem: ModelItems.Item, newItem: ModelItems.Item): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ModelItems.Item,
                newItem: ModelItems.Item
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    interface AnnouncementClickListener {
        fun onAnnouncementClick(position: Int, id: Int)
    }

}

//class AnnouncementAdapter(private val listener: AnnouncementClickListener) :RecyclerView.Adapter<AnnouncementAdapter.CategoryItemViewHolder>() {
//
//    private lateinit var context: Context
//
//    inner class CategoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        fun bind(position: Int) {
//

//
//            itemView.setOnClickListener {
//                listener.onAnnouncementClick(position)
//            }
//
//        }
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
//        context = parent.context
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_items, parent, false)
//        return CategoryItemViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
//        holder.bind(position)
//    }
//
//    override fun getItemCount(): Int {
//        return 10
//    }
//
//    interface AnnouncementClickListener{
//        fun onAnnouncementClick(position: Int)
//    }
//
//
//}