package io.github.marad.obsidianSidecar

import io.github.marad.obsidianSidecar.app.Configuration
import io.github.marad.obsidianSidecar.app.Sidecar
import io.github.marad.obsidianSidecar.server.Server
import java.nio.file.Paths

fun main() {
    val config = Configuration(Paths.get("D:\\sync\\pcloud\\obsidian"))
    val sidecar = Sidecar(config)
    sidecar.dailyNote().create()
    Server(sidecar).start()
}


