package com.sample.app.data.models

import com.google.gson.annotations.SerializedName


data class PlayerResponse(
    @SerializedName("data")
    val data: List<Data>?,
    @SerializedName("meta")
    val meta: Meta?
)

data class Data(
    @SerializedName("first_name")
    val first_name: String?,
    @SerializedName("height_feet")
    val height_feet: String?,
    @SerializedName("height_inches")
    val height_inches: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_name")
    val last_name: String?,
    @SerializedName("position")
    val position: String?,
    @SerializedName("team")
    val team: TeamInfo?,
    @SerializedName("weight_pounds")
    val weight_pounds: String?
)

data class TeamInfo(
    @SerializedName("abbreviation")
    val abbreviation: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("conference")
    val conference: String?,
    @SerializedName("division")
    val division: String?,
    @SerializedName("full_name")
    val full_name: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?
)

data class Meta(
    @SerializedName("current_page")
    val current_page: Int?,
    @SerializedName("next_page")
    val next_page: Int?,
    @SerializedName("per_page")
    val per_page: Int?,
    @SerializedName("total_count")
    val total_count: Int?,
    @SerializedName("total_pages")
    val total_pages: Int?
)