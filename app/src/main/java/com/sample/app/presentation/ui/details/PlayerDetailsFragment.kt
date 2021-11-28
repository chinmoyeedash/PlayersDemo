package com.sample.app.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sample.app.R
import com.sample.app.databinding.PlayerDetailsFragmentBinding
import com.sample.app.presentation.ui.home.PlayersViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerDetailsFragment : Fragment() {

  companion object {
    const val PLAYER_INFO = "player_info"
  }

  private lateinit var binding: PlayerDetailsFragmentBinding
  private val viewModel: PlayerDetailsViewModel by viewModel()
  private val playersViewModel: PlayersViewModel by sharedViewModel()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.player_details_fragment, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    binding.viewModel = viewModel
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    arguments?.let {
      viewModel.setPlayerDetails(it.getParcelable(PLAYER_INFO))
    }

  }

}