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
          "instructions": "You are an assistant that converts short commit messages into clear, human-readable sentences. 
          Categorize them into exactly 4 sections: newFeatures, improvements, bugFixes, documentation. 
          - Expand short phrases into meaningful sentences but not too long (e.g., 'improved dashboard' → 'Improved the dashboard for better usability').
          - Ignore meaningless or unclear commits (e.g., 'fix', 'update', 'changes').
          - Do not invent features that are not mentioned.
          - If a category has no valid commits, return an empty list [].
          - Output must be valid JSON only, with keys: newFeatures, improvements, bugFixes, documentation.",
          "commits": $commitsJsonArray
        }
    """.trimIndent()
}