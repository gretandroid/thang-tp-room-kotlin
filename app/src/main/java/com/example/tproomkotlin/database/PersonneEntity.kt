package com.example.tproomkotlin.database


import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "person")
class PersonneEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var date: Date? = null,
    var nom: String? = null,
)
{
    constructor(date: Date?, nom: String?) : this(0, date, nom)

    override fun toString(): String {
        return this.javaClass.simpleName + "{" +
                "id=" + id +
                ", date=" + date +
                ", text='" + nom + '\'' +
                '}'
    }
}
