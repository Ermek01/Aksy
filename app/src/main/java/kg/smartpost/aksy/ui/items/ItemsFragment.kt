package kg.smartpost.aksy.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import kg.smartpost.aksy.R
import kg.smartpost.aksy.data.network.category.model.ModelCategoryItem
import kg.smartpost.aksy.data.network.items.model.ModelItems
import kg.smartpost.aksy.databinding.FragmentAnnouncementBinding
import kg.smartpost.aksy.ui.chosen.ChosenFragment
import kg.smartpost.aksy.ui.items.utils.AnnouncementAdapter
import kg.smartpost.aksy.ui.items.utils.ItemsListener
import kg.smartpost.aksy.ui.items.viewmodels.ItemsViewModel
import kg.smartpost.aksy.ui.items.viewmodels.ItemsViewModelFactory
import kg.smartpost.aksy.ui.main.utils.CategoryAdapter
import kg.smartpost.aksy.ui.main.viewmodels.CategoryViewModel
import kg.smartpost.aksy.ui.main.viewmodels.CategoryViewModelFactory
import kg.smartpost.aksy.utils.SECRET_KEY
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ItemsFragment : Fragment(), AnnouncementAdapter.AnnouncementClickListener, KodeinAware, ItemsListener {

    override val kodein: Kodein by closestKodein()

    private lateinit var viewModel: ItemsViewModel
    private val viewModelFactory: ItemsViewModelFactory by instance()

    private var items = mutableListOf<ModelItems.Item>()

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentAnnouncementBinding? = null

    private lateinit var adapter: AnnouncementAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var title: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        title = arguments?.getString("title")
        _binding = FragmentAnnouncementBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ItemsViewModel::class.java)

        viewModel.setItemsListener(this)

        if (arguments != null) {
            when (title) {
                "all" -> {
                    viewModel.getItems(SECRET_KEY)
                }
            }
        }
        else {
            adapter = AnnouncementAdapter(this)
            binding.announcement.adapter = adapter
            adapter.notifyDataSetChanged()
        }



        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAnnouncementClick(position: Int, id: Int) {
        val fragment = ItemsDetailFragment()
        fragmentManager?.commit {
            replace(R.id.nav_host_fragment_content_main, fragment)
            addToBackStack(null)
            setReorderingAllowed(true)
        }
    }

    override fun getItemsSuccess(modelItems: ModelItems) {
        items.clear()
        items.addAll(modelItems.items)
        adapter = AnnouncementAdapter(this)
        binding.announcement.adapter = adapter
        adapter.submitList(items)
    }

    override fun getItemsFailure(code: Int?) {
        Toast.makeText(requireContext(), "$code", Toast.LENGTH_SHORT).show()
    }
}