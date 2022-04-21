package com.example.tproomkotlin.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tproomkotlin.R
import com.example.tproomkotlin.database.PersonneEntity
import com.example.tproomkotlin.database.TestData
import com.example.tproomkotlin.databinding.RowBinding
import java.lang.Boolean
import java.lang.String
import kotlin.Int


class PersonAdapter(personList: List<PersonneEntity>, adapterListener: PersonAdapterListener) :
    RecyclerView.Adapter<PersonAdapter.ViewHolder>() {
    private val personList: List<PersonneEntity>
    private val adapterListener: PersonAdapterListener
    init {
        this.personList = personList
        this.adapterListener = adapterListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row, parent, false)
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {

            iconImageView.setImageResource(android.R.drawable.ic_menu_delete)
            val person: PersonneEntity = personList[position]
            idTextView.setText(String.valueOf(person.id))
            nomTextView.setText(person.nom)
            dateTextView.setText(TestData.formatter.format(person.date))
            iconImageView.setOnClickListener { view: View? ->
                // change to check image
                if (iconImageView.tag === Boolean.TRUE) {
                    iconImageView.setImageResource(android.R.drawable.radiobutton_off_background)
                    iconImageView.tag = Boolean.FALSE
                } else {
                    iconImageView.setImageResource(android.R.drawable.radiobutton_on_background)
                    iconImageView.tag = Boolean.TRUE
                }

                // notify to up level
                adapterListener.onClick(view, person)
            }
        }
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RowBinding.bind(itemView);
    }

    interface PersonAdapterListener {
        fun onClick(view: View?, person: PersonneEntity?)
    }


}
