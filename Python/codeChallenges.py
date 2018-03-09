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



class Letter:

    def __init__(self, pattern=None):
        self.pattern = pattern

    def __iter__(self):
        yield from self.pattern
    def __str__(self):
        output = []
        for blip in self:
            if blip == '.':
                output.append('dot')
            else:
                output.append('dash')
        return '-'.join(output)

    @classmethod
    def from_string(cls, string_pattern):
        split_string = string_pattern.split('-')
        pattern = []
        for i in split_string:
            if i == 'dash':
                pattern.append('-')
            if i == 'dot':
                pattern.append('.')
        return cls(pattern)

class S(Letter):
    def __init__(self):
        pattern = ['.', '.', '.']
        super().__init__(pattern)
