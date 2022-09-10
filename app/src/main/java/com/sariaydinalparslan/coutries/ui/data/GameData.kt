package com.sariaydinalparslan.coutries.ui.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class GameData(var countryIx: Int?= 0,var userId: String?="", var userName: String?=""){

}
