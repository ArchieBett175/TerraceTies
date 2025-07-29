package com.example.terraceties

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class homePage : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private var mGoogleMap: GoogleMap? = null

    private lateinit var lastLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 1
    private val venues: List<Venue> by lazy {
        VenueList.getVenue()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home_page, container, false)
        val activi:MainActivity = activity as MainActivity

        fusedLocationProviderClient =LocationServices.getFusedLocationProviderClient(activi)
        //used to retrieve the devices last known location

        val mapFramgent = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFramgent.getMapAsync(this)


        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val closestFixture = (activity as MainActivity).getClosestFixture()
        val fixt_but = view.findViewById<AppCompatButton>(R.id.fixtureButton)
        displayFixture(fixt_but, closestFixture)

        val closestResult = (activity as MainActivity).getClosestResult()
        val result_but = view.findViewById<AppCompatButton>(R.id.resultButton)
        displayResult(result_but, closestResult)


        val myInterface: MyInterface = activity as MyInterface

        fixt_but.setOnClickListener {
            myInterface.setFragFromFrag("club_form_page")
            Log.d("TAG", "buttonPressed")
        }

        val stad_quest = view.findViewById<AppCompatButton>(R.id.stad_quest)
        stad_quest.setOnClickListener {
            myInterface.setFragFromFrag("stad_quest")
            Log.d("FLOAT", "STAD_QUEST")
        }

        result_but.setOnClickListener {
            myInterface.setFragFromFrag("results_page")
        }

        val profileBut = view.findViewById<AppCompatButton>(R.id.profileButton)
        profileBut.setOnClickListener {
            myInterface.setFragFromFrag("profile_page")
        }

        val messengerButton = view.findViewById<AppCompatButton>(R.id.messengerButton)
        messengerButton.setOnClickListener {
            myInterface.setFragFromFrag("chat_page")
        }

        val leaInfBut = view.findViewById<AppCompatButton>(R.id.leagueInfo)
        leaInfBut.setOnClickListener {
            myInterface.setFragFromFrag("leaInf_page")
        }

        val speaUP = view.findViewById<AppCompatButton>(R.id.speakUP)
        speaUP.setOnClickListener {
            myInterface.setFragFromFrag("speak_up")
        }
    }



    fun displayFixture(button: AppCompatButton, array: Array<String>?) {
        var data = StringBuilder()
        if (array != null) {
            val date = array[4]
            val date1 = (date[8] + "" + date[9] + "." + date[5] + "" + date[6] + "." + date[0]
                    + "" + date[1] + "" + date[2] + "" + date[3])
            val time = (date[11] + "" + date[12] + "" + date[13] + "" + date[14] + "" + date[15])
            data.append(date1)
                .append("\n")
                .append(array[2])
                .append(" VS ")
                .append(array[3])
                .append("\n")
                .append(time)

        }else{
            data.append("Fixtures Not Available")
        }
        button.text = data
    }

    fun displayResult(button: AppCompatButton, array: Array<String>?) {
        var data = StringBuilder()
        if (array != null) {
            val date = array[6]
            val date1 = (date[8] + "" + date[9] + "." + date[5] + "" + date[6] + "." + date[0]
                    + "" + date[1] + "" + date[2] + "" + date[3])
            data.append(date1)
                .append("\n")
                .append(array[2])
                .append(" - ")
                .append(array[4])
                .append(" : ")
                .append(array[5])
                .append(" - ")
                .append(array[3])
                .append("\n")
                .append("FINISHED")
        }else{
            data.append("Results Not Available")
        }
        button.text = data
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        mGoogleMap!!.uiSettings.isZoomControlsEnabled = true
        mGoogleMap!!.setOnMarkerClickListener(this)

        setUpMap()
    }

    private fun setUpMap() {
        val acti:MainActivity = activity as MainActivity
        if (ActivityCompat.checkSelfPermission(acti, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(acti, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)

            return
        }
        mGoogleMap?.isMyLocationEnabled = true
        fusedLocationProviderClient.lastLocation.addOnSuccessListener(acti) { location ->

            if(location!=null){
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLong)
                addStadMarkers()
                mGoogleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12f))
            }
        }

    }

    private fun addStadMarkers(){
        val bmp = drawableToBmp(context)

        venues.forEach{ venue ->
            val marker = mGoogleMap?.addMarker(
                MarkerOptions()
                    .title(venue.name)
                    .position(venue.latLang)
                    .icon(bmp)
                //change icon on the map to something resembling the stadium
            )
        }
    }

    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")
        mGoogleMap?.addMarker(markerOptions)
    }

    override fun onMarkerClick(p0: Marker) = false
}

private fun drawableToBmp(context: Context?): BitmapDescriptor{
    val drwble = ResourcesCompat.getDrawable(context!!.resources, R.drawable.baseline_stadium_24, null)
    if(drwble == null){
        return (BitmapDescriptorFactory.defaultMarker())
    }
    val bmp = Bitmap.createBitmap(
        drwble.intrinsicWidth,
        drwble.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = android.graphics.Canvas(bmp)
    drwble.setBounds(0,0,canvas.width, canvas.height)
    drwble.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bmp)
}