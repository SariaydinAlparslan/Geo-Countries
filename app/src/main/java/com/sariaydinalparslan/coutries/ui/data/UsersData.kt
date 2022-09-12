package com.sariaydinalparslan.coutries.ui.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UsersData(var profileImage: String?="",var userId: String?="", var userName: String?="") {
}