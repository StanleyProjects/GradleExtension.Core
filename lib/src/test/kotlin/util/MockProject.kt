package util

import groovy.lang.Closure
import org.gradle.api.Action
import org.gradle.api.AntBuilder
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.NamedDomainObjectFactory
import org.gradle.api.PathValidation
import org.gradle.api.Project
import org.gradle.api.ProjectState
import org.gradle.api.Task
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.dsl.ArtifactHandler
import org.gradle.api.artifacts.dsl.DependencyFactory
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.DependencyLockingHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.component.SoftwareComponentContainer
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.api.file.CopySpec
import org.gradle.api.file.DeleteSpec
import org.gradle.api.file.FileTree
import org.gradle.api.file.ProjectLayout
import org.gradle.api.file.SyncSpec
import org.gradle.api.initialization.dsl.ScriptHandler
import org.gradle.api.invocation.Gradle
import org.gradle.api.logging.Logger
import org.gradle.api.logging.LoggingManager
import org.gradle.api.model.ObjectFactory
import org.gradle.api.plugins.Convention
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.ObjectConfigurationAction
import org.gradle.api.plugins.PluginContainer
import org.gradle.api.plugins.PluginManager
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory
import org.gradle.api.resources.ResourceHandler
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.WorkResult
import org.gradle.normalization.InputNormalizationHandler
import org.gradle.process.ExecResult
import org.gradle.process.ExecSpec
import org.gradle.process.JavaExecSpec
import java.io.File
import java.net.URI
import java.util.concurrent.Callable

internal class MockProject : Project {
    override fun compareTo(other: Project?): Int {
        TODO("compareTo")
    }

    override fun getExtensions(): ExtensionContainer {
        TODO("getExtensions")
    }

    override fun getPlugins(): PluginContainer {
        TODO("getPlugins")
    }

    override fun apply(closure: Closure<*>) {
        TODO("apply")
    }

    override fun apply(action: Action<in ObjectConfigurationAction>) {
        TODO("apply")
    }

    override fun apply(options: MutableMap<String, *>) {
        TODO("apply")
    }

    override fun getPluginManager(): PluginManager {
        TODO("getPluginManager")
    }

    override fun getRootProject(): Project {
        TODO("getRootProject")
    }

    override fun getRootDir(): File {
        TODO("getRootDir")
    }

    override fun getBuildDir(): File {
        TODO("getBuildDir")
    }

    override fun setBuildDir(path: File) {
        TODO("setBuildDir")
    }

    override fun setBuildDir(path: Any) {
        TODO("setBuildDir")
    }

    override fun getBuildFile(): File {
        TODO("getBuildFile")
    }

    override fun getParent(): Project? {
        TODO("getParent")
    }

    override fun getName(): String {
        TODO("getName")
    }

    override fun getDisplayName(): String {
        TODO("getDisplayName")
    }

    override fun getDescription(): String? {
        TODO("getDescription")
    }

    override fun setDescription(description: String?) {
        TODO("setDescription")
    }

    override fun getGroup(): Any {
        TODO("getGroup")
    }

    override fun setGroup(group: Any) {
        TODO("setGroup")
    }

    override fun getVersion(): Any {
        TODO("getVersion")
    }

    override fun setVersion(version: Any) {
        TODO("setVersion")
    }

    override fun getStatus(): Any {
        TODO("getStatus")
    }

    override fun setStatus(status: Any) {
        TODO("setStatus")
    }

    override fun getChildProjects(): MutableMap<String, Project> {
        TODO("getChildProjects")
    }

    override fun setProperty(name: String, value: Any?) {
        TODO("setProperty")
    }

    override fun getProject(): Project {
        TODO("getProject")
    }

    override fun getAllprojects(): MutableSet<Project> {
        TODO("getAllprojects")
    }

    override fun getSubprojects(): MutableSet<Project> {
        TODO("getSubprojects")
    }

    override fun task(name: String): Task {
        TODO("task")
    }

    override fun task(args: MutableMap<String, *>, name: String): Task {
        TODO("task")
    }

    override fun task(args: MutableMap<String, *>, name: String, configureClosure: Closure<*>): Task {
        TODO("task")
    }

    override fun task(name: String, configureClosure: Closure<*>): Task {
        TODO("task")
    }

    override fun task(name: String, configureAction: Action<in Task>): Task {
        TODO("task")
    }

    override fun getPath(): String {
        TODO("getPath")
    }

    override fun getDefaultTasks(): MutableList<String> {
        TODO("getDefaultTasks")
    }

