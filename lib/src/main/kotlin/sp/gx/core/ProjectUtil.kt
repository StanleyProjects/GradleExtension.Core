package sp.gx.core

import org.gradle.api.Project
import org.gradle.api.file.Directory

fun Project.buildDir(): Directory {
    return layout.buildDirectory.get()
}
