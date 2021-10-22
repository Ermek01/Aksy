package kg.smartpost.aksy.ui.items

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kg.smartpost.aksy.R
import kg.smartpost.aksy.databinding.FragmentItemsDetailBinding
import kg.smartpost.aksy.ui.items.utils.ImagePagerAdapter
import kg.smartpost.aksy.ui.items.utils.ShareBottomDialog
import kg.smartpost.aksy.ui.items.utils.SimilarAdapter
import kg.smartpost.aksy.ui.items.utils.WriteBottomDialog


class ItemsDetailFragment : Fragment(), ImagePagerAdapter.ImageClickListener {

    private lateinit var adapter: SimilarAdapter

    private var _binding: FragmentItemsDetailBinding? = null
    private val binding get() = _binding!!

    private var isAnimate: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SimilarAdapter()
        binding.announcement.adapter = adapter
        adapter.notifyDataSetChanged()

        val adapter = ImagePagerAdapter(this)
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager, true)

        binding.btnMore.setOnClickListener {

            if (!isAnimate) {
                binding.more.startAnimation(animate(isAnimate))
                binding.txtDesc.visibility = View.VISIBLE
                isAnimate = true
            }
            else {
                binding.more.startAnimation(animate(isAnimate))
                binding.txtDesc.visibility = View.GONE
                isAnimate = false
            }

        }

        binding.imgChosen.setOnClickListener {
            if (binding.imgChosen.drawable.constantState == ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_chosen_disable
                )?.constantState
            ) {
                binding.imgChosen
                    .setImageResource(R.drawable.ic_chosen_enable)

            } else {
                binding.imgChosen
                    .setImageResource(R.drawable.ic_chosen_disable)
            }
        }

        binding.btnShare.setOnClickListener {
            val dialog = ShareBottomDialog()
            dialog.show(requireFragmentManager(), "chooseIn")
        }

        binding.btnWrite.setOnClickListener {
            val dialog = WriteBottomDialog()
            dialog.show(requireFragmentManager(), "chooseIn")
        }

    }

    private fun animate(up: Boolean): Animation {
        val anim: Animation =
            AnimationUtils.loadAnimation(requireContext(), if (up) R.anim.rotate_up else R.anim.rotate_down)
        anim.interpolator = LinearInterpolator() // for smooth animation
        return anim
    }

    override fun onImageClick(position: Int) {
        val intent = Intent(requireActivity(), ImageZoomActivity::class.java)
        startActivity(intent)
        //Animatoo.animateZoom(requireActivity())
    }

}