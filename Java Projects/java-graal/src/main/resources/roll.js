'use strict';

require('./dice-roller.js');

const args = process.argv.slice(2);

if (!args || args.length !== 1) {
  console.error("Usage: roll <dice_expression>");
  console.error("Example: roll 2d20");
  process.exit(1);
}

const expression = args[0];
console.log(roll(expression));