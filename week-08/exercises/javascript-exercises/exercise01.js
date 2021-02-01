// MANAGER FEATURE REQUESTS
// You have three managers: A, B, and C.
// Occasionally, they ask you to add features to your company software.
// Use if/else statements to enforce the following rules:
// - If all three ask for the feature, print "Feature in progress."
// - If any two of the three ask, print "Adding feature to schedule."
// - If only one of the three ask, print "Going to hold off for a bit."
// - If none of the managers ask, print "Nothing to do..."

let managerAAsked = true;
let managerBAsked = true;
let managerCAsked = true;

// 1. Add decisions statements to cover all scenarios.

function getStatus() {
    let asked = [managerAAsked, managerBAsked, managerCAsked];
    let count = 0;
    asked.forEach(a => {if (a === true) {count++;}});

    switch (count) {
        case 0:
            console.log("Nothing to do...");
            break;
        case 1:
            console.log("Going to hold off for a bit.");
            break;
        case 2:
            console.log("Adding feature to schedule.");
            break;
        case 3:
            console.log("Feature in progress.");
            break;
        default:
            console.log("Not recognized.");
            break;           
    }
}

// 2. Change manager variables to test all scenarios.

getStatus();

managerAAsked = false;
getStatus();

managerBAsked = false;
getStatus();

managerCAsked = false;
getStatus();