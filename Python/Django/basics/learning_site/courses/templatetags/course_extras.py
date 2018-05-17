from django import template

from courses.models import Course

register = template.Library()

@register.simple_tag
def newest_course():
    '''Gets the most recent course that was added to the library.'''
    return Course.objects.latest('created_at')

@register.inclusion_tag('courses/course_nav.html')
def nav_courses_list():
    ''' Returns dictionary of courses to display as navigation theme'''
    courses = Course.objects.all()
    return {'courses': courses}

@register.filter('time_estimate')
def time_estimate(word_count):
    ''' Estimates the number of minutes it will take to complete a step
    based on passed in word count'''
    minutes = round(word_count/20)
    return minutes
