package sp.gx.core

import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.ProjectLayout

fun Project.buildDir(): Directory {
    return layout.buildDirectory.get()
}

fun ProjectLayout.buildDir(): Directory {
    return buildDirectory.get()
}

fun ProjectLayout.dir(path: String): Directory {
    return projectDirectory.dir(path)
}

val Project.buildSrc: ProjectLayout get() {
    return DelegatedProjectLayout(
        objectFactory = rootProject.objects,
        projectDirectory = rootProject.layout.projectDirectory.dir("buildSrc"),
    )
}
