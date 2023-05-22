package sp.gx.core

import java.net.URI
import java.net.URL

object GitHub {
    fun pages(
        owner: String,
        name: String,
    ): URL {
        check(owner.isNotEmpty())
        check(name.isNotEmpty())
        return URI("https","$owner.github.io", null, null)
            .resolve(name)
            .toURL()
    }

    fun url(
        owner: String,
        name: String,
    ): URL {
        check(owner.isNotEmpty())
        check(name.isNotEmpty())
        return URI("https","github.com", null, null)
            .resolve(owner)
            .resolve(name)
            .toURL()
    }
}
