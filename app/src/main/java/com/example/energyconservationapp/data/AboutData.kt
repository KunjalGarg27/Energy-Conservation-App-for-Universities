package com.example.energyconservationapp.data

data class TeamMember(
    val name: String,
    val rollNo: String,
    val contribution: String
)

object AboutData {
    // Automatically mapped from your team's work division
    val teamMembers = listOf(
        TeamMember("Medha", "Roll No: 01", "Dashboard, Navigation & Final Integration"),
        TeamMember("Kunjal", "Roll No: 02", "Energy Consumption Calculator"),
        TeamMember("Ayushna", "Roll No: 03", "Energy Conservation Tips Hub"),
        TeamMember("Charu", "Roll No: 04", "Green Quiz & Badge System"),
        TeamMember("Lovedeep", "Roll No: 05", "Impact Page, About Us & Share Feature")
    )

    // Environmental impact statistics
    val impactStats = listOf(
        "Turning off unnecessary lights can save up to 400 kWh per year.",
        "Using LED bulbs reduces energy consumption by 75%.",
        "Unplugging idle electronics can save you money and reduce phantom load.",
        "Global energy demand is projected to rise by 47% by 2050."
    )
}
