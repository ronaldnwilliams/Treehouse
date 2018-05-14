from django import template

from courses.models import Course

register = template.Library()

@register.simple_tag
def newest_course():
    '''Gets the most recent course that was added to the library.'''
    return Course.objects.latest('created_at')

def nav_courses_list():
    ''' Returns dictionary of courses to display as navigation theme'''
    courses = Course.objects.all()
    return {'courses': courses}
