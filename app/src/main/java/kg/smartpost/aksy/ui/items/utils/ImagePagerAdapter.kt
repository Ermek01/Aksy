package kg.smartpost.aksy.ui.items.utils

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import kg.smartpost.aksy.R

class ImagePagerAdapter(
    private val listener: ImageClickListener,
    private val photos: List<String>,
    private val requireContext: Context
): PagerAdapter() {

    private val drawables =
        intArrayOf(R.drawable.car_detail, R.drawable.car_detail, R.drawable.car_detail)

    override fun getCount(): Int {
        return photos.size
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
        if (!photos.isNullOrEmpty())
            Glide.with(requireContext).load(photos[position])
                .error(ContextCompat.getDrawable(requireContext, R.drawable.def_image))
                .into(imageView)
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

