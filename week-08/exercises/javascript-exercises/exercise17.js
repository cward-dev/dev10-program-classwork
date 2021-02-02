const assert = require("assert");
// MERGE AND REMOVE DUPLICATES
// Create a function named `mergeAndRemoveDuplicates`
// that accepts two arrays.
// Create a new array that contains elements from both
// arrays with duplicates removed. Duplicates should be removed if
// they occur in a single parameter array or if there's a duplicated element
// in each parameter.
// Return the result.

function mergeAndRemoveDuplicates(arrayOne, arrayTwo) {
    let combinedArray = [];
    combinedArray.push(arrayOne);
    combinedArray.push(arrayTwo);
    combinedArray = combinedArray.flat();
    
    let result = [];

    for (let i = 0; i < combinedArray.length; i++) {
        if (!result.includes(combinedArray[i])) {
            result.push(combinedArray[i]);
        }
    }
    return result;
}

// Execute this exercise.
// If you see the message "success!", all tests pass.

assert.deepStrictEqual(mergeAndRemoveDuplicates([1, 2], [2, 3]), [1, 2, 3]);
assert.deepStrictEqual(mergeAndRemoveDuplicates([1, 1, 2], [2, 2, 3]), [1, 2, 3]);
assert.deepStrictEqual(mergeAndRemoveDuplicates(["one", 2, true], [true, false, "two"]), ["one", 2, true, false, "two"]);
assert.deepStrictEqual(mergeAndRemoveDuplicates([], []), []);
assert.deepStrictEqual(mergeAndRemoveDuplicates(["red"], ["blue"]), ["red", "blue"]);
assert.deepStrictEqual(mergeAndRemoveDuplicates(["red", "green", "blue"], ["blue", "yellow"]), ["red", "green", "blue", "yellow"]);

console.log("success!");