import { useState, useEffect } from 'react';
import createRandomAlias from './CreateRandomAlias';
import AliasDisplay from './AliasDisplay';

function AliasFetch( { setMenuSelection, agent } ) {

  const [aliases, setAliases] = useState([]);
  const [editAliasId, setEditAliasId] = useState(0);
  const [deleteAliasId, setDeleteAliasId] = useState(0);
  const [errors, setErrors] = useState([]);


  useEffect(() => {
    const getData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/alias/agent/${agent.agentId}`);
        
        if (response.status === 200) {
          const data = await response.json();
          setAliases(data);
        }
      } catch (error) {
        setErrors("Failed to fetch aliases.");
      }
    };
    getData();
  }, []);

  const addAlias = async (newAlias) => {
    const alias = {
      "name": newAlias.name,
      "persona": newAlias.persona,
      "agentId": agent.agentId
    };

    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(alias)
    };

    try {
      const response = await fetch("http://localhost:8080/api/alias", init);

      if (response.status === 201 || response.status === 400) {
        const data = await response.json();

        if (data.aliasId) {
          setAliases([...aliases, data]);
          handleCancel();
        } else {
          setErrors(data);
        }
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"]);
      }
    } catch (error) {
      setErrors(error)
    }
  }

  const addRandomAlias = () => {
    const alias = createRandomAlias(1);
    addAlias(alias);
  };

  const editAlias = async (editedAlias) => {
    const updatedAlias = {
      "aliasId": editedAlias.aliasId,
      "name": editedAlias.name,
      "persona": editedAlias.persona,
      "agentId": agent.agentId
    };

    const init = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify(updatedAlias)
    };

    try {
      const response = await fetch(`http://localhost:8080/api/alias/${editedAlias.aliasId}`, init);

      if (response.status === 204) {
        const newAliases = [...aliases];
        const aliasIndexToEdit = aliases.findIndex(alias => alias.aliasId === editAliasId);
        newAliases[aliasIndexToEdit] = updatedAlias;

        setAliases(newAliases);
        handleCancel();
      } else if (response.status === 400) {
        const data = await response.json();
        setErrors(data);
      } else if (response.status === 404) {
        throw new Error(["Alias to edit not found."])
      } else {
        throw new Error(["Something unexpected went wrong, sorry!"])
      }
    } catch (error) {
      setErrors(error);
    }
  }

  const deleteById = async (aliasId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/alias/${aliasId}`, { method: "DELETE" });
    
      if (response.status === 204) {
        const newAliases = aliases.filter(alias => alias.aliasId !== aliasId);
        setAliases(newAliases);
        handleCancel();
      } else if (response.status === 404) {
        throw new Error(`Alias ID #${aliasId} not found.`);
      } else {
        throw new Error("Something unexpected went wrong, sorry!")
      }
    } catch (error) {
      setErrors(error);
    }
  };

  const getAliasToEdit = () => {
    return aliases.find(a => a.aliasId === editAliasId);
  };

  const getAliasToDelete = () => {
    return aliases.find(a => a.aliasId === deleteAliasId);
  };

  const goBackToAgentMenu = () => {
    setMenuSelection(1);
  };

  const handleCancel = () => {
    setEditAliasId(0);
    setDeleteAliasId(0);
    setErrors([]);
  }

  return (
    <AliasDisplay aliases={aliases} editAliasId={editAliasId} setEditAliasId={setEditAliasId} deleteAliasId={deleteAliasId} setDeleteAliasId={setDeleteAliasId} errors={errors} 
    handleCancel={handleCancel} addAlias={addAlias} addRandomAlias={addRandomAlias} editAlias={editAlias} deleteById={deleteById} 
    getAliasToEdit={getAliasToEdit} getAliasToDelete={getAliasToDelete} goBackToAgentMenu={goBackToAgentMenu} agent={agent} />
  );
}

export default AliasFetch;