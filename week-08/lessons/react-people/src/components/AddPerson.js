import { useState } from 'react';

const DEFAULT_PERSON = {
  firstName: '',
  middleName: '',
  lastName: ''
};

function AddPerson({addPerson}) {
  const [person, setPerson] = useState(DEFAULT_PERSON);
  // const [firstName, setFirstName] = useState('');
  // const [middleName, setMiddleName] = useState('');
  // const [lastName, setLastName] = useState('');

  // Controlled Components

  // Option #1: define explicit event handler function
  // Option #2: define a handler inline within the JSX
  // Option #3: define general purpose onchange handler

  // const firstNameOnChangeHandler = (event) => {
  //   console.log(event.target.value);
  //   setFirstName(event.target.value);
  // };

  const onChangeHandler = (event) => {
    console.log(event.target.name);
    console.log(event.target.value);

    // we should never modify state directly
    const updatedPerson = {...person};

    // updatedPerson.firstName
    // updatedPerson.middleName
    // updatedPerson.lastName
    updatedPerson[event.target.name] = event.target.value;

    setPerson(updatedPerson);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    addPerson({...person});

    setPerson(DEFAULT_PERSON);
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="mb-3">
        <label htmlFor="firstName" className="form-label">First Name:</label>
        <input id="firstName" name="firstName" type="text" className="form-control" 
          onChange={onChangeHandler}
          value={person.firstName} />          
      </div>
      <div className="mb-3">
        <label htmlFor="middleName" className="form-label">Middle Name:</label>
        <input id="middleName" name="middleName" type="text" className="form-control"
          onChange={onChangeHandler}
          value={person.middleName} />          
      </div>
      <div className="mb-3">
        <label htmlFor="lastName" className="form-label">Last Name:</label>
        <input id="lastName" name="lastName" type="text" className="form-control" 
          onChange={onChangeHandler}
          value={person.lastName} />          
      </div>
      <button className="btn btn-success" type="submit">Add Person</button>
    </form>
  );
}

export default AddPerson;
