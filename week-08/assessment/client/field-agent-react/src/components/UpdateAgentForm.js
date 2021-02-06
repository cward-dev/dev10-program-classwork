import { useState } from 'react';
import './AgentForm.css'

const DEFAULT_AGENT = {
  firstName: '',
  middleName: '',
  lastName: '',
  dob: null,
  heightInInches: 0
};

function UpdateAgentForm( { getAgentToEdit, handleEdit } ) {

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

  return (
    <form onSubmit={handleSubmit}>
      <h3>Update Agent</h3>
      <div className="form-group">
        <div className="row col-10 form-row">
          <div className="col">
            <label htmlFor="firstNameInput" className="form-label" >First Name</label>
            <input className="form-control" type="text" id="firstName" name="firstName" value={agent.firstName} onChange={handleChange}></input>
          </div>
          <div className="col">
            <label htmlFor="firmiddleNameInputtNameInput" className="form-label" >Middle Name</label>
            <input className="form-control" type="text" id="middleName" name="middleName" value={agent.middleName} onChange={handleChange}></input>
          </div>
          <div className="col">
            <label htmlFor="lastNameInput" className="form-label" >Last Name</label>
            <input className="form-control" type="text" id="lastName" name="lastName" value={agent.lastName} onChange={handleChange}></input>
          </div>
        </div>
        <div className="row col-10 form-row">
          <div className="col">
            <label htmlFor="dobInput" className="form-label">Date of Birth</label>
            <input className="form-control" type="date" id="dob" name="dob" value={agent.dob} onChange={handleChange}></input>
          </div>
          <div className="col">
            <label htmlFor="heightInput" className="form-label">Height (Inches)</label>
            <input className="form-control" type="number" id="heightInInches" name="heightInInches" value={agent.heightInInches} onChange={handleChange}></input>
          </div>
        </div>
        <div className="row col-10 form-row">
          <div className="col">
            <button className="btn btn-success col" type="submit" id="submit-add-agent">Update Agent</button>
          </div>
        </div>

      </div>
    </form>
  );
}

export default UpdateAgentForm;