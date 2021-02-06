function createRandomAgent () {
  const firstNameArray = [
    'Paul', 'Robert', 'Ashley', 'David', 'John', 'Sara', 'Beverly', 'Ashton', 'Jackson'
  ];

  const middleNameArray = [
    'A', 'B', 'C', 'H', 'J', 'M', 'S', 'T', 'W'
  ];

  const lastNameArray = [
    'Warren', 'Jefferson', 'Montgomery', 'Alpine', 'Cobbler', 'Warden', 'Davenport', 'Ramish', 'Stephenson'
  ];

  const dobArray = [
    '1956-10-23', '1987-04-03', '2005-12-23', '1994-07-22', '1989-03-17', '1966-09-19', '1978-02-28', '1999-12-31', '1991-06-06'
  ];

  const heightInInchesArray = [
    54, 58, 60, 62, 64, 66, 68, 70, 72
  ];

  const agent = {
    "firstName": firstNameArray[Math.floor(Math.random() * 9)],
    "middleName": middleNameArray[Math.floor(Math.random() * 9)],
    "lastName": lastNameArray[Math.floor(Math.random() * 9)],
    "dob": dobArray[Math.floor(Math.random() * 9)],
    "heightInInches": heightInInchesArray[Math.floor(Math.random() * 9)]
  };

  return agent;
}

export default createRandomAgent;