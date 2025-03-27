package com.groupany.mangatek.core.helpers

import com.groupany.mangatek.BuildConfig

object PropertyHelper {
    fun getEmailProperty() : String = BuildConfig.EMAIL
    fun getPasswordProperty() : String = BuildConfig.PASSWORD
}