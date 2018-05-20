package com.github.midros.child.data.model

/**
 * Created by usuario on 27/03/18.
 */
class Calls {

    var contact: String? = null
    var phoneNumber: String? = null
    var dateTime: String? = null
    var duration: String? = null

    constructor() {}

    constructor(contact: String?, phoneNumber: String?, dateTime: String?, duration: String?) {
        this.contact = contact
        this.phoneNumber = phoneNumber
        this.dateTime = dateTime
        this.duration = duration
    }

}