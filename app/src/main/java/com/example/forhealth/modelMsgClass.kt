package com.example.forhealth

class ModelMsgClass (msg: String?){

    private var msg: String

    init {
        this.msg = msg!!
    }
    fun getMsg(): String? {
        return msg
    }
    fun setMsg(name: String?) {
        this.msg = msg!!
    }

}