import Faker from 'faker';
import Person from './Person';
import AddPerson from './AddPerson';

function People({addPerson, clearTheRoom, people = []}) {
  const handleAddRandomPersonClick = () => {
    addPerson({
      firstName: Faker.name.firstName(), 
      middleName: Faker.name.middleName(), 
      lastName: Faker.name.lastName()});
  }

  const clearRoom = () => {
    clearTheRoom();
  }

  if (people.length > 10) {
    return (
      <div>
        <div>maximum gathering size exceeded...go home.</div>
        <button className="btn btn-danger" onClick={clearRoom}>Clear the Room</button>
      </div>
    );
  }

  return (
    <div>
      <AddPerson addPerson={addPerson} />

      {people.length > 0 ? (
        <table className="table table-striped table-dark mt-5">
          <thead>
            <tr>
              <th scope="col">First Name</th>
              <th scope="col">Middle Name</th>
              <th scope="col">Last Name</th>
              <th scope="col">&nbsp;</th>
            </tr>
          </thead>
          <tbody>
            {people.map(person => <Person key={person.id} person={person} />)}
          </tbody>
        </table>
      ) : null}

      <button className="btn btn-outline-primary" onClick={handleAddRandomPersonClick}>Add Random Person</button>
    </div>
    
  );
}

export default People;