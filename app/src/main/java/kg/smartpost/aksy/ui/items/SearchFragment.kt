package kg.smartpost.aksy.ui.items

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kg.smartpost.aksy.R
import kg.smartpost.aksy.data.network.category.model.ModelCategoryItem
import kg.smartpost.aksy.data.network.items.model.ModelSearchItems
import kg.smartpost.aksy.databinding.FragmentSearchBinding
import kg.smartpost.aksy.ui.items.utils.AnnouncementAdapter
import kg.smartpost.aksy.ui.items.utils.SearchAnnouncementAdapter
import kg.smartpost.aksy.ui.items.utils.SearchListener
import kg.smartpost.aksy.ui.items.viewmodels.ItemsViewModel
import kg.smartpost.aksy.ui.items.viewmodels.ItemsViewModelFactory
import kg.smartpost.aksy.utils.SECRET_KEY
import kg.smartpost.aksy.utils.hide
import kg.smartpost.aksy.utils.show
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class SearchFragment : Fragment(), KodeinAware, SearchListener, SearchAnnouncementAdapter.AnnouncementClickListener {

    override val kodein: Kodein by closestKodein()

    private lateinit var viewModel: ItemsViewModel
    private val viewModelFactory: ItemsViewModelFactory by instance()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SearchAnnouncementAdapter

    private var items = mutableListOf<ModelSearchItems.Item>()

    private var page: Int = 1

    var text: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {

            text = arguments?.getString("search")

        }

        viewModel = ViewModelProvider(this, viewModelFactory).get(ItemsViewModel::class.java)

        viewModel.setSearchListener(this)

        binding.prBar.show()
        viewModel.searchItems(SECRET_KEY, text)

        binding.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                binding.prBar.show()
                page++
                viewModel.searchItems(SECRET_KEY, text)
            }
        }

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun searchItemsSuccess(modelItems: ModelSearchItems) {
        items.clear()
        items.addAll(modelItems.items)
        adapter = SearchAnnouncementAdapter(this)
        binding.searchList.adapter = adapter
        adapter.submitList(modelItems.items)
        binding.prBar.hide()
    }

    override fun searchItemsFailure(code: Int?) {

    }

    override fun onAnnouncementClick(position: Int, id: Int) {
        val bundle = Bundle()
        bundle.putInt("id", items[position].id)
        findNavController().navigate(R.id.itemsDetailFragment, bundle)
    }
}