package kg.smartpost.aksy.ui.items.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kg.smartpost.aksy.R

class AddressAdapter(private val values: MutableList<String>) : RecyclerView.Adapter<AddressAdapter.CategoryItemViewHolder>() {

    private lateinit var context: Context

    inner class CategoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {

            itemView.findViewById<TextView>(R.id.txt_item).text = values[position]

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return CategoryItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return values.size
    }


}