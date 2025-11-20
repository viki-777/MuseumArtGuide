// FILE: MuseumArtGuide/app/src/main/java/com/example/museumartguide/MainActivity.kt

package com.example.museumartguide

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.Region

class MainActivity : AppCompatActivity() {

    private lateinit var beaconManager: BeaconManager
    private lateinit var artDescriptionTextView: TextView
    private val region = Region("myRangingUniqueId", null, null, null)

    private var disappearanceCounter = 0
    // --- THIS IS THE ONLY CHANGE ---
    // Increase the grace period to 10 scans (~11 seconds) to handle the unstable simulator
    private val GRACE_PERIOD_COUNT = 10

    companion object {
        private const val TAG = "BeaconDebug"
        private const val TARGET_BEACON_UUID = "2f234454-cf6d-4a0f-adf2-f4911ba9ffa6"
        private const val ART_DESCRIPTION = "Shrimant Maharaja Madhav Rao Scindia was a compassionate leader, with a progressive and egalitarian mindset that defined the Hindavi Swaraj his ancestors fought for. He was named after the greatest Maratha warrior of the Scindia family known as ``The Great Maratha`` Madhavji Scindia. In the gallery dedicated to Shrimant Madhav Rao we have a great collection, or art, photographs etc related to him."
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        artDescriptionTextView = findViewById(R.id.artDescriptionTextView)
        checkPermissions()

        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))

        beaconManager.foregroundScanPeriod = 1100L
        beaconManager.foregroundBetweenScanPeriod = 0L

        beaconManager.getRegionViewModel(region).rangedBeacons.observe(this, rangingObserver)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called, starting ranging.")
        beaconManager.startRangingBeacons(region)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called, stopping ranging.")
        beaconManager.stopRangingBeacons(region)
    }

    private val rangingObserver = Observer<Collection<Beacon>> { beacons ->
        // ... (The rest of the code remains exactly the same)
        Log.d(TAG, "Ranging update. Beacons found: ${beacons.size}")

        val beaconFound = beacons.any { it.id1.toString() == TARGET_BEACON_UUID }

        if (beaconFound) {
            disappearanceCounter = 0
            if (artDescriptionTextView.text != ART_DESCRIPTION) {
                Log.d(TAG, "Beacon became visible!")
                artDescriptionTextView.text = ART_DESCRIPTION
            }
        } else {
            disappearanceCounter++
            Log.d(TAG, "Beacon not seen. Counter: $disappearanceCounter")
            if (disappearanceCounter >= GRACE_PERIOD_COUNT && artDescriptionTextView.text != "Searching for nearby art...") {
                Log.d(TAG, "Grace period exceeded. Beacon was lost!")
                artDescriptionTextView.text = "Searching for nearby art..."
            }
        }
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH_SCAN), 1)
            }
        }
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }
}