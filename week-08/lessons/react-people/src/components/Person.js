
function Person({ person }) {
  const { firstName, middleName, lastName } = person;

  return (
    <tr>
      <td>{firstName}</td>
      <td>{middleName}</td>
      <td>{lastName}</td>
      <td>
        <button className="btn btn-primary btn-sm">Edit</button>
        <button className="btn btn-danger btn-sm ms-2">Delete</button>
      </td>
    </tr>        
  );
}

export default Person;
