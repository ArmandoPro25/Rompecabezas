package armando.dev.rompecabezas

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform