package com.sample.app.presentation.ui.home

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.flatMap
import androidx.paging.map
import com.sample.app.domain.exception.ApiError
import com.sample.app.domain.models.IPlayer
import com.sample.app.domain.usecase.base.GetPlayersFlowUseCase
import com.sample.app.domain.usecase.base.GetPlayersUseCase
import com.sample.app.domain.usecase.base.UseCaseResponse
import com.sample.app.presentation.model.PlayerInfo
import com.sample.app.presentation.router.NavigationRouter
import com.sample.app.presentation.utils.SingleLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PlayersViewModel(
  private val navigationRouter: NavigationRouter,
  private val getPlayersUseCase: GetPlayersUseCase,
  private val getPlayersFlowUseCase: GetPlayersFlowUseCase
) : ViewModel() {

  private val _playersFlowData = MutableLiveData<PagingData<PlayerInfo>>()
  val playersFlowData = _playersFlowData as LiveData<PagingData<PlayerInfo>>
  private val _playersData = MutableLiveData<List<PlayerInfo>>()
  val playersData = _playersData as LiveData<List<PlayerInfo>>
  private val _showProgressbar = SingleLiveData<Boolean>()
  val showProgressbar = _showProgressbar as LiveData<Boolean>
  private val _showError = SingleLiveData<String>()
  val showError = _showError as LiveData<String>
  private val _crossClicked = SingleLiveData<Boolean>()
  val crossClicked = _crossClicked as LiveData<Boolean>
  val playerInfo = ObservableField<PlayerInfo>()

  /**
   * Get Players data from flow usecase as paged content and updates livedata for the
   * players list on home
   */
  @ExperimentalCoroutinesApi
  fun getPlayers() {
    _showProgressbar.value = true
    getPlayersFlowUseCase.invoke(viewModelScope, Unit, onResult = { playersData ->
      val flowList = playersData.map { pagingData ->
        pagingData.map {
          updatePlayers(it)
        }
      }.cachedIn(viewModelScope)
      viewModelScope.launch {
        flowList.collectLatest {
          _playersFlowData.postValue(it)
          _showProgressbar.postValue(false)
        }
      }

    })

   /* getPlayersUseCase.invoke(viewModelScope, null, object : UseCaseResponse<List<IPlayer>> {
      override fun onSuccess(result: List<IPlayer>) {
        Log.i(TAG, "result: $result")
        val list = mutableListOf<PlayerInfo>()
        result?.forEach { item ->
          list.add(updatePlayers(item))
        }
        _playersData.postValue(list)
        _showProgressbar.value = false
      }

      override fun onError(apiError: ApiError?) {
        _showError.value = apiError?.getErrorMessage()
        _showProgressbar.value = false
      }
    })*/
    /*Log.i(TAG, "result: $result")
    val list = mutableListOf<PlayerInfo>()
    result?.forEach { item ->
      list.add(updatePlayers(item))
    }
    _playersData.postValue(list)
    _showProgressbar.value = false*/
  }


  private fun updatePlayers(item: IPlayer): PlayerInfo {
    val player = PlayerInfo(
      id = item.id,
      firstName = item.firstName,
      lastName = item.lastName,
      position = item.position,
      heightFeet = item.heightFeet,
      weightPounds = item.weightPounds,
      teamName = item.team?.fullName,
      imageUrl = item.imageUrl,
      showName = false
    )
    return player
  }

  private fun updateShowName(show: Boolean) {
    val list = _playersFlowData.value
    list?.let { buttonList ->
      _playersFlowData.value = buttonList.map { it.copy(showName = show) }
    }
  }

  fun crossButtonClicked() {
    _crossClicked.value = true
    updateShowName(false)
  }

  fun showPlayerDetails(item: PlayerInfo) {
    updateShowName(true)
    playerInfo.set(item)
  }

  companion object {
    private val TAG = PlayersViewModel::class.java.simpleName
  }

}