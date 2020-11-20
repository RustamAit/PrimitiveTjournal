package kz.example.android.primitivetjournal

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kz.example.android.primitivetjournal.ui.postFeed.view.PostFeedFragment
import kz.example.android.primitivetjournal.ui.profile.UserViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle("Видео и гифки")
        (fragmentContainer as? NavHostFragment)?.navController?.let { navController ->
            bottomNavigation.setupWithNavController(
                navController
            )
        }
    }

}