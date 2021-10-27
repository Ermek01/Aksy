package kg.smartpost.aksy.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.snackbar.Snackbar
import kg.smartpost.aksy.R
import kg.smartpost.aksy.data.network.category.model.ModelCategory
import kg.smartpost.aksy.data.network.category.model.ModelCategoryItem
import kg.smartpost.aksy.data.network.category.model.ModelSendKey
import kg.smartpost.aksy.databinding.ActivityMainBinding
import kg.smartpost.aksy.ui.items.ItemsFragment
import kg.smartpost.aksy.ui.chosen.ChosenFragment
import kg.smartpost.aksy.ui.gallery.GalleryFragment
import kg.smartpost.aksy.ui.items.ItemsDetailFragment
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
import java.util.ArrayList

class MainActivity : AppCompatActivity(), CategoryAdapter.CategoryClickListener, KodeinAware, CategoryListener {

    override val kodein: Kodein by closestKodein()

    private lateinit var categoryViewModel: CategoryViewModel
    private val categoryViewModelFactory: CategoryViewModelFactory by instance()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: CategoryAdapter

    private var categories = mutableListOf<ModelCategoryItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        categoryViewModel = ViewModelProvider(this, categoryViewModelFactory).get(CategoryViewModel::class.java)

        categoryViewModel.setFloorListener(this)

        categoryViewModel.getCategories(SECRET_KEY)


        val bundle = Bundle()
        bundle.putString("title", "all")
        val homeFragment = ItemsFragment()
        homeFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_main, homeFragment).commit()

        binding.btnAnnouncement.setOnClickListener {
            val homeFragment = ItemsFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, homeFragment).commit()
            binding.appBarMain.contentMain.categories.visibility = View.VISIBLE
            binding.appBarMain.contentMain.txtAppName.visibility = View.VISIBLE
            binding.appBarMain.contentMain.txtChosen.visibility = View.GONE
            binding.appBarMain.contentMain.imgBack.visibility = View.GONE
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
            binding.appBarMain.contentMain.txtAppName.visibility = View.VISIBLE
            binding.appBarMain.contentMain.txtChosen.visibility = View.GONE
            binding.appBarMain.contentMain.imgBack.visibility = View.GONE
        }


        binding.btnAbout.setOnClickListener {
            val galleryFragment = GalleryFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, galleryFragment).commit()
            binding.appBarMain.contentMain.categories.visibility = View.VISIBLE
            binding.appBarMain.contentMain.txtChosen.visibility = View.GONE
            binding.appBarMain.contentMain.imgBack.visibility = View.GONE
            binding.appBarMain.contentMain.txtAppName.visibility = View.GONE
            Handler().postDelayed({
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }, 200)
        }

        binding.btnChosen.setOnClickListener {
            val slideshowFragment = ChosenFragment()
            supportFragmentManager.commit {
                add(R.id.nav_host_fragment_content_main, slideshowFragment)
                addToBackStack(null)
                setReorderingAllowed(true)
            }

//            supportFragmentManager.beginTransaction()
//                .add(R.id.nav_host_fragment_content_main, slideshowFragment).commit()
            binding.appBarMain.contentMain.categories.visibility = View.GONE
            binding.appBarMain.contentMain.txtChosen.visibility = View.VISIBLE
            binding.appBarMain.contentMain.imgBack.visibility = View.VISIBLE
            binding.appBarMain.contentMain.txtAppName.visibility = View.GONE
            Handler().postDelayed({
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }, 200)
        }

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
//        val drawerLayout: DrawerLayout = binding.drawerLayout
//        val navView: NavigationView = binding.navView
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
//            ), drawerLayout
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            // Checking for fragment count on back stack
            if (supportFragmentManager.backStackEntryCount > 0) {

                val f = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
                if (f is ChosenFragment) {
                    binding.appBarMain.contentMain.categories.visibility = View.VISIBLE
                    binding.appBarMain.contentMain.txtAppName.visibility = View.VISIBLE
                    binding.appBarMain.contentMain.txtChosen.visibility = View.GONE
                    binding.appBarMain.contentMain.imgBack.visibility = View.GONE
                }

                supportFragmentManager.popBackStack()
            } else {
                // Exit the app
                super.onBackPressed()
            }
        }
    }

    override fun onCategoryClick(position: Int) {
        if (supportFragmentManager.backStackEntryCount > 0) {

            val f = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
            if (f is ItemsDetailFragment) {
                supportFragmentManager.popBackStack()
            }
        }
        else {
            val bundle = Bundle()
            bundle.putString("title", "title")
            val homeFragment = ItemsFragment()
            homeFragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, homeFragment).commit()
        }

    }

    override fun getCategorySuccess(response: ModelCategory) {
        categories.clear()
        categories.addAll(response)
        adapter = CategoryAdapter(this)
        binding.appBarMain.contentMain.categories.adapter = adapter
        adapter.submitList(categories)
    }

    override fun getCategoryFailure(code: Int?) {
        Toast.makeText(this, "$code", Toast.LENGTH_SHORT).show()
    }
}