// DOUBLE IT

const phrase = "Eep";

// 1. Write a loop that "doubles" each character in a word.
// You'll need a new string variable to store the result.
// 2. Print the result.

// Examples
// ===============
// "dog" -> "ddoogg"
// "what?" -> "wwhhaatt??"
// "" -> "" (empty string has nothing to double)
// " " -> "  " (but whitespace should be doubled)
// "open & shut" -> "ooppeenn  &&  sshhuutt"
// "Eep" -> "EEeepp"

let result = "";

for (let i = 0; i < phrase.length; i++) {
    result += phrase.charAt(i) + phrase.charAt(i);
}

console.log(result);