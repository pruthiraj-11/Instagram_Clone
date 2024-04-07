package com.app.instagramclone.Models

class Status{
    var profileimage:String?=null
    
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