function AgentForAlias ({ agent, goBackToAgentMenu }) {
  return (
    <div>
      <div className="alert alert-secondary">
        <div className="row">
          <div className="col-8">
            <h3>Agent</h3>
          </div>
          <div className="col-4" align="right">
            <button className="btn btn-info col ml-2" onClick={goBackToAgentMenu}>Back To Agents Page</button>
          </div>
        </div>
      </div>
      <table className="table table-striped">
        <thead className="table-dark">
          <tr>
            <th className="first-name-item">First Name</th>
            <th className="middle-name-item">MI</th>
            <th className="last-name-item">Last Name</th>
            <th className="dob-item">Date of Birth</th>
            <th className="height-item">Height</th>
          </tr>
        </thead>
        <tbody>
          <tr key={agent.agentId}>
            <td className="first-name-item">{agent.firstName.substr(0,15)}</td>
            <td className="middle-name-item">{agent.middleName.charAt(0).toUpperCase()}</td>
            <td className="last-name-item">{agent.lastName.substr(0,15)}</td>
            <td className="dob-item">{agent.dob}</td>
            <td className="height-item">{agent.heightInInches}"</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}

export default AgentForAlias;