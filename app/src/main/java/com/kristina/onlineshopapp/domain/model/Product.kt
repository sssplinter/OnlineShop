package com.kristina.onlineshopapp.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Product(
    val id: Long,
    val category: String,
    val title: String,
    val price: Double,
    val image: String,
    val description: String,
    val favourite: Boolean = false,
    val comment: String = ""
) : Parcelable