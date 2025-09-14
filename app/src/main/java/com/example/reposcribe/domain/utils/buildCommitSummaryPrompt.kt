package com.example.reposcribe.domain.utils

fun builtCommitSummaryPrompt(
    projectName: String,
    startDate: String,
    endDate: String,
    commits: List<String>
): String {
    val commitsJsonArray = commits.joinToString(
        separator = ",",
        prefix = "[",
        postfix = "]"
    ) { "\"$it\"" }

    return """
        {
          "task": "summarize_commits",
          "project": "$projectName",
          "time_range": "$startDate to $endDate",
          "instructions": "Categorize commit messages into 4 sections: New Features, Improvements, Bug Fixes, and Documentation. 
          Return JSON with keys: newFeatures, improvements, bugFixes, documentation. 
          Each key should contain a list of short bullet points (strings). 
          If a category has no commits, return an empty list for that key. 
          Do not include extra text outside JSON.",
          "commits": $commitsJsonArray
        }
    """.trimIndent()
}