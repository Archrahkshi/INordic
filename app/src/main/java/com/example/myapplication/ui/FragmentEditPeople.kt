package com.example.myapplication.ui


import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.data.Person
import com.example.myapplication.data.PeopleRepository
import kotlinx.android.synthetic.main.fragment_edit_people.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class FragmentEditPeople(
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : Fragment(), CoroutineScope {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_people, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextFirstName.text = SpannableStringBuilder(arguments?.getString("firstName") ?: "")
        editTextLastName.text = SpannableStringBuilder(arguments?.getString("lastName") ?: "")
        editTextAge.text = SpannableStringBuilder(arguments?.getString("age") ?: "")

        buttonDone.setOnClickListener {
            launch {
                val people = Person(
                    firstName = editTextFirstName.text.toString(),
                    lastName = editTextLastName.text.toString(),
                    age = editTextAge.text.toString().toInt(),
                    id = arguments?.getString("id") ?: UUID.randomUUID().toString()
                )

                PeopleRepository().insertPerson(people).await()

                fragmentManager?.beginTransaction()
                    ?.replace(R.id.frameLayout, FragmentPeople())
                    ?.commit()
            }
        }
    }
}
