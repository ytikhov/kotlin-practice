package com.ytikhov

import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

enum class NodeType {
    NUMBER,
    STRING,
    BOOLEAN,
    OBJECT,
    ARRAY
}

data class TreeNode(
    val id: Int,
    val parent: Int?,
    val name: String,
    val type: NodeType,
    val defaultValue: String?,
    val sourcePath: String?
)

class Document {
    @JsonValue
    val elems: MutableMap<String, Any?> = linkedMapOf()
}


fun main(args: Array<String>) {
    val om = ObjectMapper().findAndRegisterModules()
    val source = """
        {"upperNode": {"vehicle": "Car", "mass": 1500}, "lastNode": "I'm a hero"}
    """.trimIndent()
    val sourceNode = om.readTree(source)

    val nodes = listOf(
        TreeNode(1, null, "number", NodeType.NUMBER, "-1", null),
        TreeNode(2, null, "nested object", NodeType.OBJECT, null, null),
        TreeNode(3, 2, "number2", NodeType.NUMBER, "2000", "upperNode/mass"),
        TreeNode(4, 2, "line", NodeType.STRING, "2000 as string", "lastNode"),
        TreeNode(5, 2, "nested object 2", NodeType.OBJECT, null, null),
        TreeNode(6, 5, "num_node", NodeType.NUMBER, null, null),
        TreeNode(7, null, "value", NodeType.STRING, "last node in root", "upperNode/vehicle")
    )

    val doc = Document()
    addNodes(nodes, null, sourceNode, doc.elems)

    val jsonString = om.writerWithDefaultPrettyPrinter().writeValueAsString(doc)
    println(jsonString)
}

fun addNodes(treeNodes: List<TreeNode>, parentId: Int?, source: JsonNode, node: MutableMap<String, Any?>) {
    treeNodes.filter { it.parent == parentId }
        .forEach {
            if (it.sourcePath.isNullOrBlank()) {
                when (it.type) {
                    NodeType.NUMBER -> node[it.name] = it.defaultValue?.toLong()
                    NodeType.STRING -> node[it.name] = it.defaultValue
                    NodeType.BOOLEAN -> node[it.name] = it.defaultValue?.toBoolean()
                    NodeType.OBJECT -> {
                        node[it.name] = linkedMapOf<String, Any?>()
                        addNodes(treeNodes, it.id, source, node[it.name] as MutableMap<String, Any?>)
                    }

                    NodeType.ARRAY -> TODO("ARRAYs not supported")
                }
            } else node[it.name] = findSourceValue(source, it.sourcePath)
        }
}

fun findSourceValue(source: JsonNode, path: String): Any? {
    val split = path.split("/")
    var purposeNode: JsonNode? = source
    for (s in split) {
        purposeNode = purposeNode?.findValue(s)
        if (purposeNode == null) {
            return null
        }
    }
    if (purposeNode!!.isNumber) return purposeNode.numberValue().toLong()
    else if (purposeNode.isTextual) return purposeNode.textValue()
    else if (purposeNode.isBoolean) return purposeNode.booleanValue()
    else return null
}
