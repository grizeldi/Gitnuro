package app.git

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import java.nio.file.*
import java.nio.file.StandardWatchEventKinds.*
import java.nio.file.attribute.BasicFileAttributes
import javax.inject.Inject


class FileChangesWatcher @Inject constructor() {
    suspend fun watchDirectoryPath(pathStr: String, ignoredDirsPath: List<String>) = flow {
        val watchService = FileSystems.getDefault().newWatchService()

        val path = Paths.get(pathStr)

        path.register(
            watchService,
            ENTRY_CREATE,
            ENTRY_DELETE,
            ENTRY_MODIFY
        )

        // register directory and sub-directories but ignore dirs by gitignore
        Files.walkFileTree(path, object : SimpleFileVisitor<Path>() {
            @Throws(IOException::class)
            override fun preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult {
                val isIgnoredDirectory = ignoredDirsPath.any { "$pathStr/$it" == dir.toString() }

                return if (!isIgnoredDirectory) {
                    dir.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY)
                    FileVisitResult.CONTINUE
                } else {
                    FileVisitResult.SKIP_SUBTREE
                }
            }
        })

        var key: WatchKey
        while (watchService.take().also { key = it } != null) {
            this.emit(Unit)
            key.pollEvents()
            key.reset()
        }
    }.flowOn(Dispatchers.IO)

}