package kg.smartpost.aksy.ui.items.utils

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.smartpost.aksy.R
import kg.smartpost.aksy.databinding.WriteBottomDialogBinding

class WriteBottomDialog(private val phones: ArrayList<String>) : BottomSheetDialogFragment() {

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

        binding.btnShareWa.setOnClickListener {
            val url = "https://api.whatsapp.com/send?phone=${phones[0].trim()}"
            try {
                val pm = context?.packageManager
                pm?.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            } catch (e: PackageManager.NameNotFoundException) {
                Toast.makeText(context, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

        binding.btnShareFc.setOnClickListener {
            val uri = Uri.parse("smsto:+${phones[0].trim()}")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            when {

                Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
                    val defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(context)
                    if (defaultSmsPackageName != null) intent.setPackage(defaultSmsPackageName)
                    startActivity(intent)
                }
                else -> startActivity(intent)

            }
        }

    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    interface BottomSheetListener {
        fun onConfirmClick(code: String)
    }

}