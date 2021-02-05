import './TodoItem.css'

function TodoItem( { id, description, onRemove, onUpdate, setIdToUpdate } ) {

  const handleDelete = function() {
    onRemove(id);
  }

  const handleUpdate = function() {
    setIdToUpdate(id);
    onUpdate(id, description);
  }

  return (
    <div className="todo-item row" key={id}>
      <h5>{description}</h5>
      <button className="col btn" onClick={handleUpdate}>Update</button>
      <button className="col btn" onClick={handleDelete}>Delete</button>
    </div>
  );
}

export default TodoItem;