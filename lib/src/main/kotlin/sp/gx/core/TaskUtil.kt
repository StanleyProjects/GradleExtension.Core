package sp.gx.core

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskContainer

/**
 * Creates a [Task] with the given name segments in camel case and [T] type,
 * configures it with the given [block], and adds it to [Project.getTasks].
 *
 * Usage:
 * ```
 * val project: Project = ...
 * val task = project.task<DefaultTask>("foo", "bar") {
 *  // ...
 * }
 * assertEquals("fooBar", task.name)
 * ```
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.5.4
 * @see Project.task
 * @see TaskContainer.create
 */
inline fun <reified T : Task> Project.task(
    nameSegment: String,
    secondNameSegment: String,
    vararg otherNameSegments: String,
    noinline block: T.() -> Unit,
): T {
    val name = camelCase(camelCase(nameSegment, secondNameSegment), *otherNameSegments)
    return tasks.create(name, T::class.java, block)
}

/**
 * Creates a [Task] with the given name segments in camel case and [T] type, and adds it to [Project.getTasks].
 *
 * Usage:
 * ```
 * val project: Project = ...
 * val task = project.task<DefaultTask>("foo", "bar")
 * assertEquals("fooBar", task.name)
 * ```
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.5.4
 * @see Project.task
 * @see TaskContainer.create
 */
inline fun <reified T : Task> Project.task(
    nameSegment: String,
    secondNameSegment: String,
    vararg otherNameSegments: String,
): T {
    val name = camelCase(camelCase(nameSegment, secondNameSegment), *otherNameSegments)
    return tasks.create(name, T::class.java)
}

/**
 * Creates a [Task] with the given name segments in camel case,
 * configures it with the given [block], and adds it to [this].
 *
 * Usage:
 * ```
 * val project: Project = ...
 * val task = project.tasks.create("foo", "bar") {
 *  // ...
 * }
 * assertEquals("fooBar", task.name)
 * ```
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.5.4
 * @see TaskContainer.create
 */
fun TaskContainer.create(
    nameSegment: String,
    secondNameSegment: String,
    vararg otherNameSegments: String,
    block: Action<in Task>,
): Task {
    val name = camelCase(camelCase(nameSegment, secondNameSegment), *otherNameSegments)
    return create(name, block)
}

/**
 * Creates a [Task] with the given name segments in camel case, and adds it to [this].
 *
 * Usage:
 * ```
 * val project: Project = ...
 * val task = project.tasks.create("foo", "bar")
 * assertEquals("fooBar", task.name)
 * ```
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.5.4
 * @see TaskContainer.create
 */
fun TaskContainer.create(
    nameSegment: String,
    secondNameSegment: String,
    vararg otherNameSegments: String,
): Task {
    val name = camelCase(camelCase(nameSegment, secondNameSegment), *otherNameSegments)
    return create(name)
}

/**
 * Locates a [Task] by name segments in camel case,
 * configures it with the given [block], failing if there is no such object.
 *
 * Usage:
 * ```
 * val project: Project = ...
 * val task = project.tasks.getByName("foo", "bar") {
 *  // ...
 * }
 * assertEquals("fooBar", task.name)
 * ```
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.5.4
 * @see TaskContainer.getByName
 */
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

/**
 * Locates a [Task] by name segments in camel case, failing if there is no such object.
 *
 * Usage:
 * ```
 * val project: Project = ...
 * val task = project.tasks.getByName("foo", "bar")
 * assertEquals("fooBar", task.name)
 * ```
 * @author [Stanley Wintergreen](https://github.com/kepocnhh)
 * @since 0.5.4
 * @see TaskContainer.getByName
 */
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
