package sp.gx.core

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.Directory

fun Project.buildDir(): Directory {
    return layout.buildDirectory.get()
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

private fun foo(project: Project) {
    val task = project.task<Task>("foo", "bar") {
        TODO()
    }
    TODO()
}
