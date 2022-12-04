package com.sariaydinalparslan.coutries.ui.bottomsheet


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

        continentText.text = mySingleton.continent
        populationText.text = mySingleton.population.toString()
        partContText.text = mySingleton.partContinent
        longlatText.text = mySingleton.longlat
    }

    override fun onStart() {
        super.onStart()
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.PEEK_HEIGHT_AUTO
    }
    override fun onClick(p0: View?) {
        val tvSelected = p0 as TextView
        mListener!!.onItemClick(tvSelected.text.toString())
        dismiss()
    }
}