package kg.smartpost.aksy.ui.items.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import kg.smartpost.aksy.R
import kg.smartpost.aksy.ui.items.ImageZoomActivity
import java.util.ArrayList

class ImagePagerAdapterZoom(
    private val list: ArrayList<String>?,
    private val imageZoomActivity: ImageZoomActivity
) : PagerAdapter() {



    override fun getCount(): Int {
        return list!!.size
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
        if (!list.isNullOrEmpty())
            Glide.with(imageZoomActivity).load(list[position])
                .into(imageView)
        container.addView(view)
        return view
    }

    interface ImageClickListener {
        fun onImageClick(position: Int)
    }

}