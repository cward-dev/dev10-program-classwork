// PRINT BUSINESS CARD
// Create a function named `printBusinessCard` that accepts an object.
// The object may contain the following properties:
// jobTitle, firstName, and lastName, pithyPhrase
/* Print a business card in the following format:

  Rue Majors
  Architect
  "It's better to burn out than it is to fade away."

Generically:

  firstName lastName
  jobTitle
  "pithyPhrase"
*/
// If any property is missing, omit it. If a missing property creates an empty line, omit the line.
// Hint: object destructing is useful, but not required, here.

function printBusinessCard(object) {
  let result = ``;

  if (object.firstName !== undefined && object.lastName !== undefined) {
    result += `\n${object.firstName} ${object.lastName}`;
  } else if (object.firstName !== undefined) {
    result += `\n${object.firstName}`;
  } else if (object.lastName !== undefined) {
    result += `\n${object.lastName}`;
  }

  if (object.jobTitle !== undefined) {
    result += `\n${object.jobTitle}`
  }

  if (object.pithyPhrase !== undefined) {
    result += `\n${object.pithyPhrase}`
  }

  console.log(result);
}


printBusinessCard({
    jobTitle: "Architect",
    firstName: "Rue",
    lastName: "Majors",
    pithyPhrase: "It's better to burn out than it is to fade away."
});
/* Expected output:

  Rue Majors
  Architect
  "It's better to burn out than it is to fade away."
*/

printBusinessCard({
    firstName: "Mac",
    lastName: "Gorrie"
});
/* Expected output:

  Mac Gorrie
*/

printBusinessCard({
    jobTitle: "Instructor",
    firstName: "Netta",
    pithyPhrase: "Happy to help!"
});
/* Expected output:

  Netta
  Instructor
  "Happy to help!"
*/

printBusinessCard({
    jobTitle: "Mystery Guest",
    pithyPhrase: "Life is an illusion..."
});

/* Expected output:

  Mystery Guest
  "Life is an illusion..."
*/