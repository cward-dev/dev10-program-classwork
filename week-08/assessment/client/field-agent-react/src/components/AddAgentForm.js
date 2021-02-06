import { useState } from 'react';
import './AgentForm.css';

const DEFAULT_AGENT = {
  firstName: '',
  middleName: '',
  lastName: '',
  dob: null,
  heightInInches: 0
};

function AddAgentForm( { addAgent } ) {

  const [agent, setAgent] = useState(DEFAULT_AGENT);

  const handleChange = (event) => {
    const updatedAgent = {...agent};
    updatedAgent[event.target.name] = event.target.value;
    setAgent(updatedAgent);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    addAgent(agent);

    setAgent(DEFAULT_AGENT);
    document.getElementById("firstName").value = '';
    document.getElementById("middleName").value = '';
    document.getElementById("lastName").value = '';
    document.getElementById("dob").value = '';
    document.getElementById("heightInInches").value = '';
  };

  return (
    <form onSubmit={handleSubmit}>
      <h3>Add Agent</h3>
      <div className="form-group">
        <div className="row col-10 form-row">
          <div className="col">
            <label htmlFor="firstNameInput" className="form-label" >First Name</label>
            <input className="form-control" type="text" id="firstName" name="firstName" onChange={handleChange}></input>
          </div>
          <div className="col">
            <label htmlFor="firmiddleNameInputtNameInput" className="form-label" >Middle Name</label>
            <input className="form-control" type="text" id="middleName" name="middleName" onChange={handleChange}></input>
          </div>
          <div className="col">
            <label htmlFor="lastNameInput" className="form-label" >Last Name</label>
            <input className="form-control" type="text" id="lastName" name="lastName" onChange={handleChange}></input>
          </div>
        </div>
        <div className="row col-10 form-row">
          <div className="col">
            <label htmlFor="dobInput" className="form-label">Date of Birth</label>
            <input className="form-control" type="date" id="dob" name="dob" onChange={handleChange}></input>
          </div>
          <div className="col">
            <label htmlFor="heightInput" className="form-label">Height (Inches)</label>
            <input className="form-control" type="number" id="heightInInches" name="heightInInches" onChange={handleChange}></input>
          </div>
        </div>
        <div className="row col-10 form-row">
          <div className="col">
            <button className="btn btn-success col" type="submit" id="submit-add-agent">Add Agent</button>
          </div>
        </div>

      </div>
    </form>
  );
}

export default AddAgentForm;