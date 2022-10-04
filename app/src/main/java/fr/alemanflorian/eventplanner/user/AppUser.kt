package fr.alemanflorian.eventplanner.user

import android.content.Context
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.alemanflorian.eventplanner.utils.Utils
import kotlinx.coroutines.tasks.await

class AppUser constructor(val uid:String, map:Map<String, Any>)
{
    val name:String
    val teams:List<*>
    val events:List<*>

    init
    {
        name = map.get("name") as String
        teams = if (map.containsKey("teams")) (map.get("teams") as List<*>) else listOf<String>()
        events = if (map.containsKey("events")) (map.get("events") as List<*>) else listOf<String>()
    }

    companion object
    {
        var _instance: AppUser? = null
        fun set(user: AppUser){
            _instance = user;
            save();
        }

        fun save()
        {
            if(_instance != null)
            {
                val preferences = Utils.context.getSharedPreferences("EventPlanner", Context.MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putString("uidUser", _instance!!.uid);
                editor.apply()
                editor.commit()
            }
        }

        fun get():AppUser
        {
            if(_instance == null)
            {
                _instance = AppUser("7eaQk2BW5HVUGyyeEhh5Drauwur1", buildMap {
                    put("name", "Florian A")
                    put("events", listOf("vAfx7aKkEgpAkY6igq86"))
                    put("events", listOf("LXr30tHgcP0o1owM6kWq"))
                })
            }
            return _instance!!;
        }

        fun isLogged():Boolean {
            return _instance != null
        }

        suspend fun load():AppUser?
        {
            val preferences = Utils.context.getSharedPreferences("EventPlanner", Context.MODE_PRIVATE)
            var uid = preferences.getString("uidUser", null)
            if(uid == null || uid!!.length < 3)
                return null;

            val docUser = Firebase.firestore.collection("users").document(uid)
            val doc = docUser.get().await()
            val user = AppUser(doc.id, doc.data as (Map<String, Any>))
            set(user)
            return _instance;
        }
    }


}