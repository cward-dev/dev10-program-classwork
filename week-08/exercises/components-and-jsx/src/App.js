import React from 'react';
import Heading from './components/Heading';
import Numbers from './components/Numbers';
import Movies from './components/Movies';
import Movie from './components/Movie';

function App() {
  return (
    <div>
      <Heading text="Hello World" />
      <Heading text="Bye Suckers" />
      <Heading text="Hello Again" />
      <Heading text="I can keep doing this." />
      <Numbers minimum={5} maximum={17} />
      <Movies />
      <Movie title="Avengers: Endgame" releaseYear="2020" />
    </div>
  );
}

export default App;


// function App() {
//   return (
//     <div>
//       <h1>Ramping Up on React</h1>
//       <h2>Hello...</h2>
//       <div>
//         <Heading title="Hello World"/>
//       </div>
//     </div>
//   );
// }

// export default App;



// OLD CODE FROM Q&A
// import React, { useState } from 'react';
// import Person from './components/Person';

// const initialPeopleData = [
//   {
//     id: 1,
//     firstName: "Chris",
//     lastName: "Ward"
//   },
//   {
//     id: 2,
//     firstName: "Winston",
//     lastName: "Churchill"
//   },
//   {
//     id: 3,
//     firstName: "Sally",
//     lastName: "Jones"
//   }
// ];

// function App() {
//   // array destructuring
//   const [people, setPeople] = useState(initialPeopleData); // we pass in the default value

//   const handleClick = () => { 
//     // first, we want to create a copy of the array
//     const newPeople = [...people];

//     const newId = people.length + 1;

//     // now we can change the array
//     // TODO we can use faker to generate data
//     newPeople.push({
//       id: newId,
//       firstName: 'Ralph',
//       lastName: 'Lauren'
//     });

//     // we can finally update the people state by calling setPeople()
//     setPeople(newPeople);
//   };

//   return (
//     <div>
//       <h1>Ramping Up on React</h1>
//       <h2>Hello...</h2>
//       <div>
//         <button onClick={handleClick}>Add Person</button>
//         {people.map(person => (
//           <Person key={person.id} person={person}/>
//         ))}
//       </div>
//     </div>
//   );
// }

// export default App;