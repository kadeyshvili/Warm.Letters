package com.example.myapplication.model

data class DefaultCard(
        override val id:Long,
        val imageResId: Int,
        override val text: String,
        override val isPlusNeeded: Boolean
): ICard
