// ITEM BETWEEN ARRAYS
// Create a function that accepts an array, any other value, and an array.
// Return a new array that includes all elements from the first array, the value, then
// all the elements from the last array parameter.
// Hint: spread syntax is useful here.

function combinedArray(arrayOne, valueOne, arrayTwo) {
    return [...arrayOne, valueOne, ...arrayTwo];
}

const resultOne = combinedArray([1, 2], "a", [true]);
const resultTwo = combinedArray([], 87, [9]);
const resultThree = combinedArray([14, 15], 0, []);
const resultFour = combinedArray(["red", "blue"], "orange", ["yellow"]);
const resultFive = combinedArray(["red"], ["orange"], ["yellow"]);

/* Examples
[1, 2], "a", [true]                   => [1, 2, "a", true]
[], 87, [9]                           => [87, 9]
[14, 15], 0, []                       => [14, 15, 0]
["red", "blue"], "orange", ["yellow"] => ["red", "blue", "orange", "yellow"]
["red"], ["orange"], ["yellow"]       => ["red", ["orange"], "yellow"]
*/

// Confirm your result by debugging or printing to the console.

console.log(resultOne);
console.log(resultTwo);
console.log(resultThree);
console.log(resultFour);
console.log(resultFive);