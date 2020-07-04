package io.github.marad.obsidianSidecar

import java.nio.file.Path
import java.nio.file.Paths
import java.time.DayOfWeek
import java.time.LocalDate

fun main(args: Array<String>) {
    val config = Configuration(Paths.get("C:\\sync\\pcloud\\obsidian"))
    val inbox = Inbox(config.inboxPath)
//    inbox.addUrl("http://blog.radoszewski.pl", "Mój blog")
//    inbox.addNote("Zrobić sobie coś do jedzenia")
    println(DayOfWeek.from(LocalDate.now()))
    val tomorrow = LocalDate.now().plusDays(1)
    DailyNote(config.vaultPath, tomorrow).create()
}

data class Configuration(
    val vaultPath: Path,
    val date: LocalDate = LocalDate.now()
) {
    private val inboxIcon = "\uD83D\uDCE7"
    val inboxPath = vaultPath.resolve("$inboxIcon Inbox.md")
}

