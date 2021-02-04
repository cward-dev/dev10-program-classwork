function Person( { person } ) {
  const { firstName, middleName, lastName } = person;

  return (
    <div>
      <ul>
      <li>{firstName}</li>
      {middleName ? 
        (<li>{middleName}</li>) : null}
      <li>{lastName}</li>
      </ul>
    </div>
  );
}

export default Person;