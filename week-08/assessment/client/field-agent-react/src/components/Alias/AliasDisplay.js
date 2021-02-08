import Alias from './Alias.js';
import AgentForAlias from './AgentForAlias';
import Errors from '../Errors.js';
import AddAliasForm from './AddAliasForm.js';
import UpdateAliasForm from './EditAliasForm.js';
import DeleteAliasForm from './DeleteAliasForm.js';
import aliasPic from '../images/alias_pic.png';
import './AliasDisplay.css';

function AliasDisplay( { aliases, editAliasId, setEditAliasId, deleteAliasId, setDeleteAliasId, errors, 
  handleCancel, addAlias, addRandomAlias, editAlias, deleteById, 
  getAliasToEdit, getAliasToDelete, goBackToAgentMenu, agent } ) {

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
            {aliases.length === 0 ? <div className="colspan alert alert-warning" align="center">If this agent has any aliases, even we haven't tracked them down. (No aliases found)</div> : null}
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

export default AliasDisplay;