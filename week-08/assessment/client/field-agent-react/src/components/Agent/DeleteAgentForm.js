import { useState } from 'react';
import './AgentForm.css'

function DeleteAgentForm( { getAgentToDelete, handleDelete, handleCancel } ) {

  const agentToDelete = getAgentToDelete();

  const [agent, setAgent] = useState({...agentToDelete});

  const handleSubmit = (event) => {
    event.preventDefault();
    handleDelete(agent.agentId);

    document.getElementById("firstName").value = '';
    document.getElementById("middleName").value = '';
    document.getElementById("lastName").value = '';
    document.getElementById("dob").value = '';
    document.getElementById("heightInInches").value = '';
  };

  const handleDeleteCancel = () => {
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
        <h3>Delete Agent</h3>
      </div>
      <div>
          <div className="form-row">
            <label htmlFor="firstNameInput" className="col-4" align="right" >First Name</label>
            <input className="col form-control" type="text" id="firstName" name="firstName" readOnly value={agent.firstName}></input>
          </div>
          <div className="form-row">
            <label htmlFor="firmiddleNameInputtNameInput" className="col-4" align="right">Middle Name</label>
            <input className="col form-control" type="text" id="middleName" name="middleName" readOnly value={agent.middleName ? agent.middleName : ''}></input>
          </div>
          <div className="form-row">
            <label htmlFor="lastNameInput" className="col-4" align="right">Last Name</label>
            <input className="col form-control" type="text" id="lastName" name="lastName" readOnly value={agent.lastName}></input>
          </div>
          <div className="form-row">
            <label htmlFor="dobInput" className="col-4" align="right">Date of Birth</label>
            <input className="col form-control" type="date" id="dob" name="dob" readOnly value={agent.dob}></input>
          </div>
          <div className="form-row">
            <label htmlFor="heightInput" className="col-4" align="right">Height in Inches</label>
            <input className="col form-control" type="number" id="heightInInches" name="heightInInches" readOnly value={agent.heightInInches}></input>
          </div>
        <div className="form-row">
          <div className="col">
            <button className="btn btn-danger col" type="submit" id="submit-agent">Delete Agent</button>
          </div>
          <div className="col">
            <button className="btn btn-warning col" type="button" id="cancel-agent" onClick={handleDeleteCancel}>Cancel</button>
          </div>
        </div>
      </div>
    </form>
  );
}

export default DeleteAgentForm;