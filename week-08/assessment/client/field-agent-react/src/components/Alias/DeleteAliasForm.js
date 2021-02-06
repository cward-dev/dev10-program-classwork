import { useState } from 'react';
import './AliasForm.css'

function DeleteAliasForm( { getAliasToDelete, handleDelete, handleCancel } ) {

  const aliasToDelete = getAliasToDelete();

  const [alias, setAlias] = useState({...aliasToDelete});

  const handleSubmit = (event) => {
    event.preventDefault();
    handleDelete(alias.aliasId);

    document.getElementById("name").value = '';
    document.getElementById("persona").value = '';
  };

  const handleDeleteCancel = () => {
    handleCancel();

    document.getElementById("name").value = '';
    document.getElementById("persona").value = '';
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="alert alert-secondary">
        <h3>Delete Alias</h3>
      </div>
      <div>
        <div className="form-row">
          <label htmlFor="firstNameInput" className="col-4" align="right">Name</label>
          <input className="col form-control" type="text" maxLength="125" required id="name" name="name" value={alias.name} readOnly></input>
        </div>
        <div className="form-row">
          <label htmlFor="firmiddleNameInputtNameInput" className="col-4" align="right">Persona</label>
          <textarea className="col form-control" type="textarea" rows="7" maxLength="2048" id="persona" name="persona" value={alias.persona ? alias.persona : ''} readOnly></textarea>
        </div>
        <div className="form-row">
          <div className="col">
            <button className="btn btn-danger col" type="submit" id="submit-alias">Delete Alias</button>
          </div>
          <div className="col">
            <button className="btn btn-warning col" type="button" id="cancel-alias" onClick={handleDeleteCancel}>Cancel</button>
          </div>
        </div>
      </div>
    </form>
  );
}

export default DeleteAliasForm;