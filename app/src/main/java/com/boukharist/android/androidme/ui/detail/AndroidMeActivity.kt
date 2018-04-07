package com.boukharist.android.androidme.ui.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.boukharist.android.androidme.data.AndroidImageAssets
import com.boukharist.android.androidme.ui.parent.MainActivity
import com.example.android.android_me.R

class AndroidMeActivity : AppCompatActivity() {


    var headIndex: Int = 0
    var bodyIndex: Int = 0
    var legIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_me)
        if (savedInstanceState == null) {
            retrieveArgs()
            setupFragments()
        }
    }

    private fun retrieveArgs() {
        headIndex = intent.extras.getInt(MainActivity.HEAD_INDEX)
        bodyIndex = intent.extras.getInt(MainActivity.BODY_INDEX)
        legIndex = intent.extras.getInt(MainActivity.LEG_INDEX)
    }

    private fun setupFragments() {
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