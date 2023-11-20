interface DependencyGroup {
    val groupId: String? get() = null
    val version: String? get() = null

    fun dependency(name: String, groupId: String? = this.groupId, version: String? = this.version): String {
        requireNotNull(groupId)
        requireNotNull(version)

        return "$groupId:$name:$version"
    }
}

object Kotlin {
    const val version = "1.8.21"
}

object AvroPlugin {
    const val id = "com.github.davidmc24.gradle.plugin.avro"
    const val version = "1.9.1"
}

object Junit: DependencyGroup {
    override val version = "5.9.3"
    override val groupId = "org.junit.jupiter"

    val api = dependency("junit-jupiter-api")
    val engine = dependency("junit-jupiter-engine")
    val params = dependency("junit-jupiter-params")
}

object Avro: DependencyGroup {
    override val groupId = "org.apache.avro"
    override val version = "1.11.0"

    val avro = dependency("avro")
}

object Hamcrest: DependencyGroup {
    override val groupId = "org.hamcrest"
    override val version = "2.2"

    val hamcrest = dependency("hamcrest")
}


object Sulky: DependencyGroup {
    override val groupId = "de.huxhorn.sulky"
    override val version = "8.2.0"

    val ulid = dependency("de.huxhorn.sulky.ulid")
}
