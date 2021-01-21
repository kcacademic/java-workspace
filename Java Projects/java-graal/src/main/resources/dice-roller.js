function parseDiceNotation(expression) {
  const simpleDiceRegex = /(?<numberOfDice>\d+)?[dD](?<numberOfFaces>\d+)/;
  const matchObj = simpleDiceRegex.exec(expression);

  if (!matchObj) {
    throw "Failed to parse dice expression: "+ expression;
  }

  const numberOfDice = parseInt(matchObj.groups.numberOfDice) || 1;
  const numberOfFaces = parseInt(matchObj.groups.numberOfFaces);

  return Array.from({ length: numberOfDice },
                  (x, i) => { return Math.floor(Math.random() * numberOfFaces) + 1;})
              .reduce((total, i) => { return total + i }, 0);
}

// module exports are not supported, use global scope
roll = parseDiceNotation;