function Dice(sides) {
  this.sides = sides;
}

Dice.prototype.roll = function () {
  return Math.floor(Math.random() * this.sides) + 1;
}

var dice = new Dice(6);
