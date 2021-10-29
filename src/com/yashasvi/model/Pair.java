package com.yashasvi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pair<K, V> {
    K key;
    V value;
}
