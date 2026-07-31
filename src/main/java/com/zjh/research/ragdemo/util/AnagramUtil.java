package com.zjh.research.ragdemo.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for Anagram related operations.
 */
public class AnagramUtil {

    /**
     * Identifies if two strings are anagrams.
     * Time complexity: O(N) where N is the length of the strings.
     * Space complexity: O(1) if character set is fixed (e.g., ASCII) or O(K) where K is number of unique characters.
     *
     * @param s1 First string
     * @param s2 Second string
     * @return true if they are anagrams, false otherwise
     */
    public static boolean isAnagram(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return s1 == s2;
        }

        if (s1.length() != s2.length()) {
            return false;
        }

        // Using a frequency map to achieve O(N) time complexity without sorting
        // Case-insensitive comparison as per "Listen" and "Slient" example (Wait, "Slient" has 'i' and 'e' switched, but it is an anagram of "Listen" if we consider 'L', 'i', 's', 't', 'e', 'n')
        // Actually "Listen" and "Silent" are standard anagram examples. The prompt says "Slient" which might be a typo for "Silent".
        // "Listen" (L-i-s-t-e-n) and "Slient" (S-l-i-e-n-t) have the same characters if case-insensitive.
        
        String str1 = s1.toLowerCase();
        String str2 = s2.toLowerCase();

        Map<Character, Integer> charCounts = new HashMap<>();

        for (int i = 0; i < str1.length(); i++) {
            char c = str1.charAt(i);
            charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < str2.length(); i++) {
            char c = str2.charAt(i);
            if (!charCounts.containsKey(c)) {
                return false;
            }
            int count = charCounts.get(c);
            if (count == 1) {
                charCounts.remove(c);
            } else {
                charCounts.put(c, count - 1);
            }
        }

        return charCounts.isEmpty();
    }
}
