package com.sample.app.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.sample.app.R
import com.sample.app.databinding.FragmentHomeListBinding
import com.sample.app.presentation.extension.hide
import com.sample.app.presentation.extension.observe
import com.sample.app.presentation.extension.show
import com.sample.app.presentation.model.PlayerInfo
import com.sample.app.presentation.ui.home.placeholder.PlayersAdapter
import com.sample.app.presentation.ui.home.placeholder.PlayersPagedAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A fragment representing a list of Items.
 */
class HomeFragment : Fragment(), PlayersAdapter.PlayerInfoListener,
  PlayersPagedAdapter.PlayerInfoListener {

  private var adapter: PlayersAdapter? = null
  private var pagedAdapter: PlayersPagedAdapter? = null
  private lateinit var binding: FragmentHomeListBinding
  private val viewModel: PlayersViewModel by sharedViewModel()
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_list, container, false)
    binding.viewModel = viewModel
    binding.lifecycleOwner = viewLifecycleOwner
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initAdapter()
    viewModel.getPlayers()

    /*observe(viewModel.playersData) { list ->
      list?.let {
        adapter?.updateItems(list)
        adapter?.notifyDataSetChanged()
      }
    }*/
    observe(viewModel.playersFlowData) { list ->
      list?.let {
        viewLifecycleOwner.lifecycleScope.launch {
          pagedAdapter?.submitData(list)
        }
      }
    }

    observe(viewModel.showError) { error ->
      error?.let {
        Toast.makeText(
          requireContext(),
          it,
          Toast.LENGTH_SHORT
        ).show()
      }
    }
    observe(viewModel.crossClicked) {
      binding.detailsScreen.hide()
    }
    observe(viewModel.showProgressbar) { show ->
      binding.progressBar.visibility = if (show == true) VISIBLE else GONE
    }
  }

  private fun initAdapter() {
    //adapter = PlayersAdapter()
    // adapter?.setListener(this)
    pagedAdapter = PlayersPagedAdapter()
    pagedAdapter?.setListener(this)
    binding.list.adapter = pagedAdapter
    //saves scroll position on configuration change
    binding.list.adapter?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

  }

  override fun onDestroyView() {
    super.onDestroyView()
    adapter = null
  }


  companion object {

    // TODO: Customize parameter argument names
    const val TAG = "HomeFragment"


  }

  override fun onPlayerInfoClick(item: PlayerInfo) {
    viewModel.showPlayerDetails(item)
    binding.detailsScreen.show()
  }

}