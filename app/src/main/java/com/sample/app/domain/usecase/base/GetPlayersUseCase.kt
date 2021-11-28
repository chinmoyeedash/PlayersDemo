package com.sample.app.domain.usecase.base

import com.sample.app.domain.models.IPlayer
import com.sample.app.domain.repository.IPlayersRepository


class GetPlayersUseCase constructor(
    private val playerRepository: IPlayersRepository
) : UseCase<List<IPlayer>, Any?>() {
    override suspend fun run(params: Any?): List<IPlayer> {
        return playerRepository.getPlayersInfo()
    }

}