import { useState } from 'react';
import TodoItem from './TodoItem.js'
import './TodoList.css';

const DEFAULT_TODO_CONFIG = [
  { id: 1, description: 'Buy Pickles' },
  { id: 2, description: 'Sell Pickles' }
];

function TodoList( { todoConfig = DEFAULT_TODO_CONFIG } ) {

  const [todoItems, setTodoItems] = useState(todoConfig);
  const [description, setDescription] = useState('');
  const [idToUpdate, setIdToUpdate] = useState(0);

  const handleChange = (event) => {
    setDescription(event.target.value);
  };

  const addItem = (event) => {
    event.preventDefault();

    const newTodoItems = [...todoItems];
    const nextId = Math.max(...todoItems.map((item) => item.id)) + 1;

    newTodoItems.push({
      id: nextId,
      description: description
    });

    setTodoItems(newTodoItems);
    setDescription('');
  };

  const getItemToUpdate = (id) => {
    // get the todo that the user wants to edit
    const todoToUpdate = todoItems.find(item => item.id === id);

    // update the state with that todo's information
    setDescription(todoToUpdate.description);    
    setIdToUpdate(todoToUpdate.id);
  };

  const updateItem = (event) => {
    event.preventDefault();

    // TODO once the API, this will all change :(

    // create a copy of the array of TODOs
    const newTodoItems = [...todoItems];

    // get the todo that the user wants to edit
    const indexToUpdate = todoItems.findIndex(toDo => toDo.id === idToUpdate);

    // update the todo
    newTodoItems[indexToUpdate] = {
      id: newTodoItems,
      description
    };

    // update the TODOs state
    setTodoItems(newTodoItems);

    // reset the form
    setDescription('');
    setIdToUpdate(0);
  }

  const removeItem = (id) => {    
    const newTodoItems = todoItems.filter((item) => item.id !== id);
    setTodoItems(newTodoItems);
  }

  const makeTodoItem = (item) => {
    return (
      <TodoItem key={item.id} id={item.id} description={item.description} onRemove={removeItem} onUpdate={getItemToUpdate} setIdToUpdate={setIdToUpdate} />
    );
  }

  return (
    <div className="todo-list">
      <div>
      {idToUpdate === 0 ? (
        <form id="new-task-form" onSubmit={addItem}>
          <div className="row">
            <label>Add Todo Item</label>
            <input className="col" id="new-task-input" name="newTask" type="text" onChange={handleChange} value={description} />
            <button className="col btn" type="submit" disabled={description ? false : true}>Add New</button>
          </div>
        </form>
      ) : (
        <form id="update-task-form" onSubmit={updateItem}>
          <div className="row">
            <label>Update Todo Item</label>
            <input className="col" id="new-task-input" name="newTask" type="text" onChange={handleChange} value={description} />
            <button className="col btn" type="submit" disabled={description ? false : true}>Update Existing</button>
          </div>
        </form>
      )}
      </div>
      <div>
        <div className="row">
          {todoItems.map(makeTodoItem)}
        </div>
      </div>
    </div>
  );
}

export default TodoList;