package com.example.terraceties

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView

class chatFeature : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_feature)
        val toolBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        supportActionBar?.hide()
        setSupportActionBar(toolBar)
        setToolBarTitle("ConcourseChats")

        val drawerLayout = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawerLayout)
        val toggle = ActionBarDrawerToggle(this, drawerLayout,toolBar,R.string.open,R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val nav_menu = findViewById<NavigationView>(R.id.nav_menu)
        nav_menu.setNavigationItemSelectedListener(this)


        val backButton = findViewById<AppCompatButton>(R.id.chatBack)
        backButton.setOnClickListener {
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        val navController = navHostFragment.navController

        if(item.itemId == R.id.general){
            setToolBarTitle("General")
            navController.navigate(R.id.chatFragGen)
        }
        if(item.itemId == R.id.nextGame){
            setToolBarTitle("Next Game")
            navController.navigate(R.id.chatFragNextGame)
        }
        if(item.itemId == R.id.lastGame){
            setToolBarTitle("Last Game")
            navController.navigate(R.id.chatFragLastGame)
        }
        if(item.itemId == R.id.league){
            setToolBarTitle("League")
            navController.navigate(R.id.chatFragLeague)
        }
        if(item.itemId == R.id.cup){
            setToolBarTitle("Cup")
            navController.navigate(R.id.chatFragCup)
        }

        return true
    }

    fun setToolBarTitle(title:String){
        supportActionBar?.title = title
    }




}