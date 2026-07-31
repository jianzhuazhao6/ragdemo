package com.zjh.research.ragdemo.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnagramUtilTest {

    @Test
    public void testIsAnagram_TrueCases() {
        assertTrue(AnagramUtil.isAnagram("Listen", "Silent"));
        assertTrue(AnagramUtil.isAnagram("Listen", "Slient")); // As per issue description typo
        assertTrue(AnagramUtil.isAnagram("anagram", "nagaram"));
        assertTrue(AnagramUtil.isAnagram("", ""));
    }

    @Test
    public void testIsAnagram_FalseCases() {
        assertFalse(AnagramUtil.isAnagram("Health", "Earth"));
        assertFalse(AnagramUtil.isAnagram("hello", "world"));
        assertFalse(AnagramUtil.isAnagram("a", "b"));
        assertFalse(AnagramUtil.isAnagram("abc", "ab"));
    }

    @Test
    public void testIsAnagram_NullCases() {
        assertTrue(AnagramUtil.isAnagram(null, null));
        assertFalse(AnagramUtil.isAnagram("abc", null));
        assertFalse(AnagramUtil.isAnagram(null, "abc"));
    }
}
