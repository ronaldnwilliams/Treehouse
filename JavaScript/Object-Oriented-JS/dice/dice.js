var dice = {
  sides: 6,
  roll: function () {
    return Math.floor(Math.random() * this.sides) + 1;
  }
}
