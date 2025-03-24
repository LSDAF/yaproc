package com.lsadf.yaproc.file;

import java.util.HashMap;
import java.util.Map;

/**
 * A specialized extension of {@link HashMap} with {@link String} keys and {@link Object} values.
 * ContentMap is designed to act as a generic data structure for storing content mappings,
 * enabling flexibility in managing key-value pairs where the values can be of any Object type.
 *
 * This class can be used in scenarios requiring dynamic and adaptable content handling, such as
 * storing structured or unstructured data, supporting operations that rely on flexible
 * content representation.
 *
 * Key Characteristics:
 * - Inherits all behavior of {@link HashMap} for storing and managing key-value pairs.
 * - Keys are of type {@link String}, making it suitable for textual identifiers.
 * - Values are of type {@link Object}, allowing arbitrary data storage.
 */
public class ContentMap extends HashMap<String, Object> implements Map<String, Object> {}
