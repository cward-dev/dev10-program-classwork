import { useState, useEffect } from 'react';

function TodoListFetch() {

    // array destructuring
    const [todoList, setTodoList] = useState([]);

    useEffect(() => {

        fetch("http://localhost:8080/api/todos")
            .then(response => {
                if (response.status !== 200) {
                    return Promise.reject("todos fetch failed")
                }
                return response.json();
            })
            .then(json => setTodoList(json))
            .catch(console.log);

    }, []);

    const addTodoItem = () => {

        const todoItem = {
            "id": `${Math.floor(Math.random() * 10000)}`,
            "description": "This is a randomly generated todo item."
        };

        const init = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(todoItem)
        };

        fetch("http://localhost:8080/api/todos", init)
            .then(response => {
                if (response.status !== 201) {
                    return Promise.reject("response is not 200 OK");
                }
                return response.json();
            })
            .then(json => setTodoList([...todoList, json]))
            .catch(console.log);
    };

    const editById = function (todoItemId) {
        
        const updatedTodoItem = {
            "id": todoItemId,
            "description": "Updated description."
        };
    
        const init = {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
          },
          body: JSON.stringify(updatedTodoItem)
        };
    
        fetch(`http://localhost:8080/api/todos/${todoItemId}`, init)
          .then(response => {
            if (response.status === 204) {
              return null;
            } else if (response.status === 400) {
              return response.json();
            } else if (response.status === 404) {
              return Promise.reject("Todo Item not found");
            } else {
              return Promise.reject(`Todo Item id ${updatedTodoItem.id} update failed with status ${response.status}.`);
            }
          })
          .then(data => {
            if (!data) {
              const newTodoList = [...todoList];
              const todoListIndexToEdit = todoList.findIndex(todo => todo.id === todoItemId);
              newTodoList[todoListIndexToEdit] = updatedTodoItem;
    
              setTodoList([...todoList], data);
            } else {
              setErrors(data);
            }
          });
      };

    const editById = (todoItemId) => {
        
        const todoItem = {
            "id": todoItemId,
            "description": "Updated description."
        };

        const init = {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(todoItem)
        };

        fetch(`http://localhost:8080/api/todos/${todoItemId}`, init)
            .then(response => {
                if (response.status === 404) {
                    return Promise.reject("Todos item not found");
                } else if (response.status === 204) {
                    return response.json(); // TODO need to return something other than JSON and update the live list?
                } else {
                    return Promise.reject(`Todos item id ${todoItem.id} update failed with status ${response.status}.`);
                }
            })
            .then(json => setTodoList([...todoList]));
    };

    const deleteById = (todoItemId) => {
        fetch(`http://localhost:8080/api/todos/${todoItemId}`, { method: "DELETE" })
            .then(response => {
                if (response.status === 204) {
                    setTodoList(todoList.filter(item => item.id !== todoItemId));
                } else if (response.status === 404) {
                    return Promise.reject("todos item not found");
                } else {
                    return Promise.reject(`Delete failed with status: ${response.status}`);
                }
            })
            .catch(console.log);
    };

    return (
        <div>
            <button onClick={addTodoItem}>Add Random Todo Item</button>
            <table>
                <thead>
                    <tr>
                        <th>Todo Item</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {todoList.map(item => (
                        <tr key={item.id}>
                            <td>{item.description}</td>
                            <td>
                                <button type="button" onClick={() => editById(item.id)}>Edit</button>
                                <button onClick={() => deleteById(item.id)}>Delete</button>
                            </td>
                        </tr>)
                    )}
                </tbody>
            </table>
        </div>
    );
}

export default TodoListFetch;