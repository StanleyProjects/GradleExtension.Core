package sp.gx.core.util

import org.junit.jupiter.api.Assertions
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList

internal fun NodeList.single(): Node {
    Assertions.assertTrue(length == 1)
    return item(0)
}

internal fun Element.single(tag: String): Node {
    return getElementsByTagName(tag).single()
}

internal fun Node.element(): Element {
    Assertions.assertEquals(Node.ELEMENT_NODE, nodeType)
    check(this is Element)
    return this
}
