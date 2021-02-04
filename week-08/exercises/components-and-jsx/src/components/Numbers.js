function Numbers( { minimum, maximum } ) {
  const minValue = parseInt(minimum);
  const maxValue = parseInt(maximum);
  const numbersToDisplay = [];

  for (let i = minValue; i <= maxValue; i++) {
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