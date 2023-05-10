# GradleExtension.Core
A few Gradle extensions.

---

## Snapshot

![version](https://img.shields.io/static/v1?label=version&message=0.1.1-SNAPSHOT&labelColor=212121&color=2962ff&style=flat)

- [Maven](https://s01.oss.sonatype.org/content/repositories/snapshots/com/github/kepocnhh/GradleExtension.Core/0.1.1-SNAPSHOT)
- [Documentation](https://StanleyProjects.github.io/GradleExtension.Core/doc/0.1.1-SNAPSHOT)

### Build
```
$ gradle lib:assembleSnapshotJar
```

### Import
```kotlin
repositories {
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation("com.github.kepocnhh:GradleExtension.Core:0.1.1-SNAPSHOT")
}
```

---
