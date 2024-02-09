package com.greenart7c3.citrine

data class NoticeResult(val message: String) {
    fun toJson(): String {
        return """["NOTICE","$message"]"""
    }

    companion object {
        fun invalid(message: String) = NoticeResult("invalid: $message")
    }
}
