package com.github.midros.child.data.model

/**
 * Created by luis rafael on 13/03/18.
 */
class Location {

    var latitude: Double? = null
    var longitude: Double? = null
    var address: String? = null
    var dateTime: String? = null

    constructor() {}

    constructor(latitude: Double, longitude: Double, address: String, dateTime: String) {
        this.latitude = latitude
        this.longitude = longitude
        this.address = address
        this.dateTime = dateTime
    }
}