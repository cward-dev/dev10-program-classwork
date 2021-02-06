import { useState } from 'react';
import './AliasForm.css'

function UpdateAliasForm( { getAliasToEdit, handleEdit, handleCancel } ) {

  const aliasToEdit = getAliasToEdit();

  const [alias, setAlias] = useState({...aliasToEdit});

  const handleChange = (event) => {
    const updatedAlias = {...alias};
    updatedAlias[event.target.name] = event.target.value;
    setAlias(updatedAlias);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    handleEdit(alias);

    document.getElementById("name").value = '';
    document.getElementById("persona").value = '';
  };

  const handleEditCancel = () => {
    handleCancel();

    document.getElementById("name").value = '';
    document.getElementById("persona").value = '';
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="alert alert-secondary">
        <h3>Update Alias</h3>
      </div>
      <div>
        <div className="form-row">
          <label htmlFor="firstNameInput" className="col-4" align="right">Name*</label>
          <input className="col form-control" type="text" maxLength="125" required id="name" name="name" value={alias.name} onChange={handleChange}></input>
        </div>
        <div className="form-row">
          <label htmlFor="firmiddleNameInputtNameInput" className="col-4" align="right">Persona</label>
          <textarea className="col form-control" type="textarea" rows="7" maxLength="2048" id="persona" name="persona" value={alias.persona ? alias.persona : ''} onChange={handleChange}></textarea>
        </div>
        <div className="form-row">
          <div className="col">
            <button className="btn btn-info col" type="submit" id="submit-alias">Update Alias</button>
          </div>
          <div className="col">
            <button className="btn btn-warning col" type="button" id="cancel-alias" onClick={handleEditCancel}>Cancel</button>
          </div>
        </div>
      </div>
    </form>
  );
}

export default UpdateAliasForm;