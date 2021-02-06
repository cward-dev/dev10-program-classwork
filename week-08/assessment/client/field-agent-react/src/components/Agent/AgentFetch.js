import { useState, useEffect } from 'react';
import Agent from './Agent.js';
import createRandomAgent from './CreateRandomAgent';
import AddAgentForm from './AddAgentForm.js';
import UpdateAgentForm from './UpdateAgentForm.js';
import DeleteAgentForm from './DeleteAgentForm.js';
import Errors from '../Errors.js';
import './AgentFetch.css';
import agentPic from '../images/agent_pic.jpg';

function AgentFetch( { setMenuSelection, setAgentIdForAliases } ) {

  const [agents, setAgents] = useState([]);
  const [editAgentId, setEditAgentId] = useState(0);
  const [deleteAgentId, setDeleteAgentId] = useState(0);
  const [errors, setErrors] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/api/agent')
      .then(response => response.json())
      .then(data => setAgents(data))
      .catch(error => setErrors(error));
  }, []);

  const addAgent = (newAgent) => {

    const agent = {
      "firstName": newAgent.firstName,
      "middleName": newAgent.middleName,
      "lastName": newAgent.lastName,
      "dob": newAgent.dob,
      "heightInInches": parseInt(newAgent.heightInInches),
    };

    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(agent)
    };

    fetch("http://localhost:8080/api/agent", init)
      .then(response => {
        if (response.status === 201 || response.status === 400) {
          return response.json();
        }
        return Promise.reject(`Agent add failed with status ${response.status}.`);
      })
      .then(data => {
        if (data.agentId) {
          setAgents([...agents, data]);
          setErrors([]);
        } else {
          setErrors(data);
        }})
      .catch(data => setErrors([data]));
  };

  const addRandomAgent = () => {

    const agent = createRandomAgent();

    addAgent(agent);
  };

  const editAgent = (inputAgent) => {
      
    const updatedAgent = {
      "agentId": inputAgent.agentId,
      "firstName": inputAgent.firstName,
      "middleName": inputAgent.middleName,
      "lastName": inputAgent.lastName,
      "dob": inputAgent.dob,
      "heightInInches": inputAgent.heightInInches
    };

    const init = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(updatedAgent)
    };

    fetch(`http://localhost:8080/api/agent/${inputAgent.agentId}`, init)
      .then(response => {
        if (response.status === 204) {
          return null;
        } else if (response.status === 400) {
          return response.json();
        } else if (response.status === 404) {
          return Promise.reject("Agent to edit not found.");
        } else {
          return Promise.reject(`Agent id ${updatedAgent.agentId} update failed with status ${response.status}.`);
        }
      })
      .then(data => {
        if (!data) {
          const newAgents = [...agents];
          const agentIndexToReplace = agents.findIndex(agent => agent.agentId === updatedAgent.agentId);
          newAgents[agentIndexToReplace] = updatedAgent;
  
          setAgents([...newAgents]);
          setEditAgentId(0);
          setErrors([]);
        } else {
          setErrors([data]);
        }
      });
  };

  const deleteById = (agentId) => {
    fetch(`http://localhost:8080/api/agent/${agentId}`, { method: "DELETE" })
      .then(response => {
        if (response.status === 204) {
          setAgents(agents.filter(agent => agent.agentId !== agentId));
          setDeleteAgentId(0);
          setErrors([]);
        } else if (response.status === 404) {
          return Promise.reject("Agent to delete not found.");
        } else {
          return Promise.reject(`Delete failed with status: ${response.status}`);
        }
      })
      .catch(setErrors);
  };

  const getAgentById = (agentId) => {
    const agentFound = agents.find(a => a.agentId === agentId);

    return agentFound;
  };

  const getAgentToEdit = () => {
    return getAgentById(editAgentId);
  };

  const getAgentToDelete = () => {
    return getAgentById(deleteAgentId);
  };

  const goBackToMainMenu = () => {
    setMenuSelection(0);
  };

  const handleCancel = () => {
    setEditAgentId(0);
    setDeleteAgentId(0);
  }

  const handleAliases = (agentId) => {
    setAgentIdForAliases(agentId);
  }

  const makeAgent = (agent) => {
    return (
      <Agent 
        key={agent.agentId} 
        agent={agent}
        setEditAgentId={setEditAgentId}
        setDeleteAgentId={setDeleteAgentId}
        handleAliases={handleAliases} />
    );
  };
  
  return (
    <div>  
      <div className="jumbotron row">
        <div className="col">
          <h1>Agents</h1>
        </div>
        <div className="col" align="right">
          <button className="btn btn-info mr-5" onClick={goBackToMainMenu}>Go Back</button>
        </div>
      </div>
      <div className="container">
        {errors.length > 0 ? (
          <Errors errors={errors} />
          ) : null}
        <div className="row">
          <div className="col-4"> 
            <div id="current-form">
              {editAgentId === 0 && deleteAgentId === 0 ? 
                <AddAgentForm addAgent={addAgent} /> 
                : null}
              {editAgentId !== 0 ? 
                <UpdateAgentForm getAgentToEdit={getAgentToEdit} handleEdit={editAgent} handleCancel={handleCancel} />
                : null}
              {deleteAgentId !== 0 ? 
                <DeleteAgentForm getAgentToDelete={getAgentToDelete} handleDelete={deleteById} handleCancel={handleCancel} />
                : null}
            </div>
            <div className="col d-flex justify-content-center pt-2">
              <img src={agentPic} alt="Agent Pic" id="agent-pic" align="center"></img>
            </div>
          </div>
          <div className="col-8">
            <div className="alert alert-secondary">
              <h3>Agent List</h3>
            </div>
            <table className="table table-striped">
              <thead className="table-dark">
                <tr>
                  <th className="first-name-item">First Name</th>
                  <th className="middle-name-item">MI</th>
                  <th className="last-name-item">Last Name</th>
                  <th className="dob-item">Date of Birth</th>
                  <th className="height-name-item">Height</th>
                  <th className="actions-item">Actions</th>
                </tr>
              </thead>
              <tbody>
                {agents.map(agent => makeAgent(agent))}
              </tbody>
            </table>
            <div align="right">
              <button className="btn btn-success mr-2" onClick={addRandomAgent}>Add Random Agent</button>
            </div>
          </div>
        </div>
      </div>
      <div className="jumbotron mt-5" />
    </div>
  );
}

export default AgentFetch;