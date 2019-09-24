package com.github.midros.istheapp.data.model

/**
 * Created by luis rafael on 18/03/18.
 */
class KeyData {

    var keyID: String? = null
    var keyText: String? = null

    constructor()

    constructor(keyId: String, keyText: String) {
        this.keyID = keyId
        this.keyText = keyText
    }

}