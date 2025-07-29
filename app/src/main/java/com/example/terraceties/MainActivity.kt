package com.example.terraceties

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity(), MyInterface {

    var nextFrag = "loading"
    var currFrag = "loading"
    var userName = "USERNAME"

    var hasLoaded = false

    var allResultsArr = arrayOfNulls<Array<String>>(600)
    var allFixturesArr = arrayOfNulls<Array<String>>(600)
    var teamResults = arrayOfNulls<Array<String>>(47)
    var teamFixtures = arrayOfNulls<Array<String>>(47)
    var venueArray = arrayOfNulls<Array<String>>(24)
    var logoArray = arrayOfNulls<Array<String>>(24)
    var standingsArr = arrayOfNulls<Array<String>>(24)

    private var teamImgBmp: Bitmap? = null
    private var apiData: String? = null
    private lateinit var chosenTeam: String
    //string to store all the fixtures in the league



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)


        if(checkSharedPrefrences() == false){
            val sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
            val team = sharedPreferences.getString("USER_PROFILE_0",null)
            if (team != null) {
                chosenTeam = initVenLogoArr(team)
                getApiData(this, chosenTeam)
                getLeagueInfo(this)
                setUserName()
            }
        }else {
            val frag = "sett_page"
            setFragFromFrag(frag)
        }

