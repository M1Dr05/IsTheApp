package com.github.midros.istheapp.data.model

/**
 * Created by luis rafael on 28/03/18.
 */
class Sms {

    var smsAddress: String? = null
    var smsBody: String? = null
    var dateTime: String? = null

    constructor() {}

    constructor(smsAddress: String?, smsBody: String?, dateTime: String?) {
        this.smsAddress = smsAddress
        this.smsBody = smsBody
        this.dateTime = dateTime
    }

}