COURSES = {
    "Python Basics": {"Python", "functions", "variables",
                      "booleans", "integers", "floats",
                      "arrays", "strings", "exceptions",
                      "conditions", "input", "loops"},
    "Java Basics": {"Java", "strings", "variables",
                    "input", "exceptions", "integers",
                    "booleans", "loops"},
    "PHP Basics": {"PHP", "variables", "conditions",
                   "integers", "floats", "strings",
                   "booleans", "HTML"},
    "Ruby Basics": {"Ruby", "strings", "floats",
                    "integers", "conditions",
                    "functions", "input"}
}

# Python Sets

def covers(topic):
    covered = []
    for course in COURSES:
        if COURSES[course] & topic:
            covered.append(course)
    return covered

print(covers({"Python"}))

def covers_all(topics):
    covered = []
    for course in COURSES:
        if COURSES[course] >= topics:
            covered.append(course)
    return covered

print(covers_all({"conditions", "input"}))
