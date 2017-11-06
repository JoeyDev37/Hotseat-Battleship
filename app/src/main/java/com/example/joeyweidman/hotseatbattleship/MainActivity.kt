package com.example.joeyweidman.hotseatbattleship

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.AdapterView



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myApp: MyApplication = MyApplication()

        MyApplication.P1ShipGrid = Array(10, {Array(10, {Cell(this, false)})})
        MyApplication.P1AttackGrid = Array(10, {Array(10, {Cell(this, true)})})
        MyApplication.P2ShipGrid = Array(10, {Array(10, {Cell(this, false)})})
        MyApplication.P2AttackGrid = Array(10, {Array(10, {Cell(this, true)})})

        //Button opens up the brush control activity
        newGameButton.setOnClickListener {
            val intent: Intent = Intent(MyApplication.applicationContext(), PlaceShipsActivity::class.java)
            startActivity(intent)
        }

        deleteButton.setOnClickListener {

        }

        saveButton.setOnClickListener {
            MyApplication.writeObject("Test1")
        }

        gameListView.adapter = MyCustomAdapter()

        /*gameListView.setClickable(true)
        gameListView.setOnItemClickListener(AdapterView.OnItemClickListener { arg0, arg1, position, arg3 ->
            val o = gameListView.getItemAtPosition(position)
            /* write you handling code like...
            String st = "sdcard/";
            File f = new File(st+o.toString());
            // do whatever u want to do with 'f' File object
            */
        })*/
    }

    private class MyCustomAdapter: BaseAdapter() {
        //Responsible for rendering each row
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val textView = TextView(MyApplication.applicationContext())
            textView.text = MyApplication.applicationContext().filesDir.listFiles()[position].name
            notifyDataSetChanged()
            return textView
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
