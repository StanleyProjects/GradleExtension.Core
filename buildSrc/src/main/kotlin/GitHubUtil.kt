object GitHubUtil {
    fun pages(
        owner: String,
        name: String,
        postfix: String,
    ): String {
        check(postfix.isNotEmpty())
        return "https://${owner}.github.io/${name}/$postfix"
    }
}
