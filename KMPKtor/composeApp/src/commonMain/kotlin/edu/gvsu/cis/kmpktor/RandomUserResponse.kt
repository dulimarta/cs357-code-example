package edu.gvsu.cis.kmpktor

import kotlinx.serialization.Serializable

@Serializable
data class RandomUserResponse(var results: List<RandomUser>)

@Serializable
data class RandomUser(val name: UserName, val email: String,
    val dob: UserDateOfBirth, val picture: UserPicture)

@Serializable
data class UserPicture(val large: String,
    val medium: String,
    val thumbnail: String)

@Serializable
data class UserName(val title: String, val first: String, val last:String)


@Serializable
data class UserDateOfBirth(val date: String, val age: Int)
