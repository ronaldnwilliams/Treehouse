var guess0 = document.getElementById('guess0');
var guess1 = document.getElementById('guess1');
var questionHTML = document.getElementById('question');
var currentProgress = document.getElementById('progress');
var quizDiv = document.getElementById('quiz');
var numOfChoices = 0;

function printQuestion() {
  questionHTML.innerHTML = quiz.ask();
}

function printChoices(choices) {
  var choices = quiz.choices();
  for (var choice in choices) {
    var placeholder = document.getElementById('choice' + choice);
    placeholder.innerHTML = choices[choice];
  }
  this.numOfChoices = choices.length;
}

function printProgress() {
  currentProgress.innerHTML = 'Question ';
  currentProgress.innerHTML += quiz.currentQuestionIndex + 1;
  currentProgress.innerHTML += ' of ';
  currentProgress.innerHTML += quiz.questions.length;
}

function hideChoices() {
  for (var i = 0; i < numOfChoices; i++) {
    var placeholder = document.getElementById('choice' + i);
    placeholder.style.display = "none";
  }
}

function printGrade() {
  guess0.style.display = "none";
  guess1.style.display = "none";
  currentProgress.style.display = "none";
  questionHTML.style.display = "none";
  hideChoices();
  var score = document.getElementById('score');
  score.innerHTML = 'You got ';
  score.innerHTML += quiz.correctAnswers;
  score.innerHTML += ' out of ';
  score.innerHTML += quiz.questions.length;
  score.innerHTML += ' questions correct.'
}

guess0.onclick = function() {
  if (quiz.done === false) {
    quiz.answer(document.getElementById('choice0').innerHTML);
    if (quiz.done === false) {
      printQuestion();
      printChoices();
      printProgress();
    } else {
    printGrade();
    }
  }
}

guess1.onclick = function() {
  if (quiz.done === false) {
    quiz.answer(document.getElementById('choice1').innerHTML);
    if (quiz.done === false) {
      printQuestion();
      printChoices();
      printProgress();
    } else {
    printGrade();
    }
  }
}
