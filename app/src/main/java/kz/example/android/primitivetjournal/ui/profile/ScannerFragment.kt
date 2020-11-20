package kz.example.android.primitivetjournal.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.findNavController
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import kotlinx.android.synthetic.main.fragment_scanner.*
import kz.example.android.primitivetjournal.R
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ScannerFragment : Fragment(R.layout.fragment_scanner) {

    private var codeScanner: CodeScanner? = null
    private val userViewModel: UserViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        codeScanner = CodeScanner(requireContext(), scannerView)

        codeScanner?.camera = CodeScanner.CAMERA_BACK
        codeScanner?.formats = CodeScanner.ALL_FORMATS
        codeScanner?.autoFocusMode = AutoFocusMode.SAFE
        codeScanner?.isAutoFocusEnabled = true
        codeScanner?.isFlashEnabled = true

        codeScanner?.decodeCallback = DecodeCallback {result ->
            activity?.runOnUiThread {
                userViewModel.setCurrentUserAccessToken(result.toString())
                userViewModel.fetchCurrentUser()
                view.findNavController().navigate(R.id.action_scannerFragment_to_profileFragment)
            }
        }

        scannerView?.setOnClickListener {
            codeScanner?.startPreview()
        }

    }

    override fun onResume() {
        super.onResume()
        codeScanner?.startPreview()
    }

    override fun onPause() {
        super.onPause()
        codeScanner?.releaseResources()
    }

}