function printArray(stringArray) {
    let maxLength = 0;
    for (const element of stringArray) {
        maxLength = Math.max(maxLength, element.length);
    }

    stringArray.forEach(s => {console.log(s.padStart(maxLength))});
}

let testArray = [ "merciful", "cold", "beyond reproach", "brighter", "sad"];

printArray(testArray);