package io.github.marad.obsidianSidecar.app

import java.nio.file.Path
import java.time.LocalDate

data class Configuration(
    val vaultPath: Path,
    val date: LocalDate = LocalDate.now()
) {
    private val inboxIcon = "\uD83D\uDCE7"
    val inboxPath = vaultPath.resolve("$inboxIcon Inbox.md")
}