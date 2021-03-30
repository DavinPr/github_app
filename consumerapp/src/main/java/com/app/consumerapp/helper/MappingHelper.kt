package com.app.consumerapp.helper

import android.database.Cursor
import com.app.consumerapp.data.User

object MappingHelper {

    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<User> {
        val list = ArrayList<User>()
        cursor?.apply {
            while (moveToNext()) {
                val userName = getString(getColumnIndexOrThrow("username"))
                val name = getString(getColumnIndexOrThrow("name"))
                val avatar = getString(getColumnIndexOrThrow("avatar"))
                list.add(
                    User(username = userName, name = name, avatar = avatar)
                )
            }
        }
        return list
    }


}