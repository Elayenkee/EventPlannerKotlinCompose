package fr.alemanflorian.eventplanner.team

class Team constructor(val uid:String, map:Map<String, Any>)
{
    val name:String
    val users:List<*>
    val events:List<*>

    init
    {
        name = map.get("name") as String
        users = if (map.containsKey("users")) (map.get("users") as List<*>) else listOf<String>()
        events = if (map.containsKey("events")) (map.get("events") as List<*>) else listOf<String>()
    }
}