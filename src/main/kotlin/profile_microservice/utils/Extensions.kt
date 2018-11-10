package profile_microservice.utils

fun <T> MutableCollection<T>.addNotNull(element: T?) {
    if (element != null) {
        this.add(element)
    }
}