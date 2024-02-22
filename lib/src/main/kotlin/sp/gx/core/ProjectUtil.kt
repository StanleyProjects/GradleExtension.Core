package sp.gx.core

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileCollection
import org.gradle.api.file.ProjectLayout
import org.gradle.api.file.RegularFile
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Provider
import java.io.File

fun Project.buildDir(): Directory {
    return layout.buildDirectory.get()
}

fun ProjectLayout.buildDir(): Directory {
    return buildDirectory.get()
}

fun ProjectLayout.dir(path: String): Directory {
    return projectDirectory.dir(path)
}

private class DelegatedProjectLayout(
    private val objectFactory: ObjectFactory,
    private val projectDirectory: Directory,
) : ProjectLayout {
    override fun getProjectDirectory(): Directory {
        return projectDirectory
    }

    override fun getBuildDirectory(): DirectoryProperty {
        return objectFactory.directoryProperty().value(projectDirectory.dir("build"))
    }

    override fun file(file: Provider<File>): Provider<RegularFile> {
        return projectDirectory.file(file.map { it.absolutePath })
    }

    override fun dir(file: Provider<File>): Provider<Directory> {
        return projectDirectory.dir(file.map { it.absolutePath })
    }

    override fun files(vararg paths: Any?): FileCollection {
        return projectDirectory.files(*paths)
    }
}

val Project.buildSrc: ProjectLayout get() {
    return DelegatedProjectLayout(
        objectFactory = rootProject.objects,
        projectDirectory = rootProject.layout.projectDirectory.dir("buildSrc"),
    )
}

inline fun <reified T : Task> Project.task(
    nameSegment: String,
    secondNameSegment: String,
    vararg otherNameSegments: String,
    noinline block: T.() -> Unit,
): T {
    val name = camelCase(camelCase(nameSegment, secondNameSegment), *otherNameSegments)
    return tasks.create(name, T::class.java, block)
}

inline fun <reified T : Task> Project.task(
    nameSegment: String,
    secondNameSegment: String,
    vararg otherNameSegments: String,
): T {
    val name = camelCase(camelCase(nameSegment, secondNameSegment), *otherNameSegments)
    return tasks.create(name, T::class.java)
}
