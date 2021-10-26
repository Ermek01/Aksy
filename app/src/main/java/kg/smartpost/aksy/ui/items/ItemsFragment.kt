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
import kg.smartpost.aksy.databinding.FragmentAnnouncementBinding
import kg.smartpost.aksy.ui.chosen.ChosenFragment
import kg.smartpost.aksy.ui.items.utils.AnnouncementAdapter

class ItemsFragment : Fragment(), AnnouncementAdapter.AnnouncementClickListener {

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

        if (arguments != null) {
            Toast.makeText(requireContext(), title, Toast.LENGTH_SHORT).show()
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

    override fun onAnnouncementClick(position: Int) {
        val fragment = ItemsDetailFragment()
        fragmentManager?.commit {
            replace(R.id.nav_host_fragment_content_main, fragment)
            addToBackStack(null)
            setReorderingAllowed(true)
        }
    }
}