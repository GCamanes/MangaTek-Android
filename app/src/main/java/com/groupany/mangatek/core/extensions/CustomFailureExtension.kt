package com.groupany.mangatek.core.extensions

import android.content.Context
import com.groupany.localization.R
import com.groupany.mangatek.core.domain.CustomFailure
import com.groupany.mangatek.core.domain.CustomFailure.InvalidCredential
import com.groupany.mangatek.core.domain.CustomFailure.NetworkError
import com.groupany.mangatek.core.domain.CustomFailure.TooManyRequests

fun CustomFailure.trad(context: Context): String {
    return when(this) {
        is InvalidCredential -> context.getString(R.string.error_credentials)
        is NetworkError -> context.getString(R.string.error_network)
        is TooManyRequests -> context.getString(R.string.error_too_many_request)
        else -> context.getString(R.string.error_unknown)
    }
}