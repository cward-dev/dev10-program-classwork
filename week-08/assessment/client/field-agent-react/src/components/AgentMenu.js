import { useState } from 'react';
import Agent from './Agent.js';
import AddAgentForm from './AddAgentForm.js';
import UpdateAgentForm from './UpdateAgentForm.js';
import Errors from './Errors.js';

function AgentMenu( { 
  agents, setAgents,
  errors, setErrors,
  editAgentId, setEditAgentId,
  addAgent,
  addRandomAgent,
  editAgent,
  deleteById,
  getAgentById }) {

  const getAgentToEdit = () => {
    return getAgentById(editAgentId);
  };

  const handleEdit = (agent) => {
    editAgent(agent);
  };

  const makeAgent = (agent) => {
    return (
      <Agent 
        key={agent.id} 
        agent={agent}
        setEditAgentId={setEditAgentId}
        deleteById={deleteById} />
    );
  };

  return (
    <div>
      <h1>Agents</h1>
      {editAgentId === 0 ? 
        <AddAgentForm addAgent={addAgent} /> 
        : <UpdateAgentForm getAgentToEdit={getAgentToEdit} handleEdit={handleEdit} />}
      {errors.length > 0 ? (
        <Errors errors={errors} />
      ) : null}
      <table className="table table-striped table-dark mt-5">
        <thead>
          <tr>
            <th>Id</th>
            <th>First Name</th>
            <th>Middle Name</th>
            <th>Last Name</th>
            <th>Date of Birth</th>
            <th>Height (Inches)</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {agents.map(makeAgent)}
        </tbody>
      </table>
      <div>
        <button className="btn btn-success" onClick={addRandomAgent}>Add Random Agent</button>
      </div>
    </div>
  );
}

export default AgentMenu;