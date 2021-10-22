package kg.smartpost.aksy.ui.items.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import kg.smartpost.aksy.R

class ImagePagerAdapter(private val listener : ImageClickListener): PagerAdapter() {

    private val drawables =
        intArrayOf(R.drawable.car_detail, R.drawable.car_detail, R.drawable.car_detail)

    override fun getCount(): Int {
        return 3
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =
            LayoutInflater.from(container.context).inflate(R.layout.page_images, container, false)
        val imageView = view.findViewById<ImageView>(R.id.img_shop_page)
        imageView.setImageResource(drawables[position])
        container.addView(view)

        view.setOnClickListener {
            listener.onImageClick(position)
        }

        return view
    }

    interface ImageClickListener {
        fun onImageClick(position: Int)
    }

}