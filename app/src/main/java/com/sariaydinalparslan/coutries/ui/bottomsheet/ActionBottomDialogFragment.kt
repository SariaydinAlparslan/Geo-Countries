package com.sariaydinalparslan.coutries.ui.bottomsheet


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.ui.mySingleton
import kotlinx.android.synthetic.main.bottom_sheet_settings.*


class ActionBottomDialogFragment(private var mListener: ItemClickListener):BottomSheetDialogFragment(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_settings,container,false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind()

    }
    override fun onStart() {
        super.onStart()
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
    override fun onClick(p0: View?) {
        val tvSelected = p0 as TextView
        mListener!!.onItemClick(tvSelected.text.toString())
        dismiss()
    }
    private fun bind(){
        //continent
        if (mySingleton.continent == "1"){
            continentText.text = getString(R.string.asia)
        }
        if (mySingleton.continent == "2"){
            continentText.text = getString(R.string.europe)
        }
        if (mySingleton.continent == "3"){
            continentText.text = getString(R.string.africa)
        }
        if (mySingleton.continent == "4"){
            continentText.text = getString(R.string.australia)
        }
        if (mySingleton.continent == "5"){
            continentText.text = getString(R.string.america)
        }

        //part
        if (mySingleton.partContinent == "1"){
            partContText.text = getString(R.string.east)
        }
        if (mySingleton.partContinent == "2"){
            partContText.text = getString(R.string.west)
        }
        if (mySingleton.partContinent == "3"){
            partContText.text = getString(R.string.north)
        }
        if (mySingleton.partContinent == "4"){
            partContText.text = getString(R.string.south)
        }
        if (mySingleton.partContinent == "5"){
            partContText.text = getString(R.string.middle)
        }
        if (mySingleton.population.toString() == "1"){
            populationText.text = getString(R.string.milionless)
        }else if (mySingleton.population.toString() == "0"){
            populationText.text = getString(R.string.milyar)
        }else{
            populationText.text = mySingleton.population.toString() + "  " + getString(R.string.milion)
        }

        longlatText.text = mySingleton.longlat
    }
}