    override fun setDefaultTasks(defaultTasks: MutableList<String>) {
        TODO("setDefaultTasks")
    }

    override fun defaultTasks(vararg defaultTasks: String?) {
        TODO("defaultTasks")
    }

    override fun evaluationDependsOn(path: String): Project {
        TODO("evaluationDependsOn")
    }

    override fun evaluationDependsOnChildren() {
        TODO("evaluationDependsOnChildren")
    }

    override fun findProject(path: String): Project? {
        TODO("findProject")
    }

    override fun project(path: String): Project {
        TODO("project")
    }

    override fun project(path: String, configureClosure: Closure<*>): Project {
        TODO("project")
    }

    override fun project(path: String, configureAction: Action<in Project>): Project {
        TODO("project")
    }

    override fun getAllTasks(recursive: Boolean): MutableMap<Project, MutableSet<Task>> {
        TODO("getAllTasks")
    }

    override fun getTasksByName(name: String, recursive: Boolean): MutableSet<Task> {
        TODO("getTasksByName")
    }

    override fun getProjectDir(): File {
        TODO("getProjectDir")
    }

    override fun file(path: Any): File {
        TODO("file")
    }

    override fun file(path: Any, validation: PathValidation): File {
        TODO("file")
    }

    override fun uri(path: Any): URI {
        TODO("uri")
    }

    override fun relativePath(path: Any): String {
        TODO("relativePath")
    }

    override fun files(vararg paths: Any?): ConfigurableFileCollection {
        TODO("files")
    }

    override fun files(paths: Any, configureClosure: Closure<*>): ConfigurableFileCollection {
        TODO("files")
    }

    override fun files(paths: Any, configureAction: Action<in ConfigurableFileCollection>): ConfigurableFileCollection {
        TODO("files")
    }

    override fun fileTree(baseDir: Any): ConfigurableFileTree {
        TODO("fileTree")
    }

    override fun fileTree(baseDir: Any, configureClosure: Closure<*>): ConfigurableFileTree {
        TODO("fileTree")
    }

    override fun fileTree(baseDir: Any, configureAction: Action<in ConfigurableFileTree>): ConfigurableFileTree {
        TODO("fileTree")
    }

    override fun fileTree(args: MutableMap<String, *>): ConfigurableFileTree {
        TODO("fileTree")
    }

    override fun zipTree(zipPath: Any): FileTree {
        TODO("zipTree")
    }

    override fun tarTree(tarPath: Any): FileTree {
        TODO("tarTree")
    }

    override fun <T : Any?> provider(value: Callable<T>): Provider<T> {
        TODO("provider")
    }

    override fun getProviders(): ProviderFactory {
        TODO("getProviders")
    }

    override fun getObjects(): ObjectFactory {
        TODO("getObjects")
    }

    override fun getLayout(): ProjectLayout {
        TODO("getLayout")
    }

    override fun mkdir(path: Any): File {
        TODO("mkdir")
    }

    override fun delete(vararg paths: Any?): Boolean {
        TODO("delete")
    }

    override fun delete(action: Action<in DeleteSpec>): WorkResult {
        TODO("delete")
    }

    override fun javaexec(closure: Closure<*>): ExecResult {
        TODO("javaexec")
    }

    override fun javaexec(action: Action<in JavaExecSpec>): ExecResult {
        TODO("javaexec")
    }

    override fun exec(closure: Closure<*>): ExecResult {
        TODO("exec")
    }

    override fun exec(action: Action<in ExecSpec>): ExecResult {
        TODO("exec")
    }

    override fun absoluteProjectPath(path: String): String {
        TODO("absoluteProjectPath")
    }

    override fun relativeProjectPath(path: String): String {
        TODO("relativeProjectPath")
    }

    override fun getAnt(): AntBuilder {
        TODO("getAnt")
    }

    override fun createAntBuilder(): AntBuilder {
        TODO("createAntBuilder")
    }

    override fun ant(configureClosure: Closure<*>): AntBuilder {
        TODO("ant")
    }

    override fun ant(configureAction: Action<in AntBuilder>): AntBuilder {
        TODO("ant")
    }

    override fun getConfigurations(): ConfigurationContainer {
        TODO("getConfigurations")
    }

    override fun configurations(configureClosure: Closure<*>) {
        TODO("configurations")
    }

    override fun getArtifacts(): ArtifactHandler {
        TODO("getArtifacts")
    }

    override fun artifacts(configureClosure: Closure<*>) {
        TODO("artifacts")
    }

