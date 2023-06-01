package com.example.aisle.data.model

data class Profile(
    var approved_time: Double? = 0.0,
    var disapproved_time: Double? = 0.0,
    var general_information: GeneralInformation? = GeneralInformation(),
    var has_active_subscription: Boolean? = false,
    var icebreakers: Any? = Any(),
    var instagram_images: Any? = Any(),
    var is_facebook_data_fetched: Boolean? = false,
    var last_seen: Any? = Any(),
    var last_seen_window: String? = "",
    var lat: Double? = 0.0,
    var lng: Double? = 0.0,
    var meetup: Any? = Any(),
    var online_code: Int? = 0,
    var photos: List<Photo>? = listOf(),
    var preferences: List<Preference>? = listOf(),
    var profile_data_list: List<ProfileData>? = listOf(),
    var show_concierge_badge: Boolean? = false,
    var story: Any? = Any(),
    var user_interests: List<Any>? = listOf(),
    var verification_status: String? = "",
    var work: Work? = Work()
)