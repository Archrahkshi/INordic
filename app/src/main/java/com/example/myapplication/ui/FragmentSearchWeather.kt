package com.example.myapplication.ui

import android.animation.ObjectAnimator
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.data.WeatherRepository
import kotlinx.android.synthetic.main.fragment_search_weather.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FragmentSearchWeather(
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : Fragment(), CoroutineScope {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView.setOnLongClickListener {
            val intent = Intent().also {
                it.putExtra("imageResource", R.mipmap.ic_launcher_round)
                it.putExtra("imageTag", imageView.tag as String)
            }
            val item = ClipData.Item(intent)
            val data = ClipData(
                "foo",
                arrayOf(ClipDescription.MIMETYPE_TEXT_INTENT),
                item
            )
            val shadow = MyShadow(it, imageView.drawable.mutate().constantState!!.newDrawable())
            it.startDragAndDrop(
                data,
                shadow,
                null,
                0
            )
        }

        buttonSearch.setOnDragListener { _, event ->
            if (event.action == DragEvent.ACTION_DROP) {
                buttonSearch.text = event.clipData.getItemAt(0).intent.getStringExtra("imageTag")
            }
            true
        }

        imageView.setOnDragListener { _, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_EXITED -> imageView.invalidate()
            }
            true
        }

//        imageView.setOnTouchListener { view, motionEvent ->
//            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
//                view.x = motionEvent.rawX - view.width/2
//                view.y = motionEvent.rawY - view.height/2
//            }
//            true
//        }

        val repo = WeatherRepository()

        buttonSearch.setOnClickListener {

            // 1
            val animation = buttonSearch.animate().rotationBy(20f)
            animation.duration = 2000
            animation.start()

            // 2
            buttonSearch.animate().rotationBy(20f).setDuration(2000).start()

            // 3
            buttonSearch.animate().let {
                it.rotationBy(20f)
                it.duration = 2000
                it.scaleXBy(1.5f)
                it.scaleYBy(1.5f)
                it.translationXBy(100f)
                it.start()
            }

            // 4
            ObjectAnimator.ofFloat(buttonSearch, "rotation", 20f, 10f, 20f).start()

//            AnimatorSet().let {
//                it.playSequentially(...)
//                it.playTogether(...)
//            }

            launch {
                Log.wtf("SAsfsaf", "sdfsf")
                val weather = repo.getCurrentWeatherForCity("Moscow", "ru").await()
                Log.wtf("Погода", weather?.toString() ?: "None")
            }
            Log.wtf("asfd", "sadff")

            val fragment = FragmentWeather()
            val bundle = Bundle()
            bundle.putString("City", editTextCity.text.toString())
            bundle.putString("Code", editTextCode.text.toString())
            fragment.arguments = bundle


//            fragmentManager?.beginTransaction()
//                ?.replace(R.id.frameLayout, BlankFragment3())
//                ?.addToBackStack(null)
//                ?.commit()
        }
    }
}