//        getApiData(this, chosenTeam)
    }




    fun teamURLtoBitMap(team: String,array:Array<Array<String>?>){
        var url = ""
        for(i in 0..array.size -1 ){
            val currArray = array[i]
            if( currArray != null) {
                if(currArray[0] == team){
                   url = currArray[1]
                }
            }
        }

        Log.d("TAG3", url)
        val executor = Executors.newSingleThreadExecutor()
//        val handler = Handler(Looper.getMainLooper())
        var image:Bitmap? = null
        executor.execute{

            try {
                val `in` = java.net.URL(url).openStream()
                image = BitmapFactory.decodeStream(`in`)
                Log.d("TAG3", image.toString())
                teamImgBmp = image
                Log.d("TAG3", teamImgBmp.toString())

//                handler.post{
//                    img.setImageBitmap(image)
//                }
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
    }


    fun initVenLogoArr(team: String):String{
        //a function to load these arrays with the names of the teams and also an N/A value which will be replaced by data relevant to the array
        var retTeam = ""
        val len1 = venueArray.size
        val len2 = logoArray.size
        val teams = arrayOf("AFC Fylde", "Aldershot Town", "Altrincham", "Barnet", "Boreham Wood", "Bromley", "Chesterfield", "Dagenham & Redbridge", "Dorking Wanderers", "Eastleigh", "Ebbsfleet United", "FC Halifax Town", "Gateshead", "Hartlepool", "Kidderminster Harriers", "Maidenhead", "Oldham", "Oxford City", "Rochdale", "Solihull Moors", "Southend", "Wealdstone", "Woking", "York")

        retTeam = teams[team.toInt()]

        for(i in 0 .. len1 - 1){
            val data = arrayOf(teams[i], "N/A")
            venueArray[i] = data
        }
        for(j in 0 .. len2 - 1){
            val data = arrayOf(teams[j], "N/A")
            logoArray[j] = data
        }

        return retTeam
    }


    internal fun getClosestFixture() :Array<String>?{
        var fixture = 0
        var recent = " "
        val length = teamFixtures.size
        for (i in 0..length -1){
            val array = teamFixtures[i]
            if (array != null){
                var date = array[4]
                var day = (date.get(8)+""+date.get(9))
                var month = (date.get(5)+""+date.get(6))
                var year = (date.get(0)+""+date.get(1)+""+date.get(2)+""+date.get(3))
                date = (year+""+month+""+day)
                if(recent == " "){
                    recent = date
                    fixture = i
                }
                else{
                    if(date < recent){
                        recent = date
                        fixture = i
                    }
                }
            }
        }
        return teamFixtures[fixture]
    }
    

    internal fun getClosestResult():Array<String>?{
        var result = 0
        var recent = " "
        val length = teamResults.size
        for (i in 0..length -1){
            val array = teamResults[i]
            if (array != null){
                var date = array[6]
                var day = (date.get(8)+""+date.get(9))
                var month = (date.get(5)+""+date.get(6))
                var year = (date.get(0)+""+date.get(1)+""+date.get(2)+""+date.get(3))
                date = (year+""+month+""+day)
                if(recent == " "){
                    recent = date
                    result = i
                }
                else{
                    if(date > recent){
                        recent = date
                        result = i
                    }
                }
            }
        }
        return teamResults[result]
    }


    override fun setFragFromFrag(frag:String) {
        if (frag != null) {
            nextFrag = frag

            val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val navController = navHostFragment.navController

            if (nextFrag != currFrag) {
                if (nextFrag == "homePage") {
                    currFrag = "homePage"
                    navController.navigate(R.id.homePage)
                }
                if (nextFrag == "club_form_page") {
                    currFrag = "club_form_page"
                    val pBundle = Bundle()
                    val allfixturesBundle = getFixturesFromArray(allFixturesArr)
                    val teamFixturesBundle = getFixturesFromArray(teamFixtures)
                    pBundle.putBundle("ALL_FIXTURES", allfixturesBundle)
                    pBundle.putBundle("TEAM_FIXTURES", teamFixturesBundle)
                    navController.navigate(R.id.club_form_page, pBundle)
                }
                if (nextFrag == "stad_quest") {
                    currFrag = "stad_quest"
                    navController.navigate(R.id.stad_quest2)
                    //getClosestFixture()
                }
                if (nextFrag == "results_page") {
                    currFrag = "results_page"
                    val pBundle = Bundle()
                    val allResultsBundle = getResultsFromArray(allResultsArr)
                    val teamResBundle = getResultsFromArray(teamResults)
                    pBundle.putBundle("ALL_RESULTS", allResultsBundle)
                    pBundle.putBundle("TEAM_RESULTS", teamResBundle)
                    navController.navigate(R.id.results_page, pBundle)
                }
                if (nextFrag == "profile_page") {
                    currFrag = "profile_page"
                    if (teamImgBmp == null) {
                        teamURLtoBitMap(chosenTeam, logoArray)
                        navController.navigate(R.id.profilePage)
                    } else {
                        navController.navigate(R.id.profilePage)
                    }
                }
                if (nextFrag == "sett_page") {
                    currFrag = "sett_page"
                    navController.navigate(R.id.settingsPage)
                }
                if (nextFrag == "chat_page"){
                    val i = Intent(this, chatFeature::class.java)
                    startActivity(i)
                }
                if(nextFrag == "leaInf_page"){
                    currFrag = "leaIng_page"
                    navController.navigate(R.id.league_info)
                }
                if(nextFrag == "speak_up") {
                    currFrag = "speak_up"
                    navController.navigate(R.id.speakUp)
                }
            }
        }
    }




    fun displayData(w:String) {
        //linear layout in club form page using val textView = TextView(this) to create text view and add to bottom of the tree
        // can then edit the text to the results of the club
    }

    fun getApiData(view: MainActivity, team:String) {
        val client = OkHttpClient()
        Log.d("FLOAT", "api data called")

        val request = Request.Builder()
            .url("https://api-football-v1.p.rapidapi.com/v3/fixtures?league=43&season=2023")
            .get()
            .addHeader("X-RapidAPI-Key", "93474c5c69mshd017dfd9d7ac001p1d6709jsn290592127596")
            .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
            .build()

        Log.d("FLOAT", request.toString());

        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                readJSONFact(body.toString(), team)
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d("FLOAT", "Failed to execute request")
            }
        })

    }


    fun readJSONFact(rawJson: String, team: String  ) {
        runOnUiThread(java.lang.Runnable {
            Log.d("FLOAT", rawJson.toString())
            try {
                var json = JSONObject(rawJson)
                apiData = rawJson
                var responses = json.getJSONArray("response")
                var resLen = responses.length()
                var Rcount = 0;
                var Fcount =0;
                for(i in 0..resLen){
                    if(responses.getJSONObject(i).getJSONObject("fixture").getJSONObject("status").getString("short") == "FT"){
                        var home = responses.getJSONObject(i).getJSONObject("teams").getJSONObject("home").getString("name")
                        var away = responses.getJSONObject(i).getJSONObject("teams").getJSONObject("away").getString("name")
                        var goalsH = responses.getJSONObject(i).getJSONObject("goals").getString("home")
                        var goalsA = responses.getJSONObject(i).getJSONObject("goals").getString("away")
                        var date = responses.getJSONObject(i).getJSONObject("fixture").getString("date")
                        var round = responses.getJSONObject(i).getJSONObject("league").getString("round")
                        var venue = responses.getJSONObject(i).getJSONObject("fixture").getJSONObject("venue").getString("name")

                        val data = arrayOf((i.toString()),round,home,away,goalsH,goalsA,date,venue)
                        Log.d("FLOAT", String.format("%s,%s,%s,%s,%s,%s,%s", i.toString(),round,home,away,goalsH,goalsA,date))
                        allResultsArr[i] = data

                        if(home == team || away == team){
                            val dataT = arrayOf((Rcount.toString()),round,home,away,goalsH,goalsA,date,venue)
                            teamResults[Rcount] = dataT
                            Rcount+=1
                        }
                    }
                    else{
                        var home = responses.getJSONObject(i).getJSONObject("teams").getJSONObject("home").getString("name")
                        var away = responses.getJSONObject(i).getJSONObject("teams").getJSONObject("away").getString("name")
                        var date = responses.getJSONObject(i).getJSONObject("fixture").getString("date")
                        var round = responses.getJSONObject(i).getJSONObject("league").getString("round")
                        var venue = responses.getJSONObject(i).getJSONObject("fixture").getJSONObject("venue").getString("name")


                        val data = arrayOf((i.toString()),round,home,away,date,venue)
                        Log.d("FLOAT2", String.format("%s,%s,%s,%s,%s", i.toString(),round,home,away,date,venue))
                        allFixturesArr[i] = data

                        if(home == team || away == team) {
                            val dataT = arrayOf((Fcount.toString()), round, home, away, date,venue)
                            teamFixtures[Fcount] = dataT
                            Fcount += 1
                        }
                    }

                    var venue = responses.getJSONObject(i).getJSONObject("fixture").getJSONObject("venue").getString("name")
                    var home = responses.getJSONObject(i).getJSONObject("teams").getJSONObject("home").getString("name")
                    var repl = false

                    for(j in 0..venueArray.size - 1){
                        val currArr = venueArray[j]
                        if(currArr != null){
                            if(currArr[0] == home.toString()){
                                if(currArr[1] != "N/A"){
                                    repl = true
                                }
                                if(currArr[1] == "N/A"){
                                    currArr[1] = venue
                                    venueArray[j] = currArr
                                    repl = true
                                }
                            }
                        }
                        if (repl == true){
                            break
                        }
                    }


                    var logo = responses.getJSONObject(i).getJSONObject("teams").getJSONObject("home").getString("logo")
                    var replaced = false
                    for(j in 0..logoArray.size - 1){
                        val currArr = logoArray[j]
                        if(currArr != null){
                            if(currArr[0] == home.toString()){
                                if(currArr[1] != "N/A"){
                                    replaced = true
                                }
                                if(currArr[1] == "N/A"){
                                    currArr[1] = logo
                                    logoArray[j] = currArr
                                    replaced = true
                                }
                            }
                        }
                        if (replaced == true){
                            break
                        }
                    }

                    //both similar iterative loops which iterate through the JSON results and puts strings into an array once
                    hasLoaded = true
                }
            } catch (e: JSONException) {
                displayData("INVALID JSON TEXT")
                e.printStackTrace()
            }
            teamURLtoBitMap(team, logoArray)
        })
    }


    fun getLeagueInfo(view: MainActivity){
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://api-football-v1.p.rapidapi.com/v3/standings?season=2023&league=43")
            .get()
            .addHeader("X-RapidAPI-Key", "93474c5c69mshd017dfd9d7ac001p1d6709jsn290592127596")
            .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                readLeagueStandings(body.toString())
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d("FLOAT", "Failed to execute request")
            }
        })
    }

    fun readLeagueStandings(rawJSON:String){
        runOnUiThread(java.lang.Runnable {
            try{
                var json = JSONObject(rawJSON)
                var responses = json.getJSONArray("response")
                var resLen = responses.length()

                val leagueS = responses.getJSONObject(0).getJSONObject("league")
                    .getJSONArray("standings").getJSONArray(0)
                val standSize = leagueS.length()

                for(i in 0..standSize){
                    val currTeam = leagueS.getJSONObject(i)
                    val pos = currTeam.getString("rank")
                    val team = currTeam.getJSONObject("team").getString("name")
                    val mp = currTeam.getJSONObject("all").getString("played")
                    val mw = currTeam.getJSONObject("all").getString("win")
                    val md = currTeam.getJSONObject("all").getString("draw")
                    val ml = currTeam.getJSONObject("all").getString("lose")
                    val gf = currTeam.getJSONObject("all").getJSONObject("goals").getString("for")
                    val ga = currTeam.getJSONObject("all").getJSONObject("goals").getString("against")
                    val gd = currTeam.getString("goalsDiff")
                    val points = currTeam.getString("points")
                    val form = currTeam.getString("form")
                    val desc = currTeam.getString("description")

                    val data = arrayOf(pos, team, mp,mw,md,ml,gf,ga,gd,points,form,desc)
                    Log.d("TAG3", String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",pos, team, mp,mw,md,ml,gf,ga,gd,points,form,desc ))
                    standingsArr[i] = data
                }
            }catch (e: JSONException) {
                displayData("INVALID JSON TEXT")
                e.printStackTrace()
            }
        })
    }

    internal fun getLeagueStandings():Array<Array<String>?>{
        return standingsArr
    }



    internal fun getHasLoaded():Boolean{
        return hasLoaded
    }

    internal fun updateTeamFixtures(team:String){
        var tempTeamFixtures = arrayOfNulls<Array<String>>(47)
        var tempTeamResults = arrayOfNulls<Array<String>>(47)
        var trCount = 0
        var tfCount = 0

        for(i in 0 .. (allResultsArr.size - 1)){
            val temp = allResultsArr[i]
            if(temp!=null){
                if(temp[2] == team || temp[3] == team){
                    tempTeamResults[trCount] = allResultsArr[i]
                    trCount += 1
                }
            }
        }

        for(i in 0 .. (allFixturesArr.size - 1)){
            val temp = allFixturesArr[i]
            if(temp!=null){
                if(temp[2] == team || temp[3] == team){
                    tempTeamFixtures[tfCount] = allFixturesArr[i]
                    trCount += 1
                }
            }
        }

        teamFixtures = tempTeamFixtures
        teamResults = tempTeamResults

    }

    fun getFixturesFromArray(array: Array<Array<String>?>):Bundle{
        val len = array.size
        var afBundle = Bundle()
        var bundleSize = 0

        for(i in 0..len - 1){
            afBundle.putStringArray(i.toString(), array[i])
            bundleSize += 1
        }

        afBundle.putInt("BUNDLE_SIZE", bundleSize)
        return afBundle

    }


    fun getResultsFromArray(array: Array<Array<String>?>):Bundle{
        val len = array.size
        var arBundle = Bundle()
        var bundleSize = 0

        for(i in 0..len - 1){
            arBundle.putStringArray(i.toString(),array[i])
            bundleSize += 1
        }
        arBundle.putInt("BUNDLE_SIZE", bundleSize)
        return arBundle
    }



    internal fun getStadiumsFromArray():Array<Array<String>?>{
        return venueArray
    }

    internal fun getLogosFromArray():Array<Array<String>?>{
        return logoArray
    }


    internal fun checkSharedPrefrences():Boolean?{
        val sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val profileSettings = sharedPreferences.getString("USER_PROFILE_6", null)
        return profileSettings == null
    }

    internal fun setCurrTeam(team:String){
        chosenTeam = team
    }
    internal fun getCurrTeam():String{
        return chosenTeam
    }

    internal fun getTeamBmp():Bitmap?{
        return teamImgBmp
    }

    fun setUserName(){
        val sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("USER_PROFILE_1",null)
        if(name != null){
            userName = name
        }
    }

    internal fun getUserName():String{
        return userName
    }

}
