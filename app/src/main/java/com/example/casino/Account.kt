package com.example.casino

class Account(name: String, email : String, pass: String, chipsBalance : String, saphireBalance : String,
 level: String, skins : String) {
    var name : String = ""
    var email : String = ""
    var pass : String = ""
    var chipsBalance : String = ""
    var saphireBalance : String = ""
    var level : String = ""
    var skins : String = ""

    init {
        this.name = name
        this.email = email
        this.pass = pass
        this.chipsBalance = chipsBalance
        this.saphireBalance = saphireBalance
        this.level = level
        this.skins = skins

    }
}