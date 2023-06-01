package com.example.aisle.data.model

data class PhoneNumberApiRequest(
    var number: String =""
){
    inner class Builder {

        var number: String? = null

        fun number(number: String) = apply { this.number = number }

        fun build() = PhoneNumberApiRequest(
            number = number ?: ""
        )

    }
}
