package kg.smartpost.aksy.ui.items

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kg.smartpost.aksy.R
import kg.smartpost.aksy.data.network.category.model.ModelCategory
import kg.smartpost.aksy.data.network.category.model.ModelCategoryItem
import kg.smartpost.aksy.data.network.items.model.ModelItems
import kg.smartpost.aksy.databinding.FragmentAnnouncementBinding
import kg.smartpost.aksy.ui.chosen.ChosenFragment
import kg.smartpost.aksy.ui.items.utils.AnnouncementAdapter
import kg.smartpost.aksy.ui.items.utils.ItemsListener
import kg.smartpost.aksy.ui.items.viewmodels.ItemsViewModel
import kg.smartpost.aksy.ui.items.viewmodels.ItemsViewModelFactory
import kg.smartpost.aksy.ui.main.utils.CategoryAdapter
import kg.smartpost.aksy.ui.main.utils.CategoryListener
import kg.smartpost.aksy.ui.main.viewmodels.CategoryViewModel
import kg.smartpost.aksy.ui.main.viewmodels.CategoryViewModelFactory
import kg.smartpost.aksy.utils.SECRET_KEY
import kg.smartpost.aksy.utils.hide
import kg.smartpost.aksy.utils.show
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ItemsFragment : Fragment(), AnnouncementAdapter.AnnouncementClickListener, KodeinAware, ItemsListener, CategoryListener, CategoryAdapter.CategoryClickListener {

    override val kodein: Kodein by closestKodein()

    private lateinit var viewModel: ItemsViewModel
    private val viewModelFactory: ItemsViewModelFactory by instance()

    private lateinit var categoryViewModel: CategoryViewModel
    private val categoryViewModelFactory: CategoryViewModelFactory by instance()

    private var items = mutableListOf<ModelItems.Item>()

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentAnnouncementBinding? = null

    private lateinit var adapter: AnnouncementAdapter
    private lateinit var categoryAdaper: CategoryAdapter

    private var categories = mutableListOf<ModelCategoryItem>()

    var icons = mutableListOf(
        R.drawable.ic_all_category,
        R.drawable.ic_home,
        R.drawable.ic_car_category,
        R.drawable.ic_animals_category,
        R.drawable.ic_sell,
        R.drawable.ic_work,
        R.drawable.ic_electronics,
        R.drawable.ic_route,
    )

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var id: Int? = null
    private var page: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnnouncementBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryViewModel = ViewModelProvider(this, categoryViewModelFactory).get(CategoryViewModel::class.java)

        categoryViewModel.setFloorListener(this)

        categoryViewModel.getCategories(SECRET_KEY)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ItemsViewModel::class.java)

        viewModel.setItemsListener(this)

        binding.prBar.show()
        viewModel.getItems(SECRET_KEY, page)

        binding.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                binding.prBar.show()
                page++
                viewModel.getItems(SECRET_KEY, page)
            }
        }




        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAnnouncementClick(position: Int, id: Int) {
        val bundle = Bundle()
        bundle.putInt("id", items[position].id)
        findNavController().navigate(R.id.itemsDetailFragment, bundle)
    }

    override fun getItemsSuccess(modelItems: ModelItems) {
        if (page == 1)
            items.clear()
        items.addAll(modelItems.items)
        adapter = AnnouncementAdapter(this)
        binding.announcement.adapter = adapter
        adapter.submitList(items)
        binding.prBar.hide()
    }

    override fun getItemsFailure(code: Int?) {
        Toast.makeText(requireContext(), "$code", Toast.LENGTH_SHORT).show()
    }

    override fun getCategorySuccess(response: ModelCategory) {
        categories.clear()
        categories.addAll(response)
        categoryAdaper = CategoryAdapter(this, icons)
        binding.categories.adapter = categoryAdaper
        categoryAdaper.submitList(response)
    }

    override fun getCategoryFailure(code: Int?) {
        Toast.makeText(requireContext(), "$code", Toast.LENGTH_SHORT).show()
    }

    override fun onCategoryClick(position: Int) {
        binding.prBar.show()
        viewModel.getItems(SECRET_KEY, page)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123) {
            when(resultCode) {

                RESULT_OK -> {

                    val search = data?.getStringExtra("search")
                    val bundle = Bundle()
                    bundle.putString("search", search)
                    findNavController().navigate(R.id.searchFragment, bundle)
                }

            }
        }

    }
}