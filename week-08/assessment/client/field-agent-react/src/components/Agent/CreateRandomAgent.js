function createRandomAgent () {
  const firstNameArray = [
    'Adam', 'Alex', 'Aaron', 'Ben', 'Carl', 
    'Dan', 'David', 'Edward', 'Fred', 'Frank', 
    'George', 'Hal', 'Hank', 'Ike', 'John', 
    'Jack', 'Joe', 'Larry', 'Monte', 'Matthew', 
    'Mark', 'Nathan', 'Otto', 'Paul', 'Peter', 
    'Roger', 'Steve', 'Thomas', 'Tim', 'Ty', 
    'Victor', 'Walter', 'Emily', 'Hannah', 'Madison',
    'Ashley', 'Sarah', 'Alexis', 'Samantha', 'Jessica',
    'Elizabeth', 'Taylor', 'Lauren', 'Alyssa', 'Kayla',
    'Abigail', 'Brianna', 'Olivia', 'Emma', 'Megan',
    'Grace', 'Victoria', 'Rachel', 'Anna', 'Sydney',
    'Destiny', 'Morgan', 'Jennifer', 'Jasmine', 'Haley',
    'Julia', 'Kaitlyn', 'Nicole', 'Amanda', 'Katherine'
  ];

  const middleNameArray = [
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 
    'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' 
  ];

  const lastNameArray = [
    'Warren', 'Jefferson', 'Montgomery', 'Alpine', 'Cobbler', 
    'Warden', 'Davenport', 'Ramish', 'Stephenson', 'Anderson', 
    'Ashwoon', 'Aikin', 'Bateman', 'Bongard', 'Bowers', 
    'Boyd', 'Cannon', 'Cast', 'Deitz', 'Dewalt', 
    'Ebner', 'Frick', 'Hancock', 'Haworth', 'Hesch',
    'Hoffman', 'Kassing', 'Knutson', 'Lawless', 'Lawicki', 
    'Mccord', 'McCormack', 'Miller', 'Myers', 'Nugent', 
    'Ortiz', 'Orwig', 'Ory', 'Paiser', 'Pak', 
    'Pettigrew', 'Quinn', 'Quizoz', 'Ramachandran', 'Resnick', 
    'Sagar', 'Schickowski', 'Schiebel', 'Sellon', 'Severson', 'Shaffer', 
    'Solberg', 'Soloman', 'Sonderling', 'Soukup', 'Soulis', 'Stahl', 
    'Sweeney', 'Tandy', 'Trebil', 'Trusela', 'Trussel', 'Turco', 'Uddin', 
    'Uflan', 'Ulrich', 'Upson', 'Vader', 'Vail', 'Valente', 'Van Zandt', 
    'Vanderpoel', 'Ventotla', 'Vogal', 'Wagle', 'Wagner', 'Wakefield', 'Weinstein', 
    'Weiss', 'Woo', 'Yang', 'Yates', 'Yocum', 'Zeaser', 'Zeller', 'Ziegler', 'Bauer', 
    'Baxster', 'Casal', 'Cataldi', 'Caswell', 'Celedon', 'Chambers', 'Chapman', 
    'Christensen', 'Darnell', 'Davidson', 'Davis', 'DeLorenzo', 'Dinkins', 
    'Doran', 'Dugelman', 'Dugan', 'Duffman', 'Eastman', 'Ferro', 'Ferry', 
    'Fletcher', 'Fietzer', 'Hylan', 'Hydinger', 'Illingsworth', 'Ingram', 
    'Irwin', 'Jagtap', 'Jenson', 'Johnson', 'Johnsen', 'Jones', 'Jurgenson', 
    'Kalleg', 'Kaskel', 'Keller', 'Leisinger', 'LePage', 'Lewis', 'Linde', 
    'Lulloff', 'Maki', 'Martin', 'McGinnis', 'Mills', 'Moody', 'Moore', 
    'Napier', 'Nelson', 'Norquist', 'Nuttle', 'Olson', 'Ostrander', 'Reamer', 
    'Reardon', 'Reyes', 'Rice', 'Ripka', 'Roberts', 'Rogers', 'Root', 
    'Sandstrom', 'Sawyer', 'Schlicht', 'Schmitt', 'Schwager', 'Schutz', 
    'Schuster', 'Tapia', 'Thompson', 'Tiernan', 'Tisler'
  ];

  const dobArray = [
    '1956-10-23', '1987-04-03', '2005-12-23', '1994-07-22', '1989-03-17', '1966-09-19', '1978-02-28', '1999-12-31', '1991-06-06'
  ];

  const heightInInchesArray = [
    36, 37, 38, 39,
    40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
    50, 51, 52, 53, 54, 55, 56, 57, 58, 59,
    60, 61, 62, 63, 64, 65, 66, 67, 68, 69,
    70, 71, 72, 73, 74, 75, 76, 77, 78, 79,
    80, 81, 82, 83, 84, 85, 86, 87, 88, 89,
    90, 91, 92, 93, 94, 95, 96
  ];

  const agent = {
    "firstName": firstNameArray[Math.floor(Math.random() * firstNameArray.length)],
    "middleName": middleNameArray[Math.floor(Math.random() * middleNameArray.length)],
    "lastName": lastNameArray[Math.floor(Math.random() * lastNameArray.length)],
    "dob": dobArray[Math.floor(Math.random() * dobArray.length)],
    "heightInInches": heightInInchesArray[Math.floor(Math.random() * heightInInchesArray.length)]
  };

  return agent;
}

export default createRandomAgent;