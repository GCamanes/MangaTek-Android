package com.groupany.mangatek.features.login.data.adapter

import com.google.firebase.auth.FirebaseUser
import com.groupany.mangatek.features.login.domain.entities.UserEntity

object UserAdapter {
    fun toUserEntity(fbUser: FirebaseUser): UserEntity = UserEntity(
        email = fbUser.email!!,
    )
}