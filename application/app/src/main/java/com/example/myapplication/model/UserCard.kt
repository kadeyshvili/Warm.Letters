package com.example.myapplication.model

data class UserCard (
        override val id: Long,
        val cardFilePath:String,
        override val text: String,
        override val isPlusNeeded: Boolean
): ICard
