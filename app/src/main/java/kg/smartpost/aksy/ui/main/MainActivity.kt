package kg.smartpost.aksy.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.snackbar.Snackbar
import kg.smartpost.aksy.R
import kg.smartpost.aksy.data.network.category.model.ModelCategory
import kg.smartpost.aksy.data.network.category.model.ModelCategoryItem
import kg.smartpost.aksy.databinding.ActivityMainBinding
import kg.smartpost.aksy.ui.chosen.ChosenFragment
import kg.smartpost.aksy.ui.items.ItemsDetailFragment
import kg.smartpost.aksy.ui.items.ItemsFragment
import kg.smartpost.aksy.ui.items.SearchActivity
import kg.smartpost.aksy.ui.main.utils.CategoryAdapter
import kg.smartpost.aksy.ui.main.utils.CategoryListener
import kg.smartpost.aksy.ui.main.viewmodels.CategoryViewModel
import kg.smartpost.aksy.ui.main.viewmodels.CategoryViewModelFactory
import kg.smartpost.aksy.utils.SECRET_KEY
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), CategoryAdapter.CategoryClickListener, KodeinAware, CategoryListener {

    override val kodein: Kodein by closestKodein()

    private lateinit var categoryViewModel: CategoryViewModel
    private val categoryViewModelFactory: CategoryViewModelFactory by instance()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: CategoryAdapter

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        categoryViewModel = ViewModelProvider(this, categoryViewModelFactory).get(CategoryViewModel::class.java)

        categoryViewModel.setFloorListener(this)

        categoryViewModel.getCategories(SECRET_KEY)


        binding.btnAnnouncement.setOnClickListener {

            Handler().postDelayed({
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }, 200)
        }

        binding.appBarMain.btnSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        binding.appBarMain.btnOpenDrawer.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.appBarMain.contentMain.imgBack.setOnClickListener {
            supportFragmentManager.popBackStack()
            binding.appBarMain.contentMain.categories.visibility = View.VISIBLE
            binding.appBarMain.contentMain.txtChosen.visibility = View.GONE
            binding.appBarMain.contentMain.imgBack.visibility = View.GONE
        }


        binding.btnAbout.setOnClickListener {
            Handler().postDelayed({
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }, 200)
        }

        binding.btnChosen.setOnClickListener {
            val chosenFragment = ChosenFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.nav_host_fragment_content_main, chosenFragment)
                .addToBackStack(null)
                .commit()
            binding.appBarMain.contentMain.categories.visibility = View.GONE
            binding.appBarMain.contentMain.txtChosen.visibility = View.VISIBLE
            binding.appBarMain.contentMain.imgBack.visibility = View.VISIBLE
            Handler().postDelayed({
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }, 200)
        }

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {

            val f = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
            if (f is ChosenFragment) {
                binding.appBarMain.contentMain.categories.visibility = View.VISIBLE
                binding.appBarMain.contentMain.txtChosen.visibility = View.GONE
                binding.appBarMain.contentMain.imgBack.visibility = View.GONE
            }

            supportFragmentManager.popBackStack()
        }
    }

    override fun onCategoryClick(position: Int) {
        val bundle = Bundle()
        bundle.putString("title", "title")
        val itemsFragment = ItemsFragment()
        itemsFragment.arguments = bundle
    }

    override fun getCategorySuccess(response: ModelCategory) {
        categories.clear()
        categories.addAll(response)
        adapter = CategoryAdapter(this, icons)
        binding.appBarMain.contentMain.categories.adapter = adapter
        adapter.submitList(categories)
    }

    override fun getCategoryFailure(code: Int?) {
        Toast.makeText(this, "$code", Toast.LENGTH_SHORT).show()
    }
}