function Quiz() {
  this.questions = [];
  this.currentQuestionIndex = 0;
  this.correctAnswers = 0;
  this.done = false;
}

Quiz.prototype.add = function(question) {
  this.questions.push(question);
};

Quiz.prototype.ask = function() {
  return this.questions[this.currentQuestionIndex].question;
};

Quiz.prototype.choices = function() {
  return this.questions[this.currentQuestionIndex].choices;
};

Quiz.prototype.answer = function(answer) {
  if (this.done === false) {
    var currentQuestion = this.questions[this.currentQuestionIndex];
    if (currentQuestion.answer === answer) {
      this.correctAnswers++;
    }
    this.currentQuestionIndex++;
    if (this.currentQuestionIndex === this.questions.length) {
      this.done = true;
    } else {
      this.ask();
    }
  }
};
