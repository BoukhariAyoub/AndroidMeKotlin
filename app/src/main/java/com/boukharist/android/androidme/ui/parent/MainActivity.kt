package com.boukharist.android.androidme.ui.parent

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.RenderProcessGoneDetail
import com.boukharist.android.androidme.data.AndroidImageAssets
import com.boukharist.android.androidme.ui.detail.AndroidMeActivity
import com.boukharist.android.androidme.ui.detail.BodyPartFragment
import com.boukharist.android.androidme.ui.master.MasterListFragment.OnImageClickListener
import com.boukharist.android.androidme.ui.parent.MainActivity.Companion.BODY_INDEX
import com.boukharist.android.androidme.ui.parent.MainActivity.Companion.HEAD_INDEX
import com.boukharist.android.androidme.ui.parent.MainActivity.Companion.LEG_INDEX
import com.example.android.android_me.R
import com.example.android.android_me.R.id.gridView
import com.example.android.android_me.R.id.nextButton
import kotlinx.android.synthetic.main.fragment_master_list.*


class MainActivity : AppCompatActivity(), OnImageClickListener {

    companion object {
        const val HEAD_INDEX = "MainActivity.HEAD_INDEX"
        const val BODY_INDEX = "MainActivity.BODY_INDEX"
        const val LEG_INDEX = "MainActivity.LEG_INDEX"
    }

    var headIndex: Int = 0
    var bodyIndex: Int = 0
    var legIndex: Int = 0

    var isTwoPane: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isTwoPane = findViewById<View>(R.id.android_me_linear_layout) != null
        if (isTwoPane) {
            setupTwoPaneViews()
        }
    }

    override fun onImageSelected(position: Int) {
        // Based on where a user has clicked, store the selected list index for the head, body, and leg BodyPartFragments
        // bodyPartNumber will be = 0 for the head fragment, 1 for the body, and 2 for the leg fragment
        // Dividing by 12 gives us these integer values because each list of images resources has a size of 12
        val bodyPartNumber = position / 12

        // Store the correct list index no matter where in the image list has been clicked
        // This ensures that the index will always be a value between 0-11
        val listIndex = position - 12 * bodyPartNumber
        if (isTwoPane) {
            handleTwoPane(bodyPartNumber, listIndex)
        } else {
            handleSinglePane(bodyPartNumber, listIndex)
        }

    }

    private fun handleTwoPane(bodyPartNumber: Int, listIndex: Int) {
        when (bodyPartNumber) {
            0 -> BodyPartFragment().apply {
                imageIds = AndroidImageAssets.getHeads()
                index = listIndex
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.head_container, this)
                        .commit()
            }
            1 -> BodyPartFragment().apply {
                imageIds = AndroidImageAssets.getBodies()
                index = listIndex
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.body_container, this)
                        .commit()
            }
            2 -> BodyPartFragment().apply {
                imageIds = AndroidImageAssets.getLegs()
                index = listIndex
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.leg_container, this)
                        .commit()
            }
            else -> BodyPartFragment()
        }
    }

    private fun handleSinglePane(bodyPartNumber: Int, listIndex: Int) {
        // Set the currently displayed item for the correct body part fragment
        when (bodyPartNumber) {
            0 -> headIndex = listIndex
            1 -> bodyIndex = listIndex
            2 -> legIndex = listIndex
        }

        // Put this information in a Bundle and attach it to an Intent that will launch an AndroidMeActivity
        val b = Bundle()
        b.putInt(HEAD_INDEX, headIndex)
        b.putInt(BODY_INDEX, bodyIndex)
        b.putInt(LEG_INDEX, legIndex)

        // Attach the Bundle to an intent
        val intent = Intent(this, AndroidMeActivity::class.java)
        intent.putExtras(b)

        // The "Next" button launches a new AndroidMeActivity
        nextButton.setOnClickListener { startActivity(intent) }
    }

    private fun setupTwoPaneViews() {
        nextButton.visibility = View.GONE
        gridView.numColumns = 2

        val headFragment = BodyPartFragment().apply {
            imageIds = AndroidImageAssets.getHeads()
            index = headIndex
        }
        val bodyFragment = BodyPartFragment().apply {
            imageIds = AndroidImageAssets.getBodies()
            index = bodyIndex
        }
        val legFragment = BodyPartFragment().apply {
            imageIds = AndroidImageAssets.getLegs()
            index = legIndex
        }
        val fragmentManager = supportFragmentManager

        fragmentManager.beginTransaction()
                .add(R.id.head_container, headFragment)
                .add(R.id.body_container, bodyFragment)
                .add(R.id.leg_container, legFragment)
                .commit()
    }
}