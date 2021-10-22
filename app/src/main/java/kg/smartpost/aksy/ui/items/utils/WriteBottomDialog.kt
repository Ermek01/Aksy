package kg.smartpost.aksy.ui.items.utils

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.smartpost.aksy.R
import kg.smartpost.aksy.databinding.ShareBottomDialogBinding
import kg.smartpost.aksy.databinding.WriteBottomDialogBinding

class WriteBottomDialog() : BottomSheetDialogFragment() {

    private var _binding: WriteBottomDialogBinding? = null
    private val binding: WriteBottomDialogBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WriteBottomDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    interface BottomSheetListener {
        fun onConfirmClick(code: String)
    }

}