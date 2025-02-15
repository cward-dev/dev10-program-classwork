import React from 'react';
import PiggyBank from './components/PiggyBank.js';

function App() {
  return (
    <div className="container">
      <h1>Piggy Banks</h1>
      <PiggyBank />
      <PiggyBank coinConfig={[
        { amount: 0.50, maxClicks: 4 },
        { amount: 0.25, maxClicks: 4 }
      ]}/>
      <PiggyBank coinConfig={[
        { amount: 0.01, maxClicks: 6 },
        { amount: 0.02, maxClicks: 3 },
        { amount: 0.03, maxClicks: 2 }
      ]}/>
      <PiggyBank coinConfig={[
        { amount: 0.01, maxClicks: 10 },
        { amount: 0.02, maxClicks: 10 },
        { amount: 0.03, maxClicks: 10 },
        { amount: 0.04, maxClicks: 10 },
        { amount: 0.05, maxClicks: 10 },
        { amount: 0.06, maxClicks: 10 },
        { amount: 0.07, maxClicks: 10 }
      ]}/>
    </div>
  );
}

export default App;