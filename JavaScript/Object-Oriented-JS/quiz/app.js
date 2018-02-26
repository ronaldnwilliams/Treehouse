var quiz = new Quiz();

var firstPresident = new Question("Who was the first U.S. president?", "George Washington", ["Abraham Lincoln", "George Washington"]);
var largestState = new Question("Which is the largest U.S. state?", "Alaska", ["Alaska", "Texas"]);

quiz.add(firstPresident);
quiz.add(largestState);

printQuestion(quiz.ask());
printChoices(quiz.choices());
printProgress();
