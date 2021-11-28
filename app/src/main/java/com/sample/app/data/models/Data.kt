package com.sample.app.data.models

import com.sample.app.domain.models.IPlayer
import com.sample.app.domain.models.ITeam

data class PlayerInfo(
    override val firstName: String?,
    override val heightFeet: String?,
    override val heightInches: String?,
    override val id: Int,
    override val lastName: String?,
    override val position: String?,
    override val team: ITeam?,
    override val weightPounds: String?,
    override val imageUrl: String?
): IPlayer

data class Team(
    override val abbreviation: String?,
    override val city: String?,
    override val division: String?,
    override val fullName: String?,
    override val id: Int?
    ): ITeam

