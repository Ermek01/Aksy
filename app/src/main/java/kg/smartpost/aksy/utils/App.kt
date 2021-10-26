package kg.smartpost.aksy.utils

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import kg.smartpost.aksy.data.network.ApiService
import kg.smartpost.aksy.data.network.category.repo.CategoryRepository
import kg.smartpost.aksy.ui.main.viewmodels.CategoryViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class App : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {

        import(androidXModule(this@App))

        AndroidThreeTen.init(this@App)

        bind() from singleton { ApiService() }

        bind<CategoryRepository>() with singleton { CategoryRepository(instance()) }
        bind() from provider { CategoryViewModelFactory(instance()) }

    }

}