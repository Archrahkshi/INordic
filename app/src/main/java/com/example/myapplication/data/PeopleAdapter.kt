package com.example.myapplication.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.item_person.view.*

class PeopleAdapter(
    private val people: MutableList<Person>,
    private val actionEdit: (Person) -> Unit,
    private val actionDelete: (Person) -> Unit
) : RecyclerView.Adapter<PeopleAdapter.PeopleHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_person, parent, false)
        return PeopleHolder(view)
    }

    override fun getItemCount() = people.size
    override fun onBindViewHolder(holder: PeopleHolder, position: Int) {
        holder.bind(
            people[position], actionEdit) {
            actionDelete(it)
            people.remove(it)
            notifyDataSetChanged()
        }
    }

    fun updatePeopleList(personList: List<Person>) {
        people.clear()
        people.addAll(personList)
        notifyDataSetChanged()
    }

    class PeopleHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(
            person: Person,
            actionEdit: (Person) -> Unit,
            actionDelete: (Person) -> Unit
        ) = itemView.apply {
            person.apply {
//                textViewDbId.text = "$id."
                textViewFirstName.text = firstName
                textViewLastName.text = lastName
                textViewAge.text = age.toString()
            }

            imageViewDelete.setOnClickListener {
                actionDelete(person)
            }

            imageViewEdit.setOnClickListener {
                actionEdit(person)
            }
        }
    }
}