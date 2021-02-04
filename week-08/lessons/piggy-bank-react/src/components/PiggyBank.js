import React, { useState } from 'react';
import CoinPanel from './CoinPanel.js';
import './PiggyBank.css';

const DEFAULT_COIN_CONFIG = [
  { amount: 0.25, maxClicks: 10},
  { amount: 0.10, maxClicks: 10},
  { amount: 0.05, maxClicks: 10},
  { amount: 0.01, maxClicks: 10}
]


function PiggyBank( { coinConfig = DEFAULT_COIN_CONFIG } ) {

  const [total, setTotal] = useState(0.0);

  const increaseTotal = function (amount) {
    setTotal(total + amount);
  }

  const makeCoinPanel = function (configItem) {
    return <CoinPanel 
      key={[configItem.amount, configItem.maxClicks].join("-")}
      className="col" 
      amount={configItem.amount} 
      maxClicks={configItem.maxClicks} 
      onClick={increaseTotal} total={total} />;
  };

  return (
    <div className="piggy-bank">
      <div className="row">
          {coinConfig.map(makeCoinPanel)}
      </div>
      <h4>Total: ${total.toFixed(2)}</h4>
    </div>
  );
}

export default PiggyBank;