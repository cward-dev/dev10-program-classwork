import { useState } from 'react';
import './AgentForm.css'

function UpdateAgentForm( { getAgentToEdit, handleEdit, handleCancel } ) {

  const agentToEdit = getAgentToEdit();

  const [agent, setAgent] = useState({...agentToEdit});

  const handleChange = (event) => {
    const updatedAgent = {...agent};
    updatedAgent[event.target.name] = event.target.value;
    setAgent(updatedAgent);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    handleEdit(agent);

    document.getElementById("firstName").value = '';
    document.getElementById("middleName").value = '';
    document.getElementById("lastName").value = '';
    document.getElementById("dob").value = '';
    document.getElementById("heightInInches").value = '';
  };

  const handleEditCancel = () => {
    handleCancel();

    document.getElementById("firstName").value = '';
    document.getElementById("middleName").value = '';
    document.getElementById("lastName").value = '';
    document.getElementById("dob").value = '';
    document.getElementById("heightInInches").value = '';
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="alert alert-secondary">
        <h3>Update Agent</h3>
      </div>
      <div>
          <div className="form-row">
            <label htmlFor="firstNameInput" className="col-4" align="right">*First Name</label>
            <input className="col form-control" type="text" maxLength="50" required id="firstName" name="firstName" value={agent.firstName} onChange={handleChange}></input>
          </div>
          <div className="form-row">
            <label htmlFor="firmiddleNameInputtNameInput" className="col-4" align="right">Middle Name</label>
            <input className="col form-control" type="text" maxLength="50" id="middleName" name="middleName" value={agent.middleName ? agent.middleName : ''} onChange={handleChange}></input>
          </div>
          <div className="form-row">
            <label htmlFor="lastNameInput" className="col-4" align="right">*Last Name</label>
            <input className="col form-control" type="text" maxLength="50" required id="lastName" name="lastName" value={agent.lastName} onChange={handleChange}></input>
          </div>
          <div className="form-row">
            <label htmlFor="dobInput" className="col-4" align="right">*Date of Birth</label>
            <input className="col form-control" type="date" required id="dob" name="dob" value={agent.dob} onChange={handleChange}></input>
          </div>
          <div className="form-row">
            <label htmlFor="heightInput" className="col-4" align="right">*Height in Inches</label>
            <input className="col form-control" type="number" required min="36" max="96" id="heightInInches" name="heightInInches" value={agent.heightInInches} onChange={handleChange}></input>
          </div>
        <div className="form-row">
          <div className="col">
            <button className="btn btn-info col" type="submit" id="submit-agent">Update Agent</button>
          </div>
          <div className="col">
            <button className="btn btn-warning col" type="button" id="cancel-agent" onClick={handleEditCancel}>Cancel</button>
          </div>
        </div>
      </div>
    </form>
  );
}

export default UpdateAgentForm;