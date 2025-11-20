package com.example.beaconsimulator // Make sure this matches your project's package name

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.BeaconTransmitter
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var uuidEditText: EditText
    private lateinit var majorEditText: EditText
    private lateinit var minorEditText: EditText
    private lateinit var broadcastButton: Button
    private lateinit var statusTextView: TextView

    private var beaconTransmitter: BeaconTransmitter? = null
    private var isTransmitting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        uuidEditText = findViewById(R.id.uuidEditText)
        majorEditText = findViewById(R.id.majorEditText)
        minorEditText = findViewById(R.id.minorEditText)
        broadcastButton = findViewById(R.id.broadcastButton)
        statusTextView = findViewById(R.id.statusTextView)

        checkPermissions()

        broadcastButton.setOnClickListener {
            if (isTransmitting) {
                stopBroadcasting()
            } else {
                startBroadcasting()
            }
        }

        // Check if the device supports transmission
        if (BeaconTransmitter.checkTransmissionSupported(this) != BeaconTransmitter.SUPPORTED) {
            statusTextView.text = "Error: Device does not support BLE Advertising"
            broadcastButton.isEnabled = false
            uuidEditText.isEnabled = false
            majorEditText.isEnabled = false
            minorEditText.isEnabled = false
        }
    }

    private fun startBroadcasting() {
        // --- 1. Input Validation ---
        val uuidString = uuidEditText.text.toString()
        val majorString = majorEditText.text.toString()
        val minorString = minorEditText.text.toString()

        val beaconUuid: UUID
        val major: Int
        val minor: Int

        try {
            beaconUuid = UUID.fromString(uuidString)
        } catch (e: IllegalArgumentException) {
            statusTextView.text = "Error: Invalid UUID format"
            return
        }

        try {
            major = majorString.toInt()
            minor = minorString.toInt()
        } catch (e: NumberFormatException) {
            statusTextView.text = "Error: Major/Minor must be numbers"
            return
        }

        // --- 2. Build the Beacon ---
        // The layout string is crucial for iBeacon compatibility
        val iBeaconLayout = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"
        val beacon = Beacon.Builder()
            .setId1(beaconUuid.toString()) // UUID
            .setId2(major.toString())      // Major
            .setId3(minor.toString())      // Minor
            .setManufacturer(0x0118)       // Apple's Manufacturer Code for iBeacon
            .setTxPower(-59)               // Standard transmission power
            .build()

        // --- 3. Start Advertising ---
        val beaconParser = BeaconParser().setBeaconLayout(iBeaconLayout)
        beaconTransmitter = BeaconTransmitter(applicationContext, beaconParser)
        beaconTransmitter?.startAdvertising(beacon)

        // --- 4. Update UI ---
        isTransmitting = true
        broadcastButton.text = "Stop Broadcasting"
        statusTextView.text = "Status: Broadcasting!"
    }

    private fun stopBroadcasting() {
        beaconTransmitter?.stopAdvertising()

        isTransmitting = false
        broadcastButton.text = "Start Broadcasting"
        statusTextView.text = "Status: Stopped"
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (checkSelfPermission(Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH_ADVERTISE), 1)
            }
        }
    }
}