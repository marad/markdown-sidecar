package io.github.marad.obsidianSidecar

import java.nio.file.Files
import java.nio.file.Path

class Inbox(private val inbox: Path) {
    init {
        ensureInboxIsMarkdownFile()
        ensureInboxIsNotAtRootLevel()
        makeInboxIfNotExists()
    }

    fun addNote(note: String) = appendItem(note)

    fun addUrl(url: String, comment: String? = null) =
        appendItem(if (comment != null) "$url - $comment" else url)

    private fun appendItem(item: String) {
        val source = String(Files.readAllBytes(inbox)).trim()
        Files.write(inbox, "$source\n- $item".toByteArray())
    }

    private fun ensureInboxIsMarkdownFile() {
        if(!inbox.endsWith(".md")) {
            throw RuntimeException("$inbox is not a Markdown file")
        }
    }
    private fun ensureInboxIsNotAtRootLevel() {
        if (Files.isDirectory(inbox.parent)) {
            throw RuntimeException("Invalid inbox location")
        }
    }

    private fun makeInboxIfNotExists() {
        if (Files.notExists(inbox)) {
            Files.createDirectories(inbox.parent)
            Files.createFile(inbox)
        }
    }

}
