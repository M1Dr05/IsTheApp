package com.github.midros.istheapp.data.model

/**
 * Created by luis rafael on 28/03/18.
 */
class Sms {

    var smsAddress: String? = null
    var smsBody: String? = null
    var dateTime: String? = null
    var type:Int?=null

    constructor() {}

    constructor(smsAddress: String?, smsBody: String?, dateTime: String?,type:Int) {
        this.smsAddress = smsAddress
        this.smsBody = smsBody
        this.dateTime = dateTime
        this.type = type
    }

}