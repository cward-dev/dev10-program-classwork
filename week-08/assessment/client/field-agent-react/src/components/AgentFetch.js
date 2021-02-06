import { useState, useEffect } from 'react';
import AgentMenu from './AgentMenu.js';

function AgentFetch() {

  const [agents, setAgents] = useState([]);
  const [editAgentId, setEditAgentId] = useState(0);
  const [errors, setErrors] = useState([]);

  useEffect(() => {

    fetch("http://localhost:8080/api/agent")
        .then(response => {
            if (response.status !== 200) {
                return Promise.reject("agents fetch failed")
            }
            return response.json();
        })
        .then(data => setAgents(data))
        .catch(error => console.log(error));

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
        if (response.status === 201) {
          return response.json();
        } else if (response.status === 400) {
          return Promise.reject("response is 400 Bad Request");
        }
        return Promise.reject("response is not 201 Created");
      })
      .then(data => setAgents([...agents, data]))
      .catch(console.log);
  };

  const addRandomAgent = () => {

    const agent = {
      "firstName": "Claudian",
      "middleName": "C",
      "lastName": "O'Lynn",
      "dob": "1956-11-09",
      "heightInInches": 41
    };

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
          return Promise.reject("Agent not found");
        } else {
          return Promise.reject(`Agent id ${updatedAgent.agentId} update failed with status ${response.status}.`);
        }
      })
      .then(data => {
        if (!data) {
          const newAgents = [agents.filter(agent => agent.agentId !== updatedAgent.agentId)];
          newAgents.push(updatedAgent);
          newAgents.sort(agent => agent.getAgentById);
  
          setAgents([...newAgents]);
          setEditAgentId(0);
        } else {
          setErrors(data);
        }
      });
  };

  const deleteById = (agentId) => {
    fetch(`http://localhost:8080/api/agent/${agentId}`, { method: "DELETE" })
      .then(response => {
        if (response.status === 204) {
          setAgents(agents.filter(agent => agent.agentId !== agentId));
        } else if (response.status === 404) {
          return Promise.reject("agent not found");
        } else {
          return Promise.reject(`Delete failed with status: ${response.status}`);
        }
      })
      .catch(console.log);
  };

  const getAgentById = (agentId) => {
    const agentFound = agents.find(a => a.agentId === agentId);

    return agentFound;
  }

  return (
    <div>
      <AgentMenu 
        agents={agents} agentList={setAgents}
        editAgentId={editAgentId} setEditAgentId={setEditAgentId}
        errors={errors} setErrors={setErrors}
        addAgent={addAgent}
        addRandomAgent={addRandomAgent}
        editAgent={editAgent}
        deleteById={deleteById}
        getAgentById={getAgentById}
      />
    </div>
  );
}

export default AgentFetch;