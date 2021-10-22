package kg.smartpost.aksy.ui.items.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kg.smartpost.aksy.R

class AnnouncementAdapter(private val listener: AnnouncementClickListener) :RecyclerView.Adapter<AnnouncementAdapter.CategoryItemViewHolder>() {

    private lateinit var context: Context

    inner class CategoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {

            itemView.findViewById<ImageView>(R.id.btn_chosen).setOnClickListener {
                if (itemView.findViewById<ImageView>(R.id.btn_chosen).drawable.constantState == ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_chosen_disable
                    )?.constantState
                ) {
                    itemView.findViewById<ImageView>(R.id.btn_chosen)
                        .setImageResource(R.drawable.ic_chosen_enable)

                } else {
                    itemView.findViewById<ImageView>(R.id.btn_chosen)
                        .setImageResource(R.drawable.ic_chosen_disable)
                }
            }

            itemView.setOnClickListener {
                listener.onAnnouncementClick(position)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_items, parent, false)
        return CategoryItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return 10
    }

    interface AnnouncementClickListener{
        fun onAnnouncementClick(position: Int)
    }


}