import './Agent.css';

function Agent( { agent, setEditAgentId, deleteById } ) {

  const { agentId, firstName, middleName, lastName, dob, heightInInches} = agent;

  const handleEdit = () => {
    setEditAgentId(agentId);
  }

  const handleDelete = () => {
    deleteById(agentId);
  }

  return (
    <tr key={agentId}>
      <td>{agentId}</td>
      <td>{firstName}</td>
      <td>{middleName}</td>
      <td>{lastName}</td>
      <td>{dob}</td>
      <td>{heightInInches}</td>
      <td>
        <div>
          <button className="btn btn-success" onClick={handleEdit}>Edit</button>
          <button className="btn btn-danger" onClick={handleDelete}>Delete</button>
        </div>

      </td>
    </tr>
  );
}

export default Agent;