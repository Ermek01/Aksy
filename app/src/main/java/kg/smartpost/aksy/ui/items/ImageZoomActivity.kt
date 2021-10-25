package kg.smartpost.aksy.ui.items

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kg.smartpost.aksy.R
import kg.smartpost.aksy.databinding.ActivityImageZoomBinding

import kg.smartpost.aksy.ui.items.utils.ImagePagerAdapter
import kg.smartpost.aksy.ui.items.utils.ImagePagerAdapterZoom

class ImageZoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageZoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageZoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ImagePagerAdapterZoom()
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager, true)

    }
}