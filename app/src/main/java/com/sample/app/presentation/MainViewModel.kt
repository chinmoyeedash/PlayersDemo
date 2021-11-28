package com.sample.app.presentation

import androidx.lifecycle.ViewModel
import com.sample.app.presentation.router.NavigationRouter

class MainViewModel(private val navigationRouter: NavigationRouter) : ViewModel()

/*fun onNavigationItemSelected(item: MenuItem): Boolean {
  when (item.itemId) {
    R.id.homeFragment -> {
      navigateTo(FragmentTargets.HomeFragment)
      return true
    }
    R.id.teamsFragment -> {
      navigateTo(FragmentTargets.TeamsFragment)
      return true
    }
    R.id.statsFragment -> {
      navigateTo(FragmentTargets.PlayerStatsFragment)
      return true
    }
  }
  return false
}

sealed class FragmentTargets {
  object HomeFragment : FragmentTargets()
  object TeamsFragment : FragmentTargets()
  object PlayerStatsFragment : FragmentTargets()
}

/*private fun navigateTo(
  destination: FragmentTargets,
  bundle: Bundle? = null,
  forceNav: Boolean = false
) {
  when (destination) {
    FragmentTargets.HomeFragment -> navigationRouter.goto(
    Destination.HomeFragment,
      bundle,
      forceNav
    )
    FragmentTargets.TeamsFragment -> navigationRouter.goto(
      Destination.TeamsFragment,
      bundle,
      forceNav
    )
    FragmentTargets.PlayerStatsFragment -> navigationRouter.goto(
      Destination.StatsFragment,
      bundle,
      forceNav
    )
  }
}*/