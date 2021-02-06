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
      <div className="alert alert-secondary">
        <h3>Add Agent</h3>
      </div>
      <div>
          <div className="form-row">
            <label htmlFor="firstNameInput" className="col-4" align="right">*First Name</label>
            <input className="col form-control" type="text" maxLength="50" required id="firstName" name="firstName" onChange={handleChange}></input>
          </div>
          <div className="form-row">
            <label htmlFor="firmiddleNameInputtNameInput" className="col-4" align="right">Middle Name</label>
            <input className="col form-control" type="text" maxLength="50" id="middleName" name="middleName" onChange={handleChange}></input>
          </div>
          <div className="form-row">
            <label htmlFor="lastNameInput" className="col-4" align="right">*Last Name</label>
            <input className="col form-control" type="text" required maxLength="50" id="lastName" name="lastName" onChange={handleChange}></input>
          </div>
          <div className="form-row">
            <label htmlFor="dobInput" className="col-4" align="right">*Date of Birth</label>
            <input className="col form-control" type="date" required id="dob" name="dob" onChange={handleChange}></input>
          </div>
          <div className="form-row">
            <label htmlFor="heightInput" className="col-4" align="right">*Height in Inches</label>
            <input className="col form-control" type="number" required min="36" max="96" id="heightInInches" name="heightInInches" onChange={handleChange}></input>
          </div>
        <div className="form-row">
          <div className="col">
            <button className="btn btn-success col" type="submit" id="submit-agent">Add Agent</button>
          </div>
        </div>
      </div>
    </form>
  );
}

export default AddAgentForm;