package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(
            R.id.frameLayout,
            FragmentPosts()
        ).commit()

        buttonFragment1.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(
                R.id.frameLayout,
                FragmentPosts()
            ).commit()
        }

        buttonFragment2.setOnClickListener {
            val fragment = FragmentCoroutines()
            val bundle = Bundle()
            bundle.putString("someData", "Ще не вмерла України\nНи слава, ни воля.\nЩе нам, браття українцi,\nУсмехнеться доля.")
            fragment.arguments = bundle
            supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
        }

        buttonFragment3.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(
                R.id.frameLayout,
                FragmentSearchWeather()
            ).commit()
        }

        buttonFragment4.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(
                R.id.frameLayout,
                FragmentWeather()
            ).commit()
        }

        buttonFragment5.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(
                R.id.frameLayout,
                FragmentPeople()
            ).commit()
        }
    }
}
