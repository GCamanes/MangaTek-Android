package com.groupany.authentication.data.adapter

import com.google.firebase.auth.FirebaseUser
import com.groupany.authentication.domain.entities.UserEntity

object UserAdapter {
    fun toUserEntity(fbUser: FirebaseUser): UserEntity = UserEntity(
        email = fbUser.email!!,
    )
}