package kg.smartpost.aksy.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.snackbar.Snackbar
import kg.smartpost.aksy.R
import kg.smartpost.aksy.databinding.ActivityMainBinding
import kg.smartpost.aksy.ui.items.ItemsFragment
import kg.smartpost.aksy.ui.items.SearchActivity
import kg.smartpost.aksy.ui.main.utils.CategoryAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.btnAnnouncement.setOnClickListener {

            Handler().postDelayed({
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }, 200)
        }

        binding.appBarMain.btnSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivityForResult(intent, 123)
        }

        binding.appBarMain.btnOpenDrawer.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

//        binding.appBarMain.contentMain.imgBack.setOnClickListener {
//            supportFragmentManager.popBackStack()
//            binding.appBarMain.contentMain.categories.visibility = View.VISIBLE
//            binding.appBarMain.contentMain.txtChosen.visibility = View.GONE
//            binding.appBarMain.contentMain.imgBack.visibility = View.GONE
//        }


        binding.btnAbout.setOnClickListener {
            Handler().postDelayed({
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }, 200)
        }

        binding.btnChosen.setOnClickListener {
            val navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
            navController.navigate(R.id.chosenFragment)
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

//            val f = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
//            if (f is ChosenFragment) {
//                binding.appBarMain.contentMain.categories.visibility = View.VISIBLE
//                binding.appBarMain.contentMain.txtChosen.visibility = View.GONE
//                binding.appBarMain.contentMain.imgBack.visibility = View.GONE
//            }

            supportFragmentManager.popBackStack()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.fragments?.forEach { fragment ->
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}