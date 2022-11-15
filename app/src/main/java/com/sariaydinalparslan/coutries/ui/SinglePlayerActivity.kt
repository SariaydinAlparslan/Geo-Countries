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
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.database.FirebaseDatabase
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.databinding.ActivitySinglePlayerBinding
import com.sariaydinalparslan.coutries.ui.ui.isCodeMaker
import com.sariaydinalparslan.coutries.ui.utils.downloadfromUrl
import com.sariaydinalparslan.coutries.ui.utils.placeholderProgressBar
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_game.button
import kotlinx.android.synthetic.main.activity_game.button2
import kotlinx.android.synthetic.main.activity_game.button3
import kotlinx.android.synthetic.main.activity_game.button4
import kotlinx.android.synthetic.main.activity_game.button5
import kotlinx.android.synthetic.main.activity_game.button6
import kotlinx.android.synthetic.main.activity_single_player.*
import kotlinx.android.synthetic.main.include_country_tips.view.*
import kotlinx.android.synthetic.main.include_guess_view.view.*
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class SinglePlayerActivity : AppCompatActivity() {
    var emptyCells = ArrayList<Int>()
    var player1 = ArrayList<Int>()
    val max = 159
    val min = 1
    var activeUser = 1
    val total: Int = max - min
    private var mInterstitialAd: InterstitialAd? = null
    private lateinit var binding: ActivitySinglePlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySinglePlayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpSlider()

        MobileAds.initialize(this) {}

        adMobInter()

        val number = (9..10).shuffled().last()
        mySingleton.randomImageSingle = number
        val randomCountry = (1..159).shuffled().last()
        mySingleton.singlePlayerCountryCode= randomCountry.toString()

    }

    override fun onBackPressed() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle(getString(R.string.alertdialog))
        alert.setMessage(getString(R.string.alertdialog2))
        alert.setPositiveButton(getString(R.string.yes)) { dialog, which ->

            failToast()
            reset()
            Handler().postDelayed({
                goBack()
                adMob()
            },3500)
        }
        alert.setNegativeButton(getString(R.string.no)) { dialog, which ->
        }
        alert.show()
    }

    private fun setUpSlider() {
        binding.guessCountryView.fluid_slider.positionListener = { pos ->
            binding.guessCountryView.fluid_slider.bubbleText = "${min + (total * pos).toInt()}";
            binding.guessCountryView.result_guess.text = "${min + (total * pos).toInt()}"
        }
        binding.guessCountryView.fluid_slider.position = 0.3f
        binding.guessCountryView.fluid_slider.startText = "$min"
        binding.guessCountryView.fluid_slider.endText = "$max"
    }

    private fun reset() {
        player1.clear()
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
                7 -> button7
                8 -> button8
                else -> {
                    button
                }
            }
            buttonSelected.isEnabled = true
        }
    }
    private fun goBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
    fun playNow(buttonSelected: Button, currCell: Int) {
        val random =  mySingleton.randomImageSingle
        binding.countryTipsView.countrytipsSingle.visibility = View.VISIBLE
        FirebaseDatabase.getInstance().reference.child("images").child(mySingleton.singlePlayerCountryCode.toString())
            .child(random.toString()).get().addOnSuccessListener {
                binding.countryTipsView.tips1.downloadfromUrl(it.value.toString(), placeholderProgressBar(this))
            }

        Handler().postDelayed({
            binding.countryTipsView.countrytipsSingle.visibility = View.GONE
            emptyCells.remove(currCell)
            player1.add(currCell)
            emptyCells.add(currCell)
            buttonSelected.isEnabled = false
            buttonSelected.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        }, 6000)
        mySingleton.randomImageSingle = mySingleton.randomImageSingle!!-1
    }
    fun clickfun(view: View) {
            val but = view as Button
            var cellOnline = 0
            when (but.id) {
                R.id.button -> cellOnline = 1
                R.id.button2 -> cellOnline = 2
                R.id.button3 -> cellOnline = 3
                R.id.button4 -> cellOnline = 4
                R.id.button5 -> cellOnline = 5
                R.id.button6 -> cellOnline = 6
                R.id.button7 -> cellOnline = 7
                R.id.button8 -> cellOnline = 8
                else -> {
                    cellOnline = 0
                }
            }
            playNow(but, cellOnline)
    }

    fun guess(view: View) {
        binding.guessCountryView.visibility = View.VISIBLE
        binding.guess.visibility = View.GONE
        binding.guess2.visibility = View.VISIBLE
    }

    fun guess2(view: View) {
        binding.guessCountryView.visibility = View.VISIBLE
        binding.btnResultGuess2.visibility = View.VISIBLE
    }

    fun list_guess(view: View) {
        if (binding.resultGuess.text == mySingleton.singlePlayerCountryCode.toString()) {
            binding.guessCountryView.visibility = View.GONE
            //1. kazanma yolu
            winToast()
            binding.winView.winner.visibility = View.VISIBLE
            reset()
            Handler().postDelayed({
                goBack()
                adMob()
            }, 3500)
        } else {
            //1.yanlış
            binding.guessCountryView.visibility = View.GONE
            binding.btnResultGuess1.visibility = View.GONE
            Toast.makeText(this, getString(R.string.lastchance), Toast.LENGTH_SHORT).show()
        }
    }

    fun list_guess2(view: View) {
        if (binding.resultGuess.text == mySingleton.singlePlayerCountryCode.toString()) {
            binding.guessCountryView.visibility = View.GONE
            winToast()
            binding.winView.winner.visibility = View.VISIBLE
            reset()
            Handler().postDelayed({
                goBack()
                adMob()
            }, 3500)
        } else {
            failToast()
            binding.loseView.loser.visibility = View.VISIBLE
            reset()
            Handler().postDelayed({
                goBack()
                adMob()
            }, 3500)
        }
    }
    private fun winToast(){
        MotionToast.darkToast(
            this, getString(R.string.win),getString(R.string.correct),
            MotionToastStyle.SUCCESS,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
    }
    private fun failToast(){
        MotionToast.darkToast(
            this, getString(R.string.lose),getString(R.string.wrong) ,
            MotionToastStyle.WARNING,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
    }
    private fun adMobInter() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.toString())
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