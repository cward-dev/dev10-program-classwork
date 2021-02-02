const assert = require("assert");
// PUSH THE REST
// 1. Create a function.
// Name: pushTheRest
// Parameters: any, any, ...rest parameter
// Returns: an array
// Description: Return the rest paramter.
// If no arguments are passed, do nothing but return.
// If one argument is passed, push it in the rest parameter before return.
// If two or more arguments are passed, push the first two args in the rest parameter
// and return.

function pushTheRest(anyOne, anyTwo, ...anyThree) {
    if (arguments.length === 0) {
        return [];
    }

    if (arguments.length === 1) {
        anyThree.push(anyOne);
        return anyThree;
    }

    anyThree.push(anyOne, anyTwo);
    return anyThree;
}

// Execute this exercise.
// If you see the message "success!", all tests pass.

assert.deepStrictEqual(pushTheRest(1, 2, 3, 4), [3, 4, 1, 2]);
assert.deepStrictEqual(pushTheRest("a", "b", "c", "d", "e"), ["c", "d", "e", "a", "b"]);
assert.deepStrictEqual(pushTheRest(true, false), [true, false]);
assert.deepStrictEqual(pushTheRest(23), [23]);
assert.deepStrictEqual(pushTheRest(), []);
assert.deepStrictEqual(pushTheRest({}, {}, {}), [{}, {}, {}]);

console.log("success!");