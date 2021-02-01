// INTERLEAVE
const first = "wonder";
const second = "o";

// 1. Write a loop to interleave two strings to form a new string.
// To interleave, during each loop take one character from the first string and add it to the result
// and take one character from the second string and add it to the result.
// If there are no more characters available, don't add characters.
// 2. Print the result.

// Examples
// "abc", "123" -> "a1b2c3"
// "cat", "dog" -> "cdaotg"
// "wonder", "o" -> "woonder"
// "B", "igstar" -> "Bigstar"
// "", "huh?" -> "huh?"
// "wha?", "" -> "wha?"

let result = "";

for (let i = 0; i < Math.max(first.length, second.length); i++) {
    if (first.length >= i + 1) {
        result += first.charAt(i);
    }
    if (second.length >= i + 1) {
        result += second.charAt(i);
    }
}

console.log(result);