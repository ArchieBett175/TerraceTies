package com.example.terraceties

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class stad_quest : Fragment() {

    //To-Do - Styling, Exif interface get the long and lat and compare to the long and lat of the stadium to see if in correct place

    var selectedItem = "N/A"

    private lateinit var button: AppCompatButton
    private lateinit var imageView: ImageView
    var buttonValue = "LOAD_IMAGE"

    private lateinit var currImageURI: Uri

    val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val galleryUri = it
        if (galleryUri != null) {
            currImageURI = galleryUri
        }
        try {
            imageView.setImageURI(galleryUri)
            imageView.visibility = View.VISIBLE
            button.text = "SAVE IMAGE"
            buttonValue = "SAVE_IMAGE"

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stad_quest, container, false)
        val myInterface: MyInterface = activity as MyInterface
        val myActiv: MainActivity = activity as MainActivity

        button = view.findViewById(R.id.imageLoad)
        imageView = view.findViewById(R.id.stadPhoto)

        button.setOnClickListener {
            if(buttonValue == "LOAD_IMAGE"){
                galleryLauncher.launch("image/*")
            }
            if(buttonValue == "SAVE_IMAGE"){
                val bmp = switchUriToBMP()
                deletePhotoFromInternalStorate(selectedItem)
                savePhotoToInternalStorage(selectedItem, bmp)
            }
            if (buttonValue == "SET_NEW_IMAGE"){
                galleryLauncher.launch("image/*")
            }
        }

        val venArray = myActiv.getStadiumsFromArray()
        val venTxt = view.findViewById<TextView>(R.id.venueView)

        val spinnerID = view.findViewById<Spinner>(R.id.teamSpinner)
        val teams = arrayOf("AFC Fylde", "Aldershot Town", "Altrincham", "Barnet", "Boreham Wood", "Bromley", "Chesterfield", "Dagenham & Redbridge", "Dorking Wanderers", "Eastleigh", "Ebbsfleet United", "FC Halifax Town", "Gateshead", "Hartlepool", "Kidderminster Harriers", "Maidenhead", "Oldham", "Oxford City", "Rochdale", "Solihull Moors", "Southend", "Wealdstone", "Woking", "York")
        val arrayAdp = ArrayAdapter(myActiv, android.R.layout.simple_spinner_dropdown_item, teams)
        spinnerID.adapter = arrayAdp
        spinnerID.textAlignment = View.TEXT_ALIGNMENT_CENTER
        //Deals with the current selection of the drop down, and will provide the current selection as "selectedItem"
        spinnerID.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedItem = teams[position]
                displayVenue(selectedItem, venArray,venTxt)
                checkInternalStorage(selectedItem)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(myActiv, "Nothing Selected", Toast.LENGTH_LONG).show()
            }
        }
        val backBut = view.findViewById<AppCompatButton>(R.id.sq_back_button)
        backBut.setOnClickListener {
            myInterface.setFragFromFrag("homePage")
        }

        return view
    }

    private fun setProgressBar(len:Int){
        val activ:MainActivity = activity as MainActivity
        val progressBar = activ.findViewById<ProgressBar>(R.id.progressBar)

        val sharedPreferences = activ.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("STAD_VISITED", len.toString())
        editor.apply()

        progressBar.max = 2400
        val progress = len*100
        ObjectAnimator.ofInt(progressBar, "progress", progress)
            .setDuration(2000)
            .start()
    }


    private fun checkInternalStorage(item:String){
        lifecycleScope.launch {
            val photo = loadPhotosFromInternalStorage(item)
            if(photo!=null){
                loadImageIntoApp(photo)
            }else{
                promptLoad()
            }
        }
    }

    private fun promptLoad(){
        imageView.visibility = View.INVISIBLE
        button.text = "LOAD IMAGE"
        buttonValue = "LOAD_IMAGE"
    }

    private fun loadImageIntoApp(photo:internalStoragePhoto){
        val bitmap = photo.bmp
        imageView.visibility = View.VISIBLE
        imageView.setImageBitmap(bitmap)
        button.text = "SET NEW IMAGE"
        buttonValue = "SET_NEW_IMAGE"
    }

    private fun savePhotoToInternalStorage(currentTeam: String, bmp: Bitmap):Boolean {
        val fileName = currentTeam.replace(' ', '_')
        val activ:MainActivity = activity as MainActivity
        return try {
           activ.openFileOutput("$fileName.jpg", MODE_PRIVATE).use{ stream ->
                if(!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap")
                }
           }
            true
        } catch (e:IOException){
            e.printStackTrace()
            false
        }
    }

    private fun deletePhotoFromInternalStorate(item:String): Boolean{
        val activ:MainActivity = activity as MainActivity
        val temp = item.replace(' ', '_')
        val fileName = "$temp.jpg"
        return try{
            activ.deleteFile(fileName)
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }


    private suspend fun loadPhotosFromInternalStorage(item:String): internalStoragePhoto?{
        val activ:MainActivity = activity as MainActivity
        var groundPhoto: internalStoragePhoto? = null
        val bmpLi = withContext(Dispatchers.IO){
            val files = activ.filesDir.listFiles()
            files.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg")}.map {
                val bytes = it.readBytes()
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                internalStoragePhoto(it.name, bmp)
            }
        }
        val filename = item.replace(' ','_')
        for(i in 0..bmpLi.size - 1){
            if(bmpLi[i].name == "$filename.jpg"){
                groundPhoto = bmpLi[i]
            }
        }
        setProgressBar(bmpLi.size)
        return groundPhoto
    }


    fun displayVenue(team:String, array:Array<Array<String>?>, txtView:TextView){
        for(i in 0..array.size -1 ){
            val currArray = array[i]
            if( currArray != null) {
                if(currArray[0] == team){
                    val ven = currArray[1]
                    txtView.text = ven
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun switchUriToBMP(): Bitmap {
        val source = ImageDecoder.createSource(requireActivity().contentResolver, currImageURI)
        val bitmap = ImageDecoder.decodeBitmap(source)
        return bitmap
    }

}