package sp.gx.core

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskContainer

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

fun Project.task(
    nameSegment: String,
    secondNameSegment: String,
    vararg otherNameSegments: String,
    block: Action<in Task>,
): Task {
    val name = camelCase(camelCase(nameSegment, secondNameSegment), *otherNameSegments)
    return tasks.create(name, block)
}

fun TaskContainer.create(
    nameSegment: String,
    secondNameSegment: String,
    vararg otherNameSegments: String,
): Task {
    val name = camelCase(camelCase(nameSegment, secondNameSegment), *otherNameSegments)
    return create(name)
}

inline fun <reified T : Task> TaskContainer.getByName(
    nameSegment: String,
    secondNameSegment: String,
    vararg otherNameSegments: String,
    noinline block: T.() -> Unit,
): T {
    val name = camelCase(camelCase(nameSegment, secondNameSegment), *otherNameSegments)
    val task = getByName(name)
    check(task is T)
    task.block()
    return task
}

inline fun <reified T : Task> TaskContainer.getByName(
    nameSegment: String,
    secondNameSegment: String,
    vararg otherNameSegments: String,
): T {
    val name = camelCase(camelCase(nameSegment, secondNameSegment), *otherNameSegments)
    val task = getByName(name)
    check(task is T)
    return task
}
