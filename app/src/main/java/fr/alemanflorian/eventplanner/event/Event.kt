package fr.alemanflorian.eventplanner.event

import com.google.type.DateTime

class Event constructor(val uid:String, map:Map<String, Any>)
{
    val name:String
    val uidTeam:String?
    //val date:DateTime
    val oui:List<*>
    val non:List<*>
    val maybe:List<*>

    init
    {
        name = map.get("name") as String
        uidTeam = if (map.containsKey("uidTeam")) map.get("uidTeam") as String else null
        oui = if (map.containsKey("teams")) (map.get("teams") as List<*>) else listOf<String>()
        non = if (map.containsKey("non")) (map.get("non") as List<*>) else listOf<String>()
        maybe = if (map.containsKey("maybe")) (map.get("maybe") as List<*>) else listOf<String>()
    }
}