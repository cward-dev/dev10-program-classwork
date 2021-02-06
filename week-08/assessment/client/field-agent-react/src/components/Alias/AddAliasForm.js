import { useState } from 'react';
import './AliasForm.css';

const DEFAULT_ALIAS = {
  name: '',
  persona: '',
  agentId: 0
};

function AddAliasForm( { addAlias } ) {

  const [alias, setAlias] = useState(DEFAULT_ALIAS);

  const handleChange = (event) => {
    const updatedAlias = {...alias};
    updatedAlias[event.target.name] = event.target.value;
    setAlias(updatedAlias);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    addAlias(alias);

    setAlias(DEFAULT_ALIAS);
    document.getElementById("name").value = '';
    document.getElementById("persona").value = '';
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="alert alert-secondary">
        <h3>Add Alias</h3>
      </div>
      <div>
        <div className="form-row">
          <label htmlFor="firstNameInput" className="col-2" align="right">Name*</label>
          <input className="col form-control" type="text" maxLength="125" required id="name" name="name" onChange={handleChange}></input>
        </div>
        <div className="form-row">
          <label htmlFor="firmiddleNameInputtNameInput" className="col-2" align="right">Persona</label>
          <textarea className="col form-control" type="textarea" rows="7" maxLength="2048" id="persona" name="persona" onChange={handleChange}></textarea>
        </div>
        <div className="form-row">
          <div className="col">
            <button className="btn btn-success col" type="submit" id="submit-alias">Add Alias</button>
          </div>
        </div>
      </div>
    </form>
  );
}

export default AddAliasForm;