package utils

class JsonUtil {
    companion object {
        fun fromClasspath(filename: String): String? {
            return this::class.java.classLoader.getResource(filename)?.readText()
        }
    }
}