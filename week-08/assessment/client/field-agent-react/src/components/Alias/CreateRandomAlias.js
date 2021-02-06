function createRandomAlias (agentId) {
  const nameArray = [
    'Willow', 'Robinhood', 'Pickle', 'Roofie', 'JJ', 'Arkham', 'Tailwind', 'Sailor', 'Racket'
  ];

  const personaArray = [
    'Z-3', '009', 'RN', 'DQ', '10', 'M7', 'PU', '4K', 'DD'
  ];

  const alias = {
    "name": nameArray[Math.floor(Math.random() * 9)],
    "persona": personaArray[Math.floor(Math.random() * 9)],
    "agentId": agentId
  };

  return alias;
}

export default createRandomAlias;