package com.app.githubmobile.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.app.coremodule.domain.usecase.AppUseCase
import org.koin.android.ext.android.inject

class FavoriteProvider : ContentProvider() {

    private val useCase: AppUseCase by inject()

    private val contentUri: Uri = Uri.Builder().scheme("content")
        .authority("com.app.githubmobile")
        .appendPath("favorite")
        .build()

    companion object {
        private const val AUTHORITY = "com.app.githubmobile"
        private const val TABLE_NAME = "favorite"


        private const val FAVORITE = 1
        private const val FAVORITE_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(
                AUTHORITY,
                TABLE_NAME,
                FAVORITE
            )
            sUriMatcher.addURI(
                AUTHORITY,
                "$TABLE_NAME/#",
                FAVORITE_ID
            )
        }
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            FAVORITE -> useCase.getAllFavoriteCursor()
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {

        useCase.deleteFavoriteByUsername(uri.lastPathSegment.toString())


//        val deleted = when (FAVORITE_ID) {
//            sUriMatcher.match(uri) -> {
//                useCase.deleteFavoriteByUsername(uri.lastPathSegment.toString())
//                1
//            }
//            else -> {
//                useCase.deleteFavoriteByUsername(uri.lastPathSegment.toString())
//                0
//            }
//        }

        context?.contentResolver?.notifyChange(contentUri, null)

        return 1
    }
}