    override fun artifacts(configureAction: Action<in ArtifactHandler>) {
        TODO("artifacts")
    }

    override fun getConvention(): Convention {
        TODO("getConvention")
    }

    override fun depthCompare(otherProject: Project): Int {
        TODO("depthCompare")
    }

    override fun getDepth(): Int {
        TODO("getDepth")
    }

    override fun getTasks(): TaskContainer {
        TODO("getTasks")
    }

    override fun subprojects(action: Action<in Project>) {
        TODO("subprojects")
    }

    override fun subprojects(configureClosure: Closure<*>) {
        TODO("subprojects")
    }

    override fun allprojects(action: Action<in Project>) {
        TODO("allprojects")
    }

    override fun allprojects(configureClosure: Closure<*>) {
        TODO("allprojects")
    }

    override fun beforeEvaluate(action: Action<in Project>) {
        TODO("beforeEvaluate")
    }

    override fun beforeEvaluate(closure: Closure<*>) {
        TODO("beforeEvaluate")
    }

    override fun afterEvaluate(action: Action<in Project>) {
        TODO("afterEvaluate")
    }

    override fun afterEvaluate(closure: Closure<*>) {
        TODO("afterEvaluate")
    }

    override fun hasProperty(propertyName: String): Boolean {
        TODO("hasProperty")
    }

    override fun getProperties(): MutableMap<String, *> {
        TODO("getProperties")
    }

    override fun property(propertyName: String): Any? {
        TODO("property")
    }

    override fun findProperty(propertyName: String): Any? {
        TODO("findProperty")
    }

    override fun getLogger(): Logger {
        TODO("getLogger")
    }

    override fun getGradle(): Gradle {
        TODO("getGradle")
    }

    override fun getLogging(): LoggingManager {
        TODO("getLogging")
    }

    override fun configure(`object`: Any, configureClosure: Closure<*>): Any {
        TODO("configure")
    }

    override fun configure(objects: MutableIterable<*>, configureClosure: Closure<*>): MutableIterable<*> {
        TODO("configure")
    }

    override fun <T : Any?> configure(objects: MutableIterable<T>, configureAction: Action<in T>): MutableIterable<T> {
        TODO("configure")
    }

    override fun getRepositories(): RepositoryHandler {
        TODO("getRepositories")
    }

    override fun repositories(configureClosure: Closure<*>) {
        TODO("repositories")
    }

    override fun getDependencies(): DependencyHandler {
        TODO("getDependencies")
    }

    override fun dependencies(configureClosure: Closure<*>) {
        TODO("dependencies")
    }

    override fun getDependencyFactory(): DependencyFactory {
        TODO("getDependencyFactory")
    }

    override fun getBuildscript(): ScriptHandler {
        TODO("getBuildscript")
    }

    override fun buildscript(configureClosure: Closure<*>) {
        TODO("buildscript")
    }

    override fun copy(closure: Closure<*>): WorkResult {
        TODO("copy")
    }

    override fun copy(action: Action<in CopySpec>): WorkResult {
        TODO("copy")
    }

    override fun copySpec(closure: Closure<*>): CopySpec {
        TODO("copySpec")
    }

    override fun copySpec(action: Action<in CopySpec>): CopySpec {
        TODO("copySpec")
    }

    override fun copySpec(): CopySpec {
        TODO("copySpec")
    }

    override fun sync(action: Action<in SyncSpec>): WorkResult {
        TODO("sync")
    }

    override fun getState(): ProjectState {
        TODO("getState")
    }

    override fun <T : Any?> container(type: Class<T>): NamedDomainObjectContainer<T> {
        TODO("container")
    }

    override fun <T : Any?> container(
        type: Class<T>,
        factory: NamedDomainObjectFactory<T>
    ): NamedDomainObjectContainer<T> {
        TODO("container")
    }

    override fun <T : Any?> container(type: Class<T>, factoryClosure: Closure<*>): NamedDomainObjectContainer<T> {
        TODO("container")
    }

    override fun getResources(): ResourceHandler {
        TODO("getResources")
    }

    override fun getComponents(): SoftwareComponentContainer {
        TODO("getComponents")
    }

    override fun getNormalization(): InputNormalizationHandler {
        TODO("getNormalization")
    }

    override fun normalization(configuration: Action<in InputNormalizationHandler>) {
        TODO("normalization")
    }

    override fun dependencyLocking(configuration: Action<in DependencyLockingHandler>) {
        TODO("dependencyLocking")
    }

    override fun getDependencyLocking(): DependencyLockingHandler {
        TODO("getDependencyLocking")
    }
}
