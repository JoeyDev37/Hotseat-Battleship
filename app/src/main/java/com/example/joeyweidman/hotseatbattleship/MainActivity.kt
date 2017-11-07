package com.example.joeyweidman.hotseatbattleship

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.AdapterView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myApp: MyApplication = MyApplication()
        val context: Context = MyApplication.applicationContext()

        MyApplication.P1ShipGrid = Array(10, {Array(10, {Cell(this, false)})})
        MyApplication.P1AttackGrid = Array(10, {Array(10, {Cell(this, true)})})
        MyApplication.P2ShipGrid = Array(10, {Array(10, {Cell(this, false)})})
        MyApplication.P2AttackGrid = Array(10, {Array(10, {Cell(this, true)})})

        MyApplication.gameState = "Starting"

        //Button opens up the brush control activity
        newGameButton.setOnClickListener {
            for(i in 0..9) {
                for(j in 0..9) {
                    MyApplication.P1ShipGrid[j][i].currentStatus = Status.EMPTY
                    MyApplication.P1ShipGrid[j][i].shipType = null
                    MyApplication.P1AttackGrid[j][i].currentStatus = Status.EMPTY
                    MyApplication.P1AttackGrid[j][i].shipType = null
                    MyApplication.P2ShipGrid[j][i].currentStatus = Status.EMPTY
                    MyApplication.P2ShipGrid[j][i].shipType = null
                    MyApplication.P2AttackGrid[j][i].currentStatus = Status.EMPTY
                    MyApplication.P2AttackGrid[j][i].shipType = null
                }
            }
            MyApplication.currentPlayer = 1
            var gameName = "Game"
            val sb: StringBuilder = StringBuilder()
            sb.append(gameName)
            sb.append(context.filesDir.listFiles().size.toString())
            gameName = sb.toString()

            MyApplication.gameState = "Starting"
            MyApplication.gameName = gameName
            MyApplication.saveGame(MyApplication.gameName)
            gameListView.invalidateViews()
            val intent: Intent = Intent(MyApplication.applicationContext(), PlaceShipsActivity::class.java)
            startActivity(intent)
        }

        deleteButton.setOnClickListener {
            val name = context.filesDir.listFiles()[0].name
            deleteFile(name)
            gameListView.invalidateViews()
        }

        saveButton.setOnClickListener {
            MyApplication.saveGame(MyApplication.gameName)
            gameListView.invalidateViews()
            //val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create() // for pretty print feature
            //val json: String = gson.toJson(MyApplication)
            //Log.e("MyApplication", json)
            //MyApplication.saveGame()
        }

        gameListView.adapter = MyCustomAdapter()

        //gameListView.setClickable(true)
        gameListView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(context, context.filesDir.list()[position], Toast.LENGTH_SHORT).show()
            MyApplication.loadGame(context.filesDir.listFiles()[position])
            if(MyApplication.gameState == "Starting") {
                val intent: Intent = Intent(MyApplication.applicationContext(), PlaceShipsActivity::class.java)
                startActivity(intent)
            } else if (MyApplication.gameState == "In Progress") {
                val intent: Intent = Intent(MyApplication.applicationContext(), GameScreenActivity::class.java)
                startActivity(intent)
            } else if (MyApplication.gameState == "P1 Victory" || MyApplication.gameState == "P1 Victory") {
                val intent: Intent = Intent(MyApplication.applicationContext(), TextActivity::class.java)
                startActivity(intent)
            }
            //MyApplication.readObject(context.filesDir.listFiles()[position])
        }
    }

    private class MyCustomAdapter: BaseAdapter() {
        //Responsible for rendering each row
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(MyApplication.applicationContext())
            val rowMain = layoutInflater.inflate(R.layout.row_main, viewGroup, false)
            val gameNameTextView = rowMain.findViewById<TextView>(R.id.gameNameText)
            gameNameTextView.text = MyApplication.applicationContext().filesDir.listFiles()[position].name
            return rowMain
            /*val textView = TextView(MyApplication.applicationContext())
            textView.text = MyApplication.applicationContext().filesDir.listFiles()[position].name
            notifyDataSetChanged()
            return textView*/
        }

        override fun getItem(position: Int): Any {
            return "TEST STRING"
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        //Responsible for how many rows in list
        override fun getCount(): Int {
            return MyApplication.applicationContext().filesDir.listFiles().size
        }

    }
}
