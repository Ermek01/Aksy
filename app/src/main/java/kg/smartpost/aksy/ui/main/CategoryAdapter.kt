package kg.smartpost.aksy.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import kg.smartpost.aksy.R
import kg.smartpost.aksy.data.network.category.CategoryModel

class CategoryAdapter(private var items: MutableList<CategoryModel>, private val listener: CategoryClickListener) :RecyclerView.Adapter<CategoryAdapter.CategoryItemViewHolder>() {

    private lateinit var context: Context

    inner class CategoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            val img = itemView.findViewById<CircleImageView>(R.id.img_category)
            val title = itemView.findViewById<TextView>(R.id.txt_name_category)

            img.setImageResource(items[position].icon)
            title.text = items[position].title

            itemView.setOnClickListener {
                listener.onCategoryClick(position, items[position].title)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_categories, parent, false)
        return CategoryItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface CategoryClickListener {
        fun onCategoryClick(position: Int, title: String)
    }

}