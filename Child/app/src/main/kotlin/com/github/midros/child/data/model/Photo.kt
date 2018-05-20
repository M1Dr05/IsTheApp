package com.github.midros.child.data.model

/**
 * Created by luis rafael on 13/03/18.
 */
class Photo {

    var nameRandom: String? = null
    var dateTime: String? = null
    var urlPhoto: String? = null

    constructor() {}

    constructor(nameRandom: String?, dateTime: String?, urlPhoto: String?) {
        this.nameRandom = nameRandom
        this.dateTime = dateTime
        this.urlPhoto = urlPhoto
    }

}