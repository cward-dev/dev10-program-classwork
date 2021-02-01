const assert = require("assert");

// FIRST VOWEL
// Complete the function `getFirstVowel`.
// It should return the first vowel in a string.
// If the string doesn't contain vowels, `value` is null, 
// or `value` is undefined, return an empty string.

function getFirstVowel(value) {
    let vowels = ["a", "e", "i", "o","u"];

    if (value === undefined) {
        return "";
    }

    for (let i = 0; i < value.lenth; i++) {
        switch (value.charAt(i)) {
            
        }
        if(vowels.includes(value.charAt(i).toLowerCase())) {
            return value.charAt(i);
        }
    }
    return null;
}

// Node's assert library will test your function.
// Execute this exercise.
// If you see the message "success!", all tests pass.

assert.strictEqual(getFirstVowel("magnificent"), "a");
assert.strictEqual(getFirstVowel("winsome"), "i");
assert.strictEqual(getFirstVowel("xxx"), "");
assert.strictEqual(getFirstVowel(), "");
assert.strictEqual(getFirstVowel("mAgnificent"), "A");

console.log("success!");