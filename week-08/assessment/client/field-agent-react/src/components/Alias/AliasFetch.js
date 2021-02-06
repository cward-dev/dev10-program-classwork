import { useState, useEffect } from 'react';
import Alias from './Alias.js';
import createRandomAlias from './CreateRandomAlias';
import AddAliasForm from './AddAliasForm.js';
import UpdateAliasForm from './UpdateAliasForm.js';
import DeleteAliasForm from './DeleteAliasForm.js';
import Errors from '../Errors.js';
import './AliasFetch.css';
import aliasPic from '../images/alias_pic.png';

function AliasFetch( { setMenuSelection, agentId } ) {

  const [aliases, setAliases] = useState([]);
  const [editAliasId, setEditAliasId] = useState(0);
  const [deleteAliasId, setDeleteAliasId] = useState(0);
  const [errors, setErrors] = useState([]);

  useEffect(() => {
    fetch(`http://localhost:8080/api/alias/agent/${agentId}`)
      .then(response => {
        if (response.status === 404) {
          return Promise.reject("This agent has no aliases.");
        } else {
          return response.json();
        }
      })
      .then(data => setAliases(data))
      .catch(error => setErrors([error]));
  }, []);

  const addAlias = (newAlias) => {

    const alias = {
      "name": newAlias.name,
      "persona": newAlias.persona,
      "agentId": agentId
    };

    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(alias)
    };

    fetch("http://localhost:8080/api/alias", init)
      .then(response => {
        if (response.status === 201 || response.status === 400) {
          return response.json();
        }
        return Promise.reject(`Alias add failed with status ${response.status}.`);
      })
      .then(data => {
        if (data.aliasId) {
          setAliases([...aliases, data]);
          setErrors([]);
        } else {
          setErrors(data);
        }})
      .catch(data => setErrors([data]));
  };

  const addRandomAlias = () => {

    const alias = createRandomAlias(1);

    addAlias(alias);
  };

  const editAlias = (inputAlias) => {
      
    const updatedAlias = {
      "aliasId": inputAlias.aliasId,
      "name": inputAlias.name,
      "persona": inputAlias.persona,
      "agentId": agentId
    };

    const init = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(updatedAlias)
    };

    fetch(`http://localhost:8080/api/alias/${inputAlias.aliasId}`, init)
      .then(response => {
        if (response.status === 204) {
          return null;
        } else if (response.status === 400) {
          return response.json();
        } else if (response.status === 404) {
          return Promise.reject("Alias to edit not found.");
        } else {
          return Promise.reject(`Alias id ${updatedAlias.aliasId} update failed with status ${response.status}.`);
        }
      })
      .then(data => {
        if (!data) {
          const newAliases = [...aliases];
          const aliasIndexToReplace = aliases.findIndex(alias => alias.aliasId === updatedAlias.aliasId);
          newAliases[aliasIndexToReplace] = updatedAlias;
  
          setAliases([...newAliases]);
          setEditAliasId(0);
          setErrors([]);
        } else {
          setErrors([data]);
        }
      });
  };

  const deleteById = (aliasId) => {
    fetch(`http://localhost:8080/api/alias/${aliasId}`, { method: "DELETE" })
      .then(response => {
        if (response.status === 204) {
          setAliases(aliases.filter(alias => alias.aliasId !== aliasId));
          setDeleteAliasId(0);
          setErrors([]);
        } else if (response.status === 404) {
          return Promise.reject("Alias to delete not found.");
        } else {
          return Promise.reject(`Delete failed with status: ${response.status}`);
        }
      })
      .catch(setErrors);
  };

  const getAliasById = (aliasId) => {
    const aliasFound = aliases.find(a => a.aliasId === aliasId);

    return aliasFound;
  };

  const getAliasToEdit = () => {
    return getAliasById(editAliasId);
  };

  const getAliasToDelete = () => {
    return getAliasById(deleteAliasId);
  };

  const goBackToAgentMenu = () => {
    setMenuSelection(1);
  };

  const handleCancel = () => {
    setEditAliasId(0);
    setDeleteAliasId(0);
  }

  const makeAlias = (alias) => {
    return (
      <Alias 
        key={alias.aliasId} 
        alias={alias}
        setEditAliasId={setEditAliasId}
        setDeleteAliasId={setDeleteAliasId} />
    );
  };
  
  return (
    <div>  
      <div className="jumbotron row">
        <div className="col">
          <h1>Aliases</h1>
        </div>
        <div className="col" align="right">
          <button className="btn btn-info mr-5" onClick={goBackToAgentMenu}>Go Back</button>
        </div>
      </div>
      <div className="container">
        <Errors errors={errors} />
        <div className="row">
          <div className="col-4"> 
            <div id="current-form">
              {editAliasId === 0 && deleteAliasId === 0 ? 
                <AddAliasForm addAlias={addAlias} /> 
                : null}
              {editAliasId !== 0 ? 
                <UpdateAliasForm getAliasToEdit={getAliasToEdit} handleEdit={editAlias} handleCancel={handleCancel} />
                : null}
              {deleteAliasId !== 0 ? 
                <DeleteAliasForm getAliasToDelete={getAliasToDelete} handleDelete={deleteById} handleCancel={handleCancel} />
                : null}
            </div>
            <div className="col d-flex justify-content-center pt-2">
              <img src={aliasPic} alt="Alias Pic" id="alias-pic" align="center"></img>
            </div>
          </div>
          <div className="col-8">
            <div className="alert alert-secondary">
              <h3>Alias List</h3>
            </div>
            <table className="table table-striped">
              <thead className="table-dark">
                <tr>
                  <th className="name-item">Name</th>
                  <th className="persona-item">Persona</th>
                  <th className="actions-alias-item">Actions</th>
                </tr>
              </thead>
              <tbody>
                {aliases.map(alias => makeAlias(alias))}
              </tbody>
            </table>
            <div align="right">
              <button className="btn btn-success mr-2" onClick={addRandomAlias}>Add Random Alias</button>
            </div>
          </div>
        </div>
      </div>
      <div className="jumbotron mt-5" />
    </div>
  );
}

export default AliasFetch;