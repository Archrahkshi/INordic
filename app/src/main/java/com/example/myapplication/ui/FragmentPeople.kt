package com.example.myapplication.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.myapplication.R
import com.example.myapplication.data.PeopleAdapter
import com.example.myapplication.data.PeopleRepository
import kotlinx.android.synthetic.main.fragment_people.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FragmentPeople(
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : Fragment(), CoroutineScope {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewAdd.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.frameLayout, FragmentEditPeople())
                ?.addToBackStack(null)
                ?.commit()
        }

        val repo = PeopleRepository()
        launch {
            val options = arrayOf("All", "Adults", "Greater than 10", "Range 15..25")

            val people = repo.getPeople(options[0]).await().toMutableList()

            val adapter = PeopleAdapter(
                people,
                { person ->
                    val bundle = Bundle()
                    bundle.let {
                        it.putString("firstName", person.firstName)
                        it.putString("lastName", person.lastName)
                        it.putInt("age", person.age)
                        it.putString("id", person.id)
                    }
                    val fragmentEditPerson = FragmentEditPeople()
                    fragmentEditPerson.arguments = bundle
                    fragmentManager?.beginTransaction()
                        ?.replace(R.id.frameLayout, fragmentEditPerson)
                        ?.addToBackStack(null)
                        ?.commit()
                },
                {
                    repo.deletePerson(it)
                }
            )

            recyclerViewPeople.adapter = adapter

            spinnerMode.adapter = ArrayAdapter(
                context!!,
                android.R.layout.simple_spinner_dropdown_item,
                options
            )

            spinnerMode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    this@FragmentPeople.launch {
                        val people = repo.getPeople(options[position]).await()
                        adapter.updatePeopleList(people)
                    }
                }
            }
        }
    }
}
