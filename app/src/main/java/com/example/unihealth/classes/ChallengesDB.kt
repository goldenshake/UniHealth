package com.example.unihealth.classes



data class ChallengesDB(
    val challengeTitle: List<String> = emptyList(),
    val emptyChallenges: Boolean,
    val email: String
) {
    constructor() : this(emptyList(),true, "")

}