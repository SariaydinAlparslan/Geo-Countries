package com.sariaydinalparslan.coutries.ui

object mySingleton{
    //visitorın özellikleri seçtiği ve adapter de kendini game activity atması
    var chosenCountry: String? = null
    //create (hostun özellikleri)
    var hostCountry: String? = null
    var hostName : String? = null
    //gameactivityde hazır countryler
    var readyVisitorCountry: String? = null
    var readyhostCountry: String? = null
    //homeFragment and settings fragment
    var avatarId: String? = null
    //createfragment da firebasedeki room-allpick veriyi silmek için
    var createRoomId: String? = null



}