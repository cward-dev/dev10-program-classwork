import { useState } from 'react';
import AgentFetch from './Agent/AgentFetch';
import AliasFetch from './Alias/AliasFetch';
import './MainMenu.css';
import agentspic from './images/agents_pic.jpg';

function MainMenu() {

  const [menuSelection, setMenuSelection] = useState(0);
  const [agentIdForAliases, setAgentIdForAliases] = useState(0);

  const goToAgentPage = () => {
    setMenuSelection(1);
  }

  const goToAliasPage = (agentId) => {
    setAgentIdForAliases(agentId);
    setMenuSelection(2);
  }

  return (
    <div>
      {menuSelection === 0 ? (
        <div>
          <div className="jumbotron row">
            <div className="col">
              <h1>Field Agents React</h1>
            </div>
          </div>
          <div className="container menu-button">
            <button className="btn-info btn-block btn-lg" onClick={goToAgentPage}>Welcome, agent.</button>
          </div>
          <div className="col d-flex justify-content-center pt-5 jumbotron">
              <img src={agentspic} alt="Agent Pic" id="agent-pic" align="center"></img>
          </div>
        </div> 
      ) : null}
      {menuSelection === 1 ? <AgentFetch setMenuSelection={setMenuSelection} setAgentIdForAliases={goToAliasPage} /> : null}
      {menuSelection === 2 ? <AliasFetch setMenuSelection={setMenuSelection} agentId={agentIdForAliases} /> : null}
    </div>
  );

}

export default MainMenu;