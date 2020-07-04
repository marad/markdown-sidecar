package io.github.marad.obsidianSidecar.app

import java.time.LocalDate

class Sidecar(private val configuration: Configuration) {
    fun openInbox() = Inbox(configuration.inboxPath)
    fun dailyNote(day: LocalDate = LocalDate.now()) = DailyNote(configuration.vaultPath, day)
}