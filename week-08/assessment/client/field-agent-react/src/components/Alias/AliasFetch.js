import { useState, useEffect } from 'react';
import Alias from './Alias.js';
import createRandomAlias from './CreateRandomAlias';
import AddAliasForm from './AddAliasForm.js';
import UpdateAliasForm from './EditAliasForm.js';
import DeleteAliasForm from './DeleteAliasForm.js';
import Errors from '../Errors.js';
import './AliasFetch.css';
import aliasPic from '../images/alias_pic.png';
import AgentForAlias from './AgentForAlias';

function AliasFetch( { setMenuSelection, agent } ) {

  const [aliases, setAliases] = useState([]);
  const [editAliasId, setEditAliasId] = useState(0);
  const [deleteAliasId, setDeleteAliasId] = useState(0);
  const [errors, setErrors] = useState([]);


  useEffect(() => {
    const getData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/alias/agent/${agent.agentId}`);
        
        if (response.status === 200) {
          const data = await response.json();
          setAliases(data);
        }
      } catch (error) {
        setErrors("Failed to fetch aliases.");
      }
    };
    getData();
  }, []);

  const addAlias = async (newAlias) => {
    const alias = {
      "name": newAlias.name,
      "persona": newAlias.persona,
      "agentId": agent.agentId
    };

    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(alias)
    };

    try {
      const response = await fetch("http://localhost:8080/api/alias", init);

      if (response.status === 201 || response.status === 400) {
        const data = await response.json();

        if (data.aliasId) {
          setAliases([...aliases, data]);
          handleCancel();
        } else {
          setErrors(data);
        }
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"]);
      }
    } catch (error) {
      setErrors(error)
    }
  }

  const addRandomAlias = () => {
    const alias = createRandomAlias(1);
    addAlias(alias);
  };

  const editAlias = async (editedAlias) => {
    const updatedAlias = {
      "aliasId": editedAlias.aliasId,
      "name": editedAlias.name,
      "persona": editedAlias.persona,
      "agentId": agent.agentId
    };

    const init = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(updatedAlias)
    };

    try {
      const response = await fetch(`http://localhost:8080/api/alias/${editedAlias.aliasId}`, init);

      if (response.status === 204) {
        const newAliases = [...aliases];
        const aliasIndexToEdit = aliases.findIndex(alias => alias.aliasId === editAliasId);
        newAliases[aliasIndexToEdit] = updatedAlias;

        setAliases(newAliases);
        handleCancel();
      } else if (response.status === 400) {
        const data = await response.json();
        setErrors(data);
      } else if (response.status === 404) {
        throw new Error(["Alias to edit not found."])
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"])
      }
    } catch (error) {
      setErrors(error);
    }
  }

  const deleteById = async (aliasId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/alias/${aliasId}`, { method: "DELETE" });
    
      if (response.status === 204) {
        const newAliases = aliases.filter(alias => alias.aliasId !== aliasId);
        setAliases(newAliases);
        handleCancel();
      } else if (response.status === 404) {
        throw new Error(`Alias ID #${aliasId} not found.`);
      } else {
        throw new Error("Something unexpected went wrong, sorry!")
      }
    } catch (error) {
      setErrors(error);
    }
  };

  const getAliasToEdit = () => {
    return aliases.find(a => a.aliasId === editAliasId);
  };

  const getAliasToDelete = () => {
    return aliases.find(a => a.aliasId === deleteAliasId);
  };

  const goBackToAgentMenu = () => {
    setMenuSelection(1);
  };

  const handleCancel = () => {
    setEditAliasId(0);
    setDeleteAliasId(0);
    setErrors([]);
  }

  const makeAlias = (alias) => {
    return (
      <Alias 
        key={alias.aliasId} 
        alias={alias}
        setEditAliasId={setEditAliasId}
        setDeleteAliasId={setDeleteAliasId}
        handleCancel={handleCancel} />
    );
  };
  
  return (
    <div>  
      <div className="jumbotron row">
        <div className="col">
          <h1>Aliases</h1>
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
            <AgentForAlias agent={agent} goBackToAgentMenu={goBackToAgentMenu} />
            <div className="alert alert-secondary">
              <h3>Alias List</h3>
            </div>
            <table className="table table-striped">
              <thead className="table-dark">
                <tr>
                  <th className="name-alias-item">Name</th>
                  <th className="persona-alias-item">Persona</th>
                  <th className="actions-alias-item">Actions</th>
                </tr>
              </thead>
              <tbody>
                {aliases.map(alias => makeAlias(alias))}
              </tbody>
            </table>
            {aliases.length === 0 ? <div className="colspan alert alert-warning" align="center">If this agent has any aliases, they're deep undercover. (No aliases found)</div> : null}
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