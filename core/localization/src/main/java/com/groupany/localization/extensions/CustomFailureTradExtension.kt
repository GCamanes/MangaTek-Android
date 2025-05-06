package com.groupany.localization.extensions

import android.content.Context
import com.groupany.localization.R
import com.groupany.base.CustomFailure
import com.groupany.base.CustomFailure.InvalidCredential
import com.groupany.base.CustomFailure.NetworkError
import com.groupany.base.CustomFailure.TooManyRequests

fun CustomFailure.trad(context: Context): String {
    return when(this) {
        is InvalidCredential -> context.getString(R.string.error_credentials)
        is NetworkError -> context.getString(R.string.error_network)
        is TooManyRequests -> context.getString(R.string.error_too_many_request)
        else -> context.getString(R.string.error_unknown)
    }
}