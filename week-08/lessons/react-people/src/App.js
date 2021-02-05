import React, { useState } from 'react';
import People from './components/People';

function App() {
  // array destructuring
  const [people, setPeople] = useState([]); // we pass in the default value

  const addPerson = function (person) {
    person.id = Date.now();
    const newPeople = [...people];
    newPeople.push(person);
    setPeople(newPeople);
  }

  const clearTheRoom = function () {
    setPeople([]);
  }

  return (
    <div>
      <h1 className="my-4">Ramping Up on React!</h1>
      <div className="alert alert-warning" role="alert">
        There are {people.length} people in this list.
      </div>      
      <div>       
        <People addPerson={addPerson} clearTheRoom={clearTheRoom} people={people} />
      </div>
    </div>
  );
}

export default App;