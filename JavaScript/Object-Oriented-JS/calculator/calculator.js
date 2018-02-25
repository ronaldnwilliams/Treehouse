var calculator = {
		sum: 0,
		add: function(value) {
      this.sum += value;
    },
    subtract: function(value) {
      this.sum = this.sum - value;
    },
    multipy: function(value) {
      this.sum = this.sum * value;
    },
    divide: function(value) {
      this.sum = this.sum / value;
    },
    clear: function() {
      this.sum = 0;
    },
    equals: function() {
      return this.sum;
    }
}
