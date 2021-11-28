package com.sample.app.presentation


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sample.app.R
import com.sample.app.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sample.app.presentation.ui.home.HomeFragment
import com.sample.app.presentation.ui.stats.PlayerStatsFragment
import com.sample.app.presentation.ui.teams.TeamsFragment


class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  /*private val navHostFragment: NavHostFragment by lazy {
    (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
  }
  val navController: NavController by lazy {
    navHostFragment.navController
  }
  val navGraph: NavGraph by lazy {
    navController.navInflater.inflate(R.navigation.presentation_nav)
  }
*/
  private val viewModel: MainViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this
    val adapter = PlayersPagerAdapter(this)
    binding.viewPager.adapter = adapter
    binding.viewPager.offscreenPageLimit = adapter.itemCount - 1
    binding.bottomNavView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

  }

  private val mOnNavigationItemSelectedListener =
    BottomNavigationView.OnNavigationItemSelectedListener { item ->
      when (item.itemId) {
        R.id.homeFragment -> {
          binding.viewPager.currentItem = 0
          true
        }
        R.id.teamsFragment -> {
          binding.viewPager.currentItem = 1
          true
        }
        R.id.statsFragment -> {
          binding.viewPager.currentItem = 2
          true
        }
        else -> false
      }
    }

  private class PlayersPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
      return when (position) {
        0 -> HomeFragment()
        1 -> TeamsFragment()
        2 -> PlayerStatsFragment()
        else -> HomeFragment()
      }
    }
  }
}