import Agent from './Agent.js';
import Errors from '../Errors.js';
import AddAgentForm from './AddAgentForm.js';
import UpdateAgentForm from './EditAgentForm.js';
import DeleteAgentForm from './DeleteAgentForm.js';
import agentPic from '../images/agent_pic.jpg';
import './AgentDisplay.css';

function AgentDisplay( { agents, editAgentId, setEditAgentId, deleteAgentId, setDeleteAgentId, errors, 
  handleAliases, handleCancel, addAgent, addRandomAgent, editAgent, deleteById, 
  getAgentToEdit, getAgentToDelete, goBackToMainMenu } ) {

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

export default AgentDisplay;