package com.gongjun.MarkdownOfStudy.RomanumberToInt;

class SolutionC {
    public int romanToInt(String s) {
        int n = 0;
        for (int i = 0; i < s.length();) {
            char c = s.charAt(i);
            if (c == 'I') {
                if (i + 1 < s.length()) {
                    if (s.charAt(i + 1) == 'V') {
                        n += 4;
                        i += 2;
                    } else if (s.charAt(i + 1) == 'X') {
                        n += 9;
                        i += 2;
                    } else {
                        n += 1;
                        i++;
                    }
                } else {
                    n += 1;
                    i++;
                }
            } else if (c == 'X') {
                if (i + 1 < s.length()) {
                    if (s.charAt(i + 1) == 'L') {
                        n += 40;
                        i += 2;
                    } else if (s.charAt(i + 1) == 'C') {
                        n += 90;
                        i += 2;
                    } else {
                        n += 10;
                        i++;
                    }
                } else {
                    n += 10;
                    i++;
                }
            } else if (c == 'C') {
                if (i + 1 < s.length()) {
                    if (s.charAt(i + 1) == 'D') {
                        n += 400;
                        i += 2;
                    } else if (s.charAt(i + 1) == 'M') {
                        n += 900;
                        i += 2;
                    } else {
                        n += 100;
                        i++;
                    }
                } else {
                    n += 100;
                    i++;
                }
            } else if (c == 'V') {
                n += 5;
                i++;
            } else if (c == 'L') {
                n += 50;
                i++;
            } else if (c == 'D') {
                n += 500;
                i++;
            } else if (c == 'M') {
                n += 1000;
                i++;
            }
        }
        return n;
    }
}