package kg.smartpost.aksy.ui.items.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kg.smartpost.aksy.ui.items.ItemsFragment

class AnnouncementPageAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 10

    override fun createFragment(position: Int): Fragment {
        return ItemsFragment()
    }
}