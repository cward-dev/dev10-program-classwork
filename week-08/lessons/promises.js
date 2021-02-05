// function delay(action) {
//   return new Promise((resolve) => {
//       setTimeout(() => {
//           action();
//           resolve();
//       }, Math.random() * 1500);
//   });
// }




// // synchronous wait
// let now = new Date();
// delay(() => console.log(`Fetch A milliseconds: ${new Date() - now}.`))
//   .then(() => delay(() => console.log(`Fetch B milliseconds: ${new Date() - now}.`)))
//   .then(() => delay(() => console.log(`Fetch C milliseconds: ${new Date() - now}.`)))
//   .then(() => console.log(`Sync total milliseconds: ${new Date() - now}`));




// // asynchronous wait
// let start = new Date();
// const fetchA = delay(() => console.log(`Fetch A milliseconds: ${new Date() - start}.`));
// const fetchB = delay(() => console.log(`Fetch B milliseconds: ${new Date() - start}.`));
// const fetchC = delay(() => console.log(`Fetch C milliseconds: ${new Date() - start}.`));




// // Promise.all returns a promise and resolves 
// // after all promise arguments have resolved asynchronously.
// Promise.all([fetchA, fetchB, fetchC])
//   .then(() => console.log(`Async total milliseconds: ${new Date() - start}`));




// async function go() {
//   return `The current time is: ${new Date()}`;
// }




// let p1 = go();

// // confirm that p1 is a promise.
// console.log(p1.constructor);
// // [Function: Promise]

// // use promise methods like `then`
// p1.then(console.log);
// // The current time is: Fri Jan 29 2021 06:54:58 GMT-0600 (Central Standard Time)




// function delayedValue(func) {
//   return new Promise((resolve) => {
//       setTimeout(() => resolve(func()), Math.random() * 1500);
//   });
// }

// async function go() {
//   return delayedValue(
//       () => `The current time is: ${new Date()}`);
// }

// go().then(console.log);




// function delayedValue(func) {
//   return new Promise((resolve) => {
//       setTimeout(() => resolve(func()), Math.random() * 1500);
//   });
// }

// async function go() {

//   // await a promise and get its value.
//   const currentTime = await delayedValue(
//       () => `The current time is: ${new Date()}`);

//   // In a non-async function, this statement would
//   // occur before the promise resolves or rejects.
//   // But here the `await` keyword suspends the function
//   // until a value is returned.
//   console.log(currentTime);
// }

// go();




// function delayedValue(func) {
//   return new Promise((resolve) => {
//       setTimeout(() => resolve(func()), Math.random() * 1500);
//   });
// }

// async function delayedTime() {
//   return delayedValue(
//       () => `The current time is: ${new Date()}`);
// }

// async function go() {
//   // synchronous dependencies
//   // Notice that logging occurs one step at a time.
//   // There's a slight pause between each step.
//   let timeMessage = await delayedTime();
//   console.log(timeMessage);
//   timeMessage = await delayedTime();
//   console.log(timeMessage);
//   timeMessage = await delayedTime();
//   console.log(timeMessage);
//   timeMessage = await delayedTime();
//   console.log(timeMessage);
// }

// go();




function delayedValue(func) {
  return new Promise((resolve) => {
      setTimeout(() => resolve(func()), Math.random() * 1500);
  });
}

async function delayedTime() {
  return delayedValue(
      () => `The current time is: ${new Date()}`);
}

async function go() {

  // p1-p4 are promises.
  // They've already started their work.
  // They're working asynchronously behind the scenes.
  const p1 = delayedTime();
  const p2 = delayedTime();
  const p3 = delayedTime();
  const p4 = delayedTime();

  // Notice that logging occurs largely all at once
  // because p1-p4 don't depend on each other.
  // As the promises resolve, their values are logged.
  // The total time required is the longest operation,
  // not the sum of operations.
  console.log(await p1);
  console.log(await p2);
  console.log(await p3);
  console.log(await p4);
}

go();