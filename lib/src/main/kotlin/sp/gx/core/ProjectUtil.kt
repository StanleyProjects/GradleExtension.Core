package sp.gx.core

import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.ProjectLayout

/**
 * Usage:
 * ```
 * val project: Project = ...
 * val dir = project.buildDir()
 * val file = dir.asFile
 * if (file.exists()) {
 *  assertTrue(file.isDirectory)
 * }
 * ```
 * @return the build [Directory] for the project.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.5.4
 * @see [ProjectLayout.getBuildDirectory]
 */
fun Project.buildDir(): Directory {
    return layout.buildDirectory.get()
}

/**
 * Usage:
 * ```
 * val project: Project = ...
 * val dir = project.layout.buildDir()
 * val file = dir.asFile
 * if (file.exists()) {
 *  assertTrue(file.isDirectory)
 * }
 * ```
 * @return the build [Directory] for the project.
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.5.4
 * @see [ProjectLayout.getBuildDirectory]
 */
fun ProjectLayout.buildDir(): Directory {
    return buildDirectory.get()
}

/**
 * Usage:
 * ```
 * val project: Project = ...
 * val dir = project.layout.dir("foo")
 * val file = dir.asFile
 * if (file.exists()) {
 *  assertTrue(file.isDirectory)
 * }
 * ```
 * @return a [Directory] whose location is the given path, resolved relative to [ProjectLayout.getProjectDirectory].
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.5.4
 * @see [ProjectLayout.getProjectDirectory]
 */
fun ProjectLayout.dir(path: String): Directory {
    return projectDirectory.dir(path)
}

/**
 * Provides access to various important directories for "buildSrc" directory of [Project].
 *
 * Usage:
 * ```
 * val project: Project = ...
 * val dir = project.buildSrc.projectDirectory
 * val file = dir.asFile
 * if (file.exists()) {
 *  assertTrue(file.isDirectory)
 *  assertEquals("buildSrc", file.name)
 * }
 * ```
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.5.4
 */
val Project.buildSrc: ProjectLayout get() {
    return DelegatedProjectLayout(
        objectFactory = rootProject.objects,
        projectDirectory = rootProject.layout.projectDirectory.dir("buildSrc"),
    )
}
