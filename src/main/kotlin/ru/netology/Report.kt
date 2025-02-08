package ru.netology

enum class ReportReason(raw: UInt) {
    SPAM(0u),
    VIOLENCE(1u),
    DRUGS(2u),
    ADULT(3u)
}

class Report(
    val commentId: UInt,
    val reason: ReportReason?
)

class ReasonIsNotProvided(message: String) : Exception(message)
