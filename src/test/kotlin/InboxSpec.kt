import com.google.common.jimfs.Configuration
import com.google.common.jimfs.Jimfs
import io.github.marad.obsidianSidecar.app.Inbox
import org.junit.Test
import java.nio.file.FileSystem
import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.BeforeTest
import kotlin.test.expect

class InboxSpec {
    private lateinit var fs: FileSystem
    private lateinit var inboxPath: Path

    @BeforeTest
    fun setup() {
        fs = Jimfs.newFileSystem(Configuration.unix())
        inboxPath = fs.getPath("/vault/inbox.md")
        Files.createDirectories(inboxPath.parent)
    }

    @Test
    fun `should append url without comment`() {
        Files.write(inboxPath, "- existing".toByteArray())
        Inbox(inboxPath).addUrl("http://github.com")
        expect("- existing\n- http://github.com") {
            String(Files.readAllBytes(inboxPath))
        }
    }

    @Test
    fun `should append url with comment`() {
        Files.write(inboxPath, "- existing".toByteArray())
        Inbox(inboxPath).addUrl("http://github.com", "Fun VCS")
        expect("- existing\n- http://github.com - Fun VCS") {
            String(Files.readAllBytes(inboxPath))
        }
    }

    @Test
    fun `should append note`() {
        Files.write(inboxPath, "- existing".toByteArray())
        Inbox(inboxPath).addNote("Some note")
        expect("- existing\n- Some note") {
            String(Files.readAllBytes(inboxPath))
        }
    }
}