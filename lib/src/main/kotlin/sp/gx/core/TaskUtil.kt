package sp.gx.core

import org.gradle.api.Project
import org.gradle.api.Task

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
