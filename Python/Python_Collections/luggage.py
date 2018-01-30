# def packer(name=None, **kwargs):
#     print(kwargs)
#
# def unpacker(first_name=None, last_name=None):
#     if first_name and last_name:
#         print('Hi {} {}'.format(first_name, last_name))
#     else:
#         print('Hi no name')
#
# packer(name="Ronald", num=27, spanish_inquisition=None)
# unpacker(**{'last_name':'Williams','first_name':'Ronald'})
#
# values = [{"name": "Michelangelo", "food": "PIZZA"}, {"name": "Garfield", "food": "lasagna"}]
# template = "Hi, I'm {name} and I love to eat {food}!"
#
# def string_factory(values):
#     strings = []
#     for value in values:
#         strings.append(template.format(name=value['name'], food=value['food']))
#     print(strings)
#
# string_factory(values)
#
#
# string = "I I do not not like it Sam I Am"
#
# def word_count(string_arg):
#     string_arg = string_arg.lower().split()
#     words = {}
#     for word in string_arg:
#         if word not in words:
#             words[word] = 1
#         else:
#             words[word] += 1
#     return words
#
# print(word_count(string))

# The dictionary will look something like:
dictionary = {'Andrew Chalkley': ['jQuery Basics', 'Node.js Basics'], 'Kenneth Love': ['Python Basics', 'Python Collections', 'java', 'sql']}
#
# Each key will be a Teacher and the value will be a list of courses.
#
# Your code goes below here.

def num_teachers(dictionary):
    count = 0
    for key in dictionary.keys():
        count += 1
    return count

def num_courses(dictionary):
    count = 0
    for value in dictionary.values():
        count += len(value)
    return count

def courses(dictionary):
    courses = []
    for value in dictionary.values():
        courses += value
    return courses

def most_courses(dictionary):
    most = []
    teacher = ''
    for key in dictionary.keys():
        if len(dictionary[key]) > len(most):
            teacher = key
    return teacher

def stats(dictionary):
    teacher_courses = []
    for key in dictionary.keys():
        teacher_courses.append([key, len(dictionary[key])])
    return teacher_courses

# print(num_teachers(dictionary))
# print(num_courses(dictionary))
# print (courses(dictionary))
# print(most_courses(dictionary))
# print(stats(dictionary))

course_minutes = {"Python Basics" : 232, "Django Basics" : 237, "Flask Basics": 189, "Java Basics" : 133}

for course, minutes in course_minutes.items():
    print("{} is {} minutes long".format(course, minutes))

# for index, letter in enumerate("abcdefghijklmnopqrstuvwxyz"):
#     print("{}. {}".format(index+1, letter))
