package kg.smartpost.aksy.ui.chosen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kg.smartpost.aksy.databinding.FragmentChosenBinding
import kg.smartpost.aksy.ui.chosen.utils.ChosenAdapter

class ChosenFragment : Fragment() {

    private var _binding: FragmentChosenBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ChosenAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChosenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ChosenAdapter()
        binding.chosen.adapter = adapter
        adapter.notifyDataSetChanged()

    }

}