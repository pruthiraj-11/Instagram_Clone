package com.app.instagramclone.models

class User{
    var profileimage:String?=null
    var name:String?=null
    var email:String?=null
    var password:String?=null
    constructor()
    constructor(profileimage:String?,name:String?,email:String?,password:String?){
        this.profileimage=profileimage
        this.name=name
        this.email=email
        this.password=password
    }
    constructor(name:String?,email:String?,password:String?){
        this.name=name
        this.email=email
        this.password=password
    }
}
