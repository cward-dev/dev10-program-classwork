function Numbers( { minimum, maximum } ) {
  const numbersToDisplay = [];
  for (let i = minimum; i <= maximum; i++) {
    numbersToDisplay.push(i);
  }
  
  return (
    <ul>
      {numbersToDisplay.map((number) => 
        <li key={number}>{number}</li>
      )}     
    </ul>
  );
}

export default Numbers;