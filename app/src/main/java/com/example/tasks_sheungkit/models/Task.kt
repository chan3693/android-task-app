package com.example.tasks_sheungkit.models

class Task {
    var name:String
    var isPriority:Boolean

    constructor(name:String, isPriority:Boolean){
        this.name = name
        this.isPriority = isPriority
    }

    override fun toString(): String {
        return "Tasks(name=$name, isPriority=$isPriority)"
    }
}