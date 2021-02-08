function createRandomAlias (agentId) {
  const nameArray = [
    'Willow', 'Robinhood', 'Pickle', 'Roofie', 'JJ', 'Arkham', 'Tailwind', 'Sailor', 'Racket'
  ];

  const personaArray = [
    'Z-3', '009', 'RN', 'DQ', '10', 'M7', 'PU', '4K', 'DD'
  ];

  const alias = {
    "name": nameArray[Math.floor(Math.random() * nameArray.length)],
    "persona": personaArray[Math.floor(Math.random() * personaArray.length)],
    "agentId": agentId
  };

  return alias;
}

export default createRandomAlias;