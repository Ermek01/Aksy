package kg.smartpost.aksy.ui.items

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import kg.smartpost.aksy.R
import kg.smartpost.aksy.databinding.ActivitySearchBinding
import kg.smartpost.aksy.ui.items.utils.SearchAdapter

class SearchActivity : AppCompatActivity() {

    private var isAnimateCategory: Boolean = false
    private var isAnimateAddress: Boolean = false
    private var isAnimateSort: Boolean = false
    private var isAnimateCurrency: Boolean = false

    private val categories = mutableListOf<String>(
        "Баардыгы", "Кыймылсыз мүлк", "Унаалар", "Мал-чарба", "Электроника", "Алуу/Сатуу", "Каттам", "Жумуш"
    )

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

        val categoryAdapter = SearchAdapter(categories)
        binding.selectCategory.adapter = categoryAdapter
        categoryAdapter.notifyDataSetChanged()

        val addressAdapter = SearchAdapter(address)
        binding.selectAddress.adapter = addressAdapter
        addressAdapter.notifyDataSetChanged()

        val sortAdapter = SearchAdapter(sort)
        binding.selectSort.adapter = sortAdapter
        sortAdapter.notifyDataSetChanged()

        val currencyAdapter = SearchAdapter(currency)
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

    }

    private fun animate(up: Boolean): Animation {
        val anim: Animation =
            AnimationUtils.loadAnimation(this, if (up) R.anim.rotate_up else R.anim.rotate_down)
        anim.interpolator = LinearInterpolator() // for smooth animation
        return anim
    }
}