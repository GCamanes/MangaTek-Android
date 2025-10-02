package com.groupany.base.usecases

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetAppVersionUseCase @Inject constructor(@param:ApplicationContext private val context: Context) :
    NoParamUseCase<String>() {
    override suspend fun invoke(): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName.toString()
        } catch (_: Exception) {
            "Unknown"
        }
    }
}