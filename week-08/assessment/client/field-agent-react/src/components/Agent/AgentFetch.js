import { useState, useEffect } from 'react';
import Agent from './Agent.js';
import createRandomAgent from './CreateRandomAgent';
import AddAgentForm from './AddAgentForm.js';
import UpdateAgentForm from './EditAgentForm.js';
import DeleteAgentForm from './DeleteAgentForm.js';
import Errors from '../Errors.js';
import './AgentFetch.css';
import agentPic from '../images/agent_pic.jpg';

function AgentFetch( { setMenuSelection, setAgentForAliases } ) {

  const [agents, setAgents] = useState([]);
  const [editAgentId, setEditAgentId] = useState(0);
  const [deleteAgentId, setDeleteAgentId] = useState(0);
  const [errors, setErrors] = useState([]);

  useEffect(() => {
    const getData = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/agent");
        const data = await response.json();
        setAgents(data);
      } catch (error) {
        setErrors(["Failed to fetch agents."]);
      }
    };
    getData();
  }, []);

  const addAgent = async (newAgent) => {
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

    try {
      const response = await fetch("http://localhost:8080/api/agent", init);

      if (response.status === 201 || response.status === 400) {
        const data = await response.json();

        if (data.agentId) {
          setAgents([...agents, data]);
          handleCancel();
        } else {
          setErrors(data);
        }
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"]);
      }
    } catch (error) {
      setErrors([error])
    }
  }

  const addRandomAgent = () => {
    const agent = createRandomAgent();
    addAgent(agent);
  };

  const editAgent = async (editedAgent) => {
    const updatedAgent = {
      "agentId": editedAgent.agentId,
      "firstName": editedAgent.firstName,
      "middleName": editedAgent.middleName,
      "lastName": editedAgent.lastName,
      "dob": editedAgent.dob,
      "heightInInches": editedAgent.heightInInches
    };

    const init = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(updatedAgent)
    };

    try {
      const response = await fetch(`http://localhost:8080/api/agent/${editedAgent.agentId}`, init);

      if (response.status === 204) {
        const newAgents = [...agents];
        const agentIndexToEdit = agents.findIndex(agent => agent.agentId === editAgentId);
        newAgents[agentIndexToEdit] = updatedAgent;

        setAgents(newAgents);
        handleCancel();
      } else if (response.status === 400) {
        const data = await response.json();
        setErrors(data);
      } else if (response.status === 404) {
        throw new Error(["Agent to edit not found."])
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"])
      }
    } catch (error) {
      setErrors(error);
    }
  }

  const deleteById = async (agentId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/agent/${agentId}`, { method: "DELETE" });
    
      if (response.status === 204) {
        const newAgents = agents.filter(agent => agent.agentId !== agentId);
        setAgents(newAgents);
        handleCancel();
      } else if (response.status === 404) {
        throw new Error(`Agent ID #${agentId} not found.`);
      } else {
        throw new Error("Something unexpected went wrong, sorry!")
      }
    } catch (error) {
      setErrors(error);
    }
  };

  const getAgentToEdit = () => {
    return agents.find(a => a.agentId === editAgentId);
  };

  const getAgentToDelete = () => {
    return agents.find(a => a.agentId === deleteAgentId);
  };

  const goBackToMainMenu = () => {
    setMenuSelection(0);
  };

  const handleCancel = () => {
    setEditAgentId(0);
    setDeleteAgentId(0);
    setErrors([]);
  }

  const handleAliases = (agentId) => {
    const agentForAliases = agents.find(a => a.agentId === agentId);
    setAgentForAliases(agentForAliases);
  }

  const makeAgent = (agent) => {
    return (
      <Agent 
        key={agent.agentId} 
        agent={agent}
        setEditAgentId={setEditAgentId}
        setDeleteAgentId={setDeleteAgentId}
        handleAliases={handleAliases}
        handleCancel={handleCancel} />
    );
  };
  
  return (
    <div>  
      <div className="jumbotron row">
        <div className="col">
          <h1>Agents</h1>
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
              <div className="row">
                <div className="col-8">
                  <h3>Agent List</h3>
                </div>
                <div className="col-4" align="right">
                  <button className="btn btn-info mr-5 col ml-2" onClick={goBackToMainMenu}>Back To Landing Page</button>
                </div>
              </div>
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
            {agents.length === 0 ? <div className="colspan alert alert-warning" align="center">These agents appear to have given you the slip. (No agents found)</div> : null}
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