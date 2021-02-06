import './Alias.css';

function Alias( { alias, setEditAliasId, setDeleteAliasId } ) {

  const { aliasId, name, persona, agentId} = alias;

  const personaLengthShown = 175;

  const handleEdit = () => {
    setDeleteAliasId(0);
    setEditAliasId(aliasId);
  }

  const handleDelete = () => {
    setEditAliasId(0);
    setDeleteAliasId(aliasId);
  }

  return (
    <tr key={aliasId}>
      <td className="name-item">{name.substr(0,18)}</td>
      <td className="persona-item">{persona ? persona.substr(0,personaLengthShown) + (persona.length > personaLengthShown ? '...' : '') : null}</td>
      <td className="actions-alias-item">
        <div className="row pr-3">
          <button className="col btn btn-info" onClick={handleEdit}>Edit</button>
          <button className="col btn btn-danger ml-2" onClick={handleDelete}>Delete</button>
        </div>
      </td>
    </tr>
  );
}

export default Alias;