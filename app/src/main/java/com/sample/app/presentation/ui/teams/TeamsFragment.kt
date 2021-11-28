package com.sample.app.presentation.ui.teams

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sample.app.R
import com.sample.app.databinding.FragmentTeamListBinding
import com.sample.app.presentation.ui.teams.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class TeamsFragment : Fragment() {

  private var columnCount = 1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    arguments?.let {
      columnCount = it.getInt(ARG_COLUMN_COUNT)
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val binding : FragmentTeamListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_team_list, container, false)
    with(binding.teamsList) {
        adapter = MyTeamsRecyclerViewAdapter(PlaceholderContent.ITEMS)
        (adapter as MyTeamsRecyclerViewAdapter).stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT
      }
    return binding.root
  }

  companion object {

    // TODO: Customize parameter argument names
    const val ARG_COLUMN_COUNT = "column-count"


  }
}