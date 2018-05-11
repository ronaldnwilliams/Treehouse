from django.urls import reverse
from django.test import TestCase
from django.utils import timezone

from .models import Course, Step


class CourseModelTest(TestCase):

    def test_course_creation(self):
        course = Course.objects.create(title='Python Basics', description='Learn the Python basics')
        now = timezone.now()
        self.assertLess(course.created_at, now)


class StepModelTests(TestCase):
    def setUp(self):
        self.course = Course.objects.create(
            title='Python Basics',
            description='Learn the Python basics'
        )

    def test_step_creation(self):
        step = Step.objects.create(
            title='Python String',
            description='Learn about Strings in Python',
            course=self.course
        )
        self.assertIn(step, self.course.step_set.all())


class CourseViewsTests(TestCase):
    def setUp(self):
        self.course = Course.objects.create(
            title='Python Basics',
            description='Learn the Python basics'
        )
        self.course2 = Course.objects.create(
            title='Django',
            description='Learn some Django'
        )
        self.step = Step.objects.create(
            title='Python String',
            description='Learn about Strings in Python',
            course=self.course
        )

    def test_course_list_view(self):
        res = self.client.get(reverse('courses:list'))
        self.assertEqual(res.status_code, 200)
        self.assertIn(self.course, res.context['courses'])
        self.assertIn(self.course2, res.context['courses'])
        self.assertTemplateUsed(res, 'courses/course_list.html')
        self.assertContains(res, self.course.title)

    def test_course_detail_view(self):
        res = self.client.get(reverse('courses:detail',
                                       kwargs={'pk': self.course.pk}))
        self.assertEqual(res.status_code, 200)
        self.assertEqual(self.course, res.context['course'])
        self.assertTemplateUsed(res, 'courses/course_detail.html')

    def test_step_detail_view(self):
        res = self.client.get(reverse('courses:step', kwargs={
                    'course_pk': self.course.pk,
                    'step_pk': self.step.pk}))
        self.assertEqual(res.status_code, 200)
        self.assertEqual(self.step, res.context['step'])
        self.assertTemplateUsed(res, 'courses/step_detail.html')
