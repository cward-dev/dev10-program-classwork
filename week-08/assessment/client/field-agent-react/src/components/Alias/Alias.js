import './Alias.css';

function Alias( { alias, setEditAliasId, setDeleteAliasId, handleCancel } ) {

  const { aliasId, name, persona } = alias;

  const personaLengthShown = 175;

  const handleEdit = () => {
    handleCancel();
    setEditAliasId(aliasId);
  }

  const handleDelete = () => {
    handleCancel();
    setDeleteAliasId(aliasId);
  }

  return (
    <tr key={aliasId}>
      <td className="name-alias-item">{name.substr(0,18)}</td>
      <td className="persona-alias-item">{persona ? persona.substr(0,personaLengthShown) + (persona.length > personaLengthShown ? '...' : '') : null}</td>
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