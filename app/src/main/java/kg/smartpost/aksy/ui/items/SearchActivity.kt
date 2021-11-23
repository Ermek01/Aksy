package kg.smartpost.aksy.ui.items

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kg.smartpost.aksy.R
import kg.smartpost.aksy.data.network.category.model.ModelCategory
import kg.smartpost.aksy.data.network.category.model.ModelCategoryItem
import kg.smartpost.aksy.data.network.items.model.ModelSearchItems
import kg.smartpost.aksy.databinding.ActivitySearchBinding
import kg.smartpost.aksy.ui.items.utils.AddressAdapter
import kg.smartpost.aksy.ui.items.utils.SearchAdapter
import kg.smartpost.aksy.ui.items.utils.SearchListener
import kg.smartpost.aksy.ui.items.viewmodels.ItemsViewModel
import kg.smartpost.aksy.ui.items.viewmodels.ItemsViewModelFactory
import kg.smartpost.aksy.ui.main.utils.CategoryAdapter
import kg.smartpost.aksy.ui.main.utils.CategoryListener
import kg.smartpost.aksy.ui.main.viewmodels.CategoryViewModel
import kg.smartpost.aksy.ui.main.viewmodels.CategoryViewModelFactory
import kg.smartpost.aksy.utils.SECRET_KEY
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class SearchActivity : AppCompatActivity(), KodeinAware, CategoryListener, SearchListener {

    override val kodein: Kodein by closestKodein()

    private lateinit var categoryViewModel: CategoryViewModel
    private val categoryViewModelFactory: CategoryViewModelFactory by instance()

    private lateinit var itemsViewModel: ItemsViewModel
    private val itemsViewModelFactory: ItemsViewModelFactory by instance()

    private var categories = mutableListOf<ModelCategoryItem>()

    private lateinit var searchAdapter: SearchAdapter

    private var isAnimateCategory: Boolean = false
    private var isAnimateAddress: Boolean = false
    private var isAnimateSort: Boolean = false
    private var isAnimateCurrency: Boolean = false

    private val address = mutableListOf(
        "Ар кандай", "Ак-Жол", "Ак-Суу", "Жаңы-Жол", "Жерге-Тал", "Сары-Жыгыч", "Кашка-Суу"
    )

    private val sort = mutableListOf(
        "Жаңы жарыялар", "Башында арзандар", "Башында кымбаттар", "Көп көрүлгөндөр"
    )

    private val currency = mutableListOf(
        "Сом", "АКШ Доллар"
    )

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryViewModel = ViewModelProvider(this, categoryViewModelFactory).get(CategoryViewModel::class.java)
        itemsViewModel = ViewModelProvider(this, itemsViewModelFactory).get(ItemsViewModel::class.java)

        categoryViewModel.setFloorListener(this)
        itemsViewModel.setSearchListener(this)

        categoryViewModel.getCategories(SECRET_KEY)

        val addressAdapter = AddressAdapter(address)
        binding.selectAddress.adapter = addressAdapter
        addressAdapter.notifyDataSetChanged()

        val sortAdapter = AddressAdapter(sort)
        binding.selectSort.adapter = sortAdapter
        sortAdapter.notifyDataSetChanged()

        val currencyAdapter = AddressAdapter(currency)
        binding.selectCurrency.adapter = currencyAdapter
        currencyAdapter.notifyDataSetChanged()

        binding.btnSelectCategory.setOnClickListener {

            if (!isAnimateCategory) {
                binding.more.startAnimation(animate(isAnimateCategory))
                binding.selectCategory.visibility = View.VISIBLE
                isAnimateCategory = true
            }
            else {
                binding.more.startAnimation(animate(isAnimateCategory))
                binding.selectCategory.visibility = View.GONE
                isAnimateCategory = false
            }

        }

        binding.btnSelectAddress.setOnClickListener {

            if (!isAnimateAddress) {
                binding.moreAddress.startAnimation(animate(isAnimateAddress))
                binding.selectAddress.visibility = View.VISIBLE
                isAnimateAddress = true
            }
            else {
                binding.moreAddress.startAnimation(animate(isAnimateAddress))
                binding.selectAddress.visibility = View.GONE
                isAnimateAddress = false
            }

        }

        binding.btnSelectSort.setOnClickListener {

            if (!isAnimateSort) {
                binding.moreSort.startAnimation(animate(isAnimateSort))
                binding.selectSort.visibility = View.VISIBLE
                isAnimateSort = true
            }
            else {
                binding.moreSort.startAnimation(animate(isAnimateSort))
                binding.selectSort.visibility = View.GONE
                isAnimateSort = false
            }

        }

        binding.btnSelectCurreny.setOnClickListener {

            if (!isAnimateCurrency) {
                binding.moreCurrency.startAnimation(animate(isAnimateCurrency))
                binding.selectCurrency.visibility = View.VISIBLE
                isAnimateCurrency = true
            }
            else {
                binding.moreCurrency.startAnimation(animate(isAnimateCurrency))
                binding.selectCurrency.visibility = View.GONE
                isAnimateCurrency = false
            }

        }

        binding.etSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                itemsViewModel.searchItems(SECRET_KEY, s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }


        })

        binding.btnResult.setOnClickListener {
            val intent = Intent()
            intent.putExtra("search", binding.etSearch.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }

    }

    override fun getCategorySuccess(response: ModelCategory) {
        categories.clear()
        categories.addAll(response)
        searchAdapter = SearchAdapter()
        binding.selectCategory.adapter = searchAdapter
        searchAdapter.submitList(categories)
    }

    override fun getCategoryFailure(code: Int?) {
        Toast.makeText(this, "$code", Toast.LENGTH_SHORT).show()
    }

    private fun animate(up: Boolean): Animation {
        val anim: Animation =
            AnimationUtils.loadAnimation(this, if (up) R.anim.rotate_up else R.anim.rotate_down)
        anim.interpolator = LinearInterpolator() // for smooth animation
        return anim
    }

    @SuppressLint("SetTextI18n")
    override fun searchItemsSuccess(modelItems: ModelSearchItems) {
        binding.count.text = "(${modelItems.countItem})"
    }

    override fun searchItemsFailure(code: Int?) {

    }
}