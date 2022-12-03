package com.sariaydinalparslan.coutries.ui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.*
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.databinding.ActivityGameBinding
import com.sariaydinalparslan.coutries.ui.ui.code
import com.sariaydinalparslan.coutries.ui.ui.isCodeMaker
import com.sariaydinalparslan.coutries.ui.ui.keyValue
import com.sariaydinalparslan.coutries.ui.utils.downloadfromUrl
import com.sariaydinalparslan.coutries.ui.utils.placeholderProgressBar
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.include_guess_view.*
import kotlinx.android.synthetic.main.include_waiting_player.*
import kotlinx.android.synthetic.main.reycler_row.*
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import kotlin.system.exitProcess

var isMyMove = isCodeMaker
var playerTurn = true

class GameActivity : AppCompatActivity() {
    var player2 = ArrayList<Int>()
    var player1 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()
    var activeUser = 1
    val max = 159
    val min = 1
    val total: Int = max - min
    private lateinit var timer: CountDownTimer
    private lateinit var binding: ActivityGameBinding
    private var mInterstitialAd: InterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        MobileAds.initialize(this) {}

        adMobInter()

        val number = (9..10).shuffled().last()
        mySingleton.randomImageSingle = number

        timer = object : CountDownTimer(140000, 1000) {
            override fun onTick(p0: Long) {
                binding.txtTimer.text = " ${p0 / 1000}"
            }
            override fun onFinish() {
                Toast.makeText(this@GameActivity, getString(R.string.draw), Toast.LENGTH_SHORT).show()
                val intent = Intent(this@GameActivity, MainActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }
        //bunu onchild change e koy
        // timer.start()
        setUpSlider()
        visitorName()
        hostName()
        hostCountry()
        visitorCountry()
        data()
        someoneQuitReady()
        someoneWinReady()
        wrongGuessReady()
    }
    override fun onBackPressed() {
        //2.kaybetme yolu
        val alert = AlertDialog.Builder(this)
        alert.setTitle(getString(R.string.alertdialog))
        alert.setMessage(getString(R.string.alertdialog2))
        alert.setPositiveButton(getString(R.string.yes)) { dialog, which ->
            someoneQuit()
            failToast()
            reset()
            deleteGamersCountries()
            Handler().postDelayed({
                goBack()
                adMob()
            },3500)
        }
        alert.setNegativeButton(getString(R.string.no)) { dialog, which ->
              }
        alert.show()
    }

    private fun data() {
        FirebaseDatabase.getInstance().reference.child("data").child(code)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    var data = snapshot.value
                    if (isMyMove == true) {
                        isMyMove = false
                        moveOnline(data.toString(), isMyMove)
                    } else {
                        isMyMove = true
                        moveOnline(data.toString(), isMyMove)
                    }
                }
                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    reset()

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
    private fun hostName() {
        FirebaseDatabase.getInstance().reference.child("hostname").child(code)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    mySingleton.multiHostPlayerName = snapshot.value.toString()
                    binding.countryTips.hostname.text = mySingleton.multiHostPlayerName
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
    private fun hostCountry() {
        FirebaseDatabase.getInstance().reference.child("hostcountry").child(code)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    mySingleton.readyhostCountry = snapshot.value.toString()
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun visitorName() {
        FirebaseDatabase.getInstance().reference.child("visitorname").child(code)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    mySingleton.multiVisitorPlayerName = snapshot.value.toString()
                    binding.countryTips.visitornameText.text = mySingleton.multiVisitorPlayerName
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
    private fun visitorCountry() {
        FirebaseDatabase.getInstance().reference.child("visitorscountry").child(code)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    mySingleton.readyVisitorCountry = snapshot.value.toString()
                    binding.includeWaiting.giris.visibility = View.GONE
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
    private fun setUpSlider() {
        fluid_slider.positionListener = { pos ->
            fluid_slider.bubbleText = "${min + (total * pos).toInt()}";
            result_guess.text = "${min + (total * pos).toInt()}"
        }
        fluid_slider.position = 0.3f
        fluid_slider.startText = "$min"
        fluid_slider.endText = "$max"
    }

    private fun reset() {
        player1.clear()
        player2.clear()
        emptyCells.clear()
        activeUser = 1
        for (i in 1..9) {
            var buttonSelected: Button?
            buttonSelected = when (i) {
                1 -> button
                2 -> button2
                3 -> button3
                4 -> button4
                5 -> button5
                6 -> button6
                else -> {
                    button
                }
            }
            buttonSelected.isEnabled = true
            buttonSelected.text = ""
            isMyMove = isCodeMaker
            if (isCodeMaker) {
                FirebaseDatabase.getInstance().reference.child("data")
                    .child(code).removeValue()
            }
        }
    }
    fun deleteGamersCountries() {
        FirebaseDatabase.getInstance().reference.child("visitorscountry").child(code)
            .removeValue()
        FirebaseDatabase.getInstance().reference.child("hostcountry").child(code)
            .removeValue()
    }
    private fun goBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    private fun removeCode() {
        if (isCodeMaker) {
            FirebaseDatabase.getInstance().reference.child("Room").child("AllPick")
                .child(keyValue).removeValue()
        }
    }
    fun updateDatabase(cellId: Int) {
        FirebaseDatabase.getInstance().reference.child("data").child(code)
            .push().setValue(cellId)
    }

    // buttona basan kiside görünen
    fun playNow(buttonSelected: Button, currCell: Int) {
        binding.countryTips.countrytips.visibility = View.VISIBLE
            FirebaseDatabase.getInstance().reference.child("images").child(mySingleton.readyVisitorCountry.toString())
                .child(mySingleton.randomImageSingle.toString()).get().addOnSuccessListener {
                    binding.countryTips.tips1.downloadfromUrl(it.value.toString(), placeholderProgressBar(this))
                }
            buttonSelected.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            Handler().postDelayed({
                binding.countryTips.countrytips.visibility = View.GONE
                emptyCells.remove(currCell)
                player1.add(currCell)
                emptyCells.add(currCell)
                buttonSelected.isEnabled = false
            }, 6000)

        FirebaseDatabase.getInstance().reference.child("images").child(mySingleton.readyhostCountry.toString())
            .child(mySingleton.randomImageSingle.toString()).get().addOnSuccessListener {
                binding.countryTips.tips2.downloadfromUrl(it.value.toString(), placeholderProgressBar(this))
            }
        buttonSelected.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        Handler().postDelayed({
            binding.countryTips.countrytips.visibility = View.GONE
            emptyCells.remove(currCell)
            player1.add(currCell)
            emptyCells.add(currCell)
            buttonSelected.isEnabled = false
        }, 6000)
        mySingleton.randomImageSingle = mySingleton.randomImageSingle!!-1
    }
    // buttona basan kisi değilde visitorda görünen
    fun moveOnline(data: String, move: Boolean) {
        if (move) {
            var buttonSelected: Button?
            buttonSelected = when (data.toInt()) {
                1 -> button
                2 -> button2
                3 -> button3
                4 -> button4
                5 -> button5
                6 -> button6
                else -> {
                    button
                }
            }
            binding.countryTips.countrytips.visibility = View.VISIBLE
            FirebaseDatabase.getInstance().reference.child("images").child(mySingleton.readyVisitorCountry.toString())
                .child(mySingleton.randomImageSingle.toString()).get().addOnSuccessListener {
                    binding.countryTips.tips1.downloadfromUrl(it.value.toString(), placeholderProgressBar(this))
                }
            buttonSelected.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            Handler().postDelayed({
                binding.countryTips.countrytips.visibility = View.GONE
                player2.add(data.toInt())
                emptyCells.add(data.toInt())
                buttonSelected.isEnabled = false
            }, 6000)
            //
            FirebaseDatabase.getInstance().reference.child("images").child(mySingleton.readyhostCountry.toString())
                .child(mySingleton.randomImageSingle.toString()).get().addOnSuccessListener {
                    binding.countryTips.tips2.downloadfromUrl(it.value.toString(), placeholderProgressBar(this))
                }
            buttonSelected.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            Handler().postDelayed({
                binding.countryTips.countrytips.visibility = View.GONE
                player2.add(data.toInt())
                emptyCells.add(data.toInt())
                buttonSelected.isEnabled = false
            }, 6000)

        }
    }
    //basma halinde data gitmesi, hangi butonlar olacağı, sıra geçimi,basma özellikleri
    fun clickfun(view: View) {
        if (isMyMove) {
            val but = view as Button
            var cellOnline = 0
            when (but.id) {
                R.id.button -> cellOnline = 1
                R.id.button2 -> cellOnline = 2
                R.id.button3 -> cellOnline = 3
                R.id.button4 -> cellOnline = 4
                R.id.button5 -> cellOnline = 5
                R.id.button6 -> cellOnline = 6
                else -> {
                    cellOnline = 0
                }
            }
            playerTurn = false
            Handler().postDelayed(Runnable {
                playerTurn = true
            }, 600)
            playNow(but, cellOnline)
            updateDatabase(cellOnline)
        }
    }

    fun guess(view: View) {
        binding.guessCountryView.guessCountryView.visibility = View.VISIBLE
        binding.guess.visibility = View.GONE
        binding.guess2.visibility = View.VISIBLE
    }

    fun guess2(view: View) {
        binding.guessCountryView.guessCountryView.visibility = View.VISIBLE
        binding.guessCountryView.btnResultGuess2.visibility = View.VISIBLE
    }

    fun list_guess(view: View) {
        if (binding.guessCountryView.resultGuess.text == mySingleton.chosenCountry) {
            binding.guessCountryView.guessCountryView.visibility = View.GONE
            //1. kazanma yolu
            binding.winViewOnline.winner.visibility = View.VISIBLE
            winToast()
            Handler().postDelayed({
                reset()
                goBack()
                adMob()
                deleteGamersCountries()
            }, 3500)
            someoneWin()
        } else {
            binding.guessCountryView.guessCountryView.visibility = View.GONE
            binding.guessCountryView.btnResultGuess1.visibility = View.GONE
            lastchance()
        }
    }
    fun list_guess2(view: View) {
        if (binding.guessCountryView.resultGuess.text == mySingleton.chosenCountry) {
            binding.guessCountryView.guessCountryView.visibility = View.GONE
            //2. kazanma yolu
            binding.winViewOnline.winner.visibility = View.VISIBLE
            winToast()
            Handler().postDelayed({
                reset()
                goBack()
                adMob()
               deleteGamersCountries()
            }, 3500)
            someoneWin()
        } else {
            binding.guessCountryView.guessCountryView.visibility = View.GONE
            failToast()
            binding.loseViewOnline.loser.visibility = View.VISIBLE
            reset()
            deleteGamersCountries()
            Handler().postDelayed({
                goBack()
                adMob()
            }, 4500)
            wrongGuess()
        }
    }

    private fun someoneQuit(){
        FirebaseDatabase.getInstance().reference.child("someonequit").child(code)
            .push().setValue("someonequit")
    }
    private fun someoneQuitReady(){
        FirebaseDatabase.getInstance().reference.child("someonequit").child(code)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    quitToast()
                    reset()
                    goBack()
                }
                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }
                override fun onChildRemoved(snapshot: DataSnapshot) {
                }
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
    private fun someoneWin(){
        FirebaseDatabase.getInstance().reference.child("someonewin").child(code)
            .push().setValue("someonewin")
    }
    private fun someoneWinReady(){
        FirebaseDatabase.getInstance().reference.child("someonewin").child(code)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    loseToast()
                    reset()
                    goBack()
                }
                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }
                override fun onChildRemoved(snapshot: DataSnapshot) {
                }
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
    private fun wrongGuess(){
        FirebaseDatabase.getInstance().reference.child("wrongguess").child(code)
            .push().setValue("wrongguess")
    }
    private fun wrongGuessReady(){
        FirebaseDatabase.getInstance().reference.child("wrongguess").child(code)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    failguessToast()
                    reset()
                    goBack()
                }
                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }
                override fun onChildRemoved(snapshot: DataSnapshot) {
                }
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun winToast(){
        MotionToast.darkToast(
            this, getString(R.string.win),getString(R.string.correct) ,
            MotionToastStyle.SUCCESS,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
    }
    private fun failToast(){
        MotionToast.darkToast(
            this, getString(R.string.fail),getString(R.string.wrong) ,
            MotionToastStyle.ERROR,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
    }
    private fun loseToast(){
        MotionToast.darkToast(
            this, getString(R.string.lose),getString(R.string.opponent1),
            MotionToastStyle.ERROR,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
    }
    private fun quitToast(){
        MotionToast.darkToast(
            this, getString(R.string.win),getString(R.string.opponent2) ,
            MotionToastStyle.INFO,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
    }
    private fun failguessToast(){
        MotionToast.darkToast(
            this,  getString(R.string.win), getString(R.string.opponent3),
            MotionToastStyle.INFO,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
    }
    private fun lastchance(){
        MotionToast.darkToast(
            this, getString(R.string.wrong),getString(R.string.lastchance) ,
            MotionToastStyle.INFO,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
    }
    private fun adMobInter() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-2330201235781557/4068123048", adRequest, object : InterstitialAdLoadCallback() {

            override fun onAdFailedToLoad(adError: LoadAdError) {
               // Log.d(TAG, adError?.toString())
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                // Called when ad fails to show.
                Log.e(TAG, "Ad failed to show fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }
    }
    private fun adMob(){
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
        } else {
            Log.e("alp", "The interstitial ad wasn't ready yet.")
        }
    }
}
