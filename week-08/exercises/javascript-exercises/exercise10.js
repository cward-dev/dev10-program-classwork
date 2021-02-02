const assert = require("assert");

// FUNCTION RETURNS A FUNCTION
// Create a function named `makeFunction` that returns a function.
// Use an arrow function for the return value.

function makeFunction() {
    return (a => a + 1);
}

// Execute this exercise.
// If you see the message "success!", all tests pass.

assert.strictEqual(typeof makeFunction(), "function");
console.log("success!");