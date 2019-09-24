package com.github.midros.istheapp.data.model

/**
 * Created by luis rafael on 27/03/18.
 */
class Recording {

    var nameAudio: String? = null
    var dateTime: String? = null
    var duration: String? = null

    constructor() {}

    constructor(nameAudio: String?, dateTime: String?, duration: String?) {
        this.nameAudio = nameAudio
        this.dateTime = dateTime
        this.duration = duration
    }

}