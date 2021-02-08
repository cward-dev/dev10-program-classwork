function Agent( { agent, setEditAgentId, setDeleteAgentId, handleAliases, handleCancel } ) {

  const { agentId, firstName, middleName, lastName, dob, heightInInches} = agent;

  const handleEdit = () => {
    handleCancel();
    setEditAgentId(agentId);
  }

  const handleDelete = () => {
    handleCancel();
    setDeleteAgentId(agentId);
  }

  const handleAliasesClick = () => {
    handleAliases(agentId);
  }

  return (
    <tr key={agentId}>
      <td className="first-name-item">{firstName.substr(0,15)}</td>
      <td className="middle-name-item">{middleName.charAt(0).toUpperCase()}</td>
      <td className="last-name-item">{lastName.substr(0,15)}</td>
      <td className="dob-item">{dob}</td>
      <td className="height-item">{heightInInches}"</td>
      <td className="actions-agent-item">
        <div className="row pr-2">
          <button className="col-3 btn btn-info" onClick={handleEdit}>Edit</button>
          <button className="col-4 btn btn-danger ml-2" onClick={handleDelete}>Delete</button>
          <button className="col-4 btn btn-secondary ml-2" onClick={handleAliasesClick}>Aliases</button>
        </div>

      </td>
    </tr>
  );
}

export default Agent;