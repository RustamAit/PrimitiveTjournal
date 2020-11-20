package kz.example.android.primitivetjournal.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.fragment_profile.*
import kz.example.android.primitivetjournal.R
import kz.example.android.primitivetjournal.data.domain.User
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val userViewModel: UserViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.fetchCurrentUser()
        userViewModel.currentUser.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                showUserUi(it)
            } ?: run {
                if(userViewModel.isLoading.value == false){
                    view.findNavController().navigate(R.id.action_profileFragment_to_authFragment)
                }
            }
        })
    }

    private fun showUserUi(u: User){
        userName.visibility = View.VISIBLE
        signOutBtn.visibility = View.VISIBLE
        avatarView.visibility = View.VISIBLE
        userName.text = u.name
        Glide.with(this)
            .load(u.avatarUrl)
            .placeholder(R.color.grey)
            .transform(FitCenter(), RoundedCorners(48))
            .into(avatarView)

        signOutBtn.setOnClickListener {
            userViewModel.signOut()
        }
    }
}