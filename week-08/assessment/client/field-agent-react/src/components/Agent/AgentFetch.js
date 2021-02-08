import { useState, useEffect } from 'react';
import createRandomAgent from './CreateRandomAgent';
import AgentDisplay from './AgentDisplay';

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
        setErrors(["Something went wrong with our database, sorry!"]);
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
      if (error.message === "Failed to fetch") {
        setErrors(["Something went wrong with our database, sorry!"])
      } else {
        setErrors(["Something unexpected went wrong, sorry!"]);
      }
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
      if (error.message === "Failed to fetch") {
        setErrors(["Something went wrong with our database, sorry!"])
      } else {
        setErrors(["Something unexpected went wrong, sorry!"]);
      }
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
      if (error.message === "Failed to fetch") {
        setErrors(["Something went wrong with our database, sorry!"])
      } else {
        setErrors(["Something unexpected went wrong, sorry!"]);
      }
    }
  };

  const getAgentToEdit = () => {
    return agents.find(a => a.agentId === editAgentId);
  };

  const getAgentToDelete = () => {
    return agents.find(a => a.agentId === deleteAgentId);
  };

  const handleCancel = () => {
    setEditAgentId(0);
    setDeleteAgentId(0);
    setErrors([]);
  }

  const goBackToMainMenu = () => {
    setMenuSelection(0);
  };

  const handleAliases = (agentId) => {
    const agentForAliases = agents.find(a => a.agentId === agentId);
    setAgentForAliases(agentForAliases);
  }

  return (
    <AgentDisplay agents={agents} editAgentId={editAgentId} setEditAgentId={setEditAgentId} deleteAgentId={deleteAgentId} setDeleteAgentId={setDeleteAgentId} errors={errors} 
      handleAliases={handleAliases} handleCancel={handleCancel} addAgent={addAgent} addRandomAgent={addRandomAgent} editAgent={editAgent} deleteById={deleteById} 
      getAgentToEdit={getAgentToEdit} getAgentToDelete={getAgentToDelete} goBackToMainMenu={goBackToMainMenu} />
  );
}

export default AgentFetch;