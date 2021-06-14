package com.rhyme.modiriathesab
import java.io.Serializable


data class Transaction(val username: String, val title: String, val amount: Int): Serializable
