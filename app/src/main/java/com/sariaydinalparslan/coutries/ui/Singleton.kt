package com.sariaydinalparslan.coutries.ui

object mySingleton{
    //visitorın özellikleri seçtiği ve adapter de kendini game activity atması
    var chosenCountry: String? = null
    //create (hostun özellikleri)
    var hostName : String? = null
    //gameactivityde hazır countryler
    var readyVisitorCountry: String? = null
    var readyhostCountry: String? = null
    //homeFragment and settings fragment
    var avatarId: String? = null
    //singleplayer country kod
    var singlePlayerCountryCode : String? = null
    //multiplayer country kod
    var multiPlayerCountryCode : String? = null
    //singleplayer random ımage
    var randomImageSingle : Int? = 0
    //multiplayer hostname
    var multiHostPlayerName : String? = null
    //multiplayer visitroname
    var multiVisitorPlayerName : String? = null


}