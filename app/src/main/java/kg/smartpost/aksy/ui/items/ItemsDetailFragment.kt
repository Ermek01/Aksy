package kg.smartpost.aksy.ui.items

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kg.smartpost.aksy.R
import kg.smartpost.aksy.data.network.items.model.ModelItemDetail
import kg.smartpost.aksy.databinding.FragmentItemsDetailBinding
import kg.smartpost.aksy.ui.items.utils.*
import kg.smartpost.aksy.ui.items.viewmodels.ItemsViewModel
import kg.smartpost.aksy.ui.items.viewmodels.ItemsViewModelFactory
import kg.smartpost.aksy.utils.SECRET_KEY
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class ItemsDetailFragment : Fragment(), ImagePagerAdapter.ImageClickListener, KodeinAware, ItemsByIdListener {

    override val kodein: Kodein by closestKodein()

    private lateinit var adapter: SimilarAdapter

    private var _binding: FragmentItemsDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ItemsViewModel
    private val viewModelFactory: ItemsViewModelFactory by instance()

    val items = arrayListOf<String>()
    val phones = arrayListOf<String>()

    private var isAnimate: Boolean = false

    var id: Int? = null

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

        viewModel = ViewModelProvider(this, viewModelFactory).get(ItemsViewModel::class.java)

        if (arguments != null) {
            id = arguments?.getInt("id")
        }

        viewModel.setItemsByIdListener(this)

        viewModel.getItemsById(SECRET_KEY, id!!)

        adapter = SimilarAdapter()
        binding.announcement.adapter = adapter
        adapter.notifyDataSetChanged()

//        binding.btnMore.setOnClickListener {
//
//            if (!isAnimate) {
//                binding.more.startAnimation(animate(isAnimate))
//                binding.txtDesc.visibility = View.VISIBLE
//                isAnimate = true
//            }
//            else {
//                binding.more.startAnimation(animate(isAnimate))
//                binding.txtDesc.visibility = View.GONE
//                isAnimate = false
//            }
//
//        }

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

        binding.btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL);
            intent.data = Uri.parse("tel:${phones[0]}")
            startActivity(intent)
        }

        binding.btnShare.setOnClickListener {
            val dialog = ShareBottomDialog()
            dialog.show(requireFragmentManager(), "chooseIn")
        }

        binding.btnWrite.setOnClickListener {
            val dialog = WriteBottomDialog(phones)
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
        intent.putStringArrayListExtra("list", items)
        startActivity(intent)
        //Animatoo.animateZoom(requireActivity())
    }

    override fun getItemsSuccess(response: ModelItemDetail) {
        items.clear()
        items.addAll(response.item.photos)
        phones.addAll(response.item.phone)
        val adapter = ImagePagerAdapter(this, response.item.photos, requireContext())
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager, true)
        binding.txtDesc.text = response.item.text
    }

    override fun getItemsFailure(code: Int?) {

    }

}