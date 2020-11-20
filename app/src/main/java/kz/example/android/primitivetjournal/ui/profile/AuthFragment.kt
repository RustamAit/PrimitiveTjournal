package kz.example.android.primitivetjournal.ui.profile

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_auth.*
import kz.example.android.primitivetjournal.R
import java.util.jar.Manifest


class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val permissionToCameraAccepted: MutableLiveData<Boolean> = MutableLiveData()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermission()

        permissionToCameraAccepted.observe(viewLifecycleOwner, Observer {
            if(it){
                signInBtn?.setOnClickListener {
                    view.findNavController().navigate(R.id.action_authFragment_to_scannerFragment)
                }
            } else {
                signInBtn?.setOnClickListener {
                    requestCameraPermission()
                }
            }
        })

    }

    private fun checkPermission(){
        permissionToCameraAccepted.value = ContextCompat.checkSelfPermission(requireContext(),
            android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission(){
        requestPermissions(arrayOf(android.Manifest.permission.CAMERA), 4004)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            permissionToCameraAccepted.value = true
        }
    }

}