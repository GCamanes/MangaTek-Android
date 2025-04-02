package com.groupany.mangatek.features.auth.data.adapter

import com.google.firebase.auth.FirebaseUser
import com.groupany.mangatek.features.auth.domain.entities.UserEntity

object UserAdapter {
    fun toUserEntity(fbUser: FirebaseUser): UserEntity = UserEntity(
        email = fbUser.email!!,
    )